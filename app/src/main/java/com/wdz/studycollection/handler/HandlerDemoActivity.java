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
    private MyHandler mHandler = new MyHandler();

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //主线程
            mTvMsg.setText("handler的callback:"+msg.obj);
            return true;
        }
    })
    {
        @Override
        public void handleMessage(Message msg) {
            mTvMsg.setText("handler的handleMessage:"+msg.obj);
        }
    };

    private class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            //do somethings on UI thread
        }
    }


    private class MyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            mTvMsg.setText("handler的handleMessage:"+msg.obj);
            super.handleMessage(msg);
        }
    }


    private Handler handler1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_demo);
        ButterKnife.bind(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler1 = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        //mTvMsg.setText("我是子线程的handler:"+msg.obj);
                        Toast.makeText(getBaseContext(),"我是子线程的handler:"+msg.obj,Toast.LENGTH_SHORT).show();
                    }
                };
                Looper.loop();
            }
        }).start();
    }

    @OnClick({R.id.bt_handle,R.id.bt_databinding,R.id.bt_clock,R.id.bt_clock1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_handle:
                //开启一个子线程
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        //在子线程发送一个消息。
//                        Message msg = Message.obtain(handler, new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.i(TAG,"message自身");
//                            }
//                        });
                        Message msg = Message.obtain();
                        msg.obj = "我是消息数据";

                        mHandler.sendMessageDelayed(msg,2000);

//                    }
//                }).start();

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
