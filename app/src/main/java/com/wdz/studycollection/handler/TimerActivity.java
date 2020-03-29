package com.wdz.studycollection.handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;


import com.wdz.studycollection.R;
import com.wdz.studycollection.databinding.ActivityTimerBinding;
import com.wdz.studycollection.handler.model.ObservableTimer;


/*handle+message*/

public class TimerActivity extends AppCompatActivity {

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mBinding.tvTimerStatus.setText(msg.what + "");   //在handleMessage中处理消息队列中的消息
        }
    };
    private ActivityTimerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_timer);
        ObservableTimer timer = new ObservableTimer(new ObservableField<String>("倒计时开始"));
        mBinding.setObservableTimer(timer);

        mBinding.btStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过Handler + Message的方式实现倒计时
                for (int i = 1; i <= 10; i++) {
                    Message message = Message.obtain(mHandler);
                    message.what = 10 - i;
                    mHandler.sendMessageDelayed(message, 1000 * i); //通过延迟发送消息，每隔一秒发送一条消息
                }
            }
        });

        mBinding.buttonHandlerThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimerActivity.this, HandlerThreadActivity.class));
            }
        });


    }
}
