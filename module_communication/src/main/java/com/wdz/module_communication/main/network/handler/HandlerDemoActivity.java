package com.wdz.module_communication.main.network.handler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_HANDLER)
public class HandlerDemoActivity extends AppCompatActivity {
    @BindView(R2.id.bt_handle) Button mBtnHandle;
    @BindView(R2.id.tv_msg) TextView mTvMsg;
    @BindView(R2.id.bt_databinding) Button mBtnDatabinding;
    @BindView(R2.id.bt_clock) Button mBtnClock;
    @BindView(R2.id.bt_clock1) Button mBtnClock1;
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

    @OnClick({R2.id.bt_handle,R2.id.bt_databinding,R2.id.bt_clock,R2.id.bt_clock1})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_handle) {
            Message msg = Message.obtain();
            msg.obj = "我是消息数据";
            mHandler.sendMessageDelayed(msg, 2000);
        } else if (id == R.id.bt_databinding) {
            startActivity(new Intent(this, DataBindingDemoActivity.class));
        } else if (id == R.id.bt_clock) {
            startActivity(new Intent(this, TimerActivity.class));
        } else if (id == R.id.bt_clock1) {
            startActivity(new Intent(this, Timer1Activity.class));
        }

    }
}
