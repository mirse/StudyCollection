package com.wdz.module_communication.main.handler;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.wdz.module_communication.R;
import com.wdz.module_communication.databinding.ModuleCommunicationActivityTimer1Binding;


public class Timer1Activity extends AppCompatActivity {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ModuleCommunicationActivityTimer1Binding bind = DataBindingUtil.setContentView(this, R.layout.module_communication_activity_timer1);
        bind.btStartTimer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 1;i <= 10;i++){
                    final int fadedSecond = i;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bind.tvTimer1.setText((10-fadedSecond)+"");
                        }
                    },1000*i);
                }
            }
        });
    }
}
