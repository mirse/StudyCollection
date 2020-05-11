package com.wdz.studycollection.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wdz.studycollection.R;


import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandlerDemoActivity extends AppCompatActivity {
    @BindView(R.id.bt_handle) Button mBtnHandle;
    @BindView(R.id.tv_msg) TextView mTvMsg;
    @BindView(R.id.bt_databinding) Button mBtnDatabinding;
    @BindView(R.id.bt_clock) Button mBtnClock;
    @BindView(R.id.bt_clock1) Button mBtnClock1;
    private static final String TAG = "HandlerDemoActivity";
    private MyHandler mHandler = new MyHandler(this);


    private static class MyHandler extends Handler{

        public final WeakReference<HandlerDemoActivity> handlerDemoActivity;

        public MyHandler(HandlerDemoActivity handlerDemoActivity) {
            this.handlerDemoActivity = new WeakReference<>(handlerDemoActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerDemoActivity handlerDemoActivity = this.handlerDemoActivity.get();
            if (handlerDemoActivity!=null){
                handlerDemoActivity.mTvMsg.setText("handler的handleMessage:"+msg.obj);
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_demo);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.bt_handle,R.id.bt_databinding,R.id.bt_clock,R.id.bt_clock1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_handle:
                Message msg = Message.obtain();
                msg.obj = "我是消息数据";
                mHandler.sendMessageDelayed(msg,2000);
                break;

            case R.id.bt_databinding:
                startActivity(new Intent(this, DataBindingDemoActivity.class));
                break;
            case R.id.bt_clock:
                startActivity(new Intent(this, TimerActivity.class));
                break;
            case R.id.bt_clock1:
                startActivity(new Intent(this, Timer1Activity.class));
                break;

            default:
                break;

        }

    }
}
