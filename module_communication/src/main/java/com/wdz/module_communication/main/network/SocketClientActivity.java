package com.wdz.module_communication.main.network;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

@Route(path = ARouterConstant.ACTIVITY_SOCKET)
public class SocketClientActivity extends AppCompatActivity {
    private Button bt_send;
    private EditText et_receive;
    private TextView tv_message;
    private PrintWriter printWriter;
    private static final String TAG = "SocketClientActivity";
    private String msg;

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
                msg = et_receive.getText().toString();
                if (msg !=null && printWriter!=null){
                    new Thread(){
                        @Override
                        public void run() {
                            printWriter.println(msg);
                        }
                    }.start();
//                    MyThread myThread = new MyThread();
//                    myThread.start();
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
                Log.e(TAG, "服务器连接成功");
            } catch (final IOException e) {
                SystemClock.sleep(1000);
                Log.e(TAG, "连接TCP服务失败, 重试...");
            }
        }
        try {
            //接收服务器端的消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!SocketClientActivity.this.isFinishing()){
                final String s = bufferedReader.readLine();
                if (s != null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_message.setText(tv_message.getText() + "\n" + "服务端：" + s);
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

//    class MyThread extends Thread{
//        @Override
//        public void run() {
//            printWriter.println(msg);
//        }
//    }
}
