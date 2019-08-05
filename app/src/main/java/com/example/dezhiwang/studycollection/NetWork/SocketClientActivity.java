package com.example.dezhiwang.studycollection.NetWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dezhiwang.studycollection.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClientActivity extends AppCompatActivity {
    private Button bt_send;
    private EditText et_receive;
    private TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
        initView();
        Intent service = new Intent(this, SocketServerService.class);
        startService(service);
        connectToService();
    }
    private void initView() {
        et_receive= (EditText) findViewById(R.id.et_receive);
        bt_send= (Button) findViewById(R.id.bt_send);
        tv_message= (TextView) this.findViewById(R.id.tv_message);
    }


    private void connectToService() {
        Socket socket = null;
        if (socket == null){
            try {
                socket = new Socket("host", 8688);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //接收服务器端的消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()){
                String s = bufferedReader.readLine();
                if (s != null){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
