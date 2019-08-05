package com.example.dezhiwang.studycollection.NetWork;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dezhiwang.studycollection.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClientActivity extends AppCompatActivity {
    private Button bt_send;
    private EditText et_receive;
    private TextView tv_message;
    private PrintWriter printWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
        initView();
        Intent service = new Intent(this, SocketServerService.class);
        startService(service);
        new Thread(){
            @Override
            public void run() {
                connectToService();
            }
        }.start();
    }
    private void initView() {
        et_receive= (EditText) findViewById(R.id.et_receive);
        bt_send= (Button) findViewById(R.id.bt_send);
        tv_message= (TextView) this.findViewById(R.id.tv_message);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_receive.getText().toString();
                if (msg!=null && printWriter!=null){
                    printWriter.println(msg);
                    tv_message.setText(tv_message.getText() + "\n" + "客户端：" + msg);
                    et_receive.setText("");
                }
            }
        });

    }


    private void connectToService() {
        Socket socket = null;
        while (socket == null){
            try {
                socket = new Socket("localhost", 8688);
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            } catch (final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_message.setText("error:"+e.toString());
                    }
                });

                SystemClock.sleep(1000);
            }
        }
        try {
            //接收服务器端的消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()){
                final String s = bufferedReader.readLine();
                if (s != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                         tv_message.setText("服务端："+s);
                        }
                    });
                }
            }
            printWriter.close();
            bufferedReader.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
