package com.example.dezhiwang.studycollection.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.dezhiwang.studycollection.DataSave.Room.RoomTestActivity;
import com.example.dezhiwang.studycollection.R;

import butterknife.BindView;
import butterknife.OnClick;

public class HandlerDemoActivity extends AppCompatActivity {
    @BindView(R.id.bt_handle)
    Button mBtnHandle;
    private static final String TAG = "HandlerDemoActivity";

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.i(TAG,"handler自己的callback:"+msg.obj);
            return false;
        }
    })
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG,"handler的handleMessage:"+msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_demo);
        handler = new Handler();
    }

    @OnClick(R.id.bt_handle)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_handle:
                //开启一个子线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //在子线程发送一个消息。
                        Message msg = new Message();
                        msg.obj = "我是消息数据";
                        handler.sendMessage(msg);
                    }
                }).start();
                break;

            default:
                break;

        }

    }
}
