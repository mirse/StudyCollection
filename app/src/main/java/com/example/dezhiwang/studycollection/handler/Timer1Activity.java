package com.example.dezhiwang.studycollection.handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.dezhiwang.studycollection.R;
import com.example.dezhiwang.studycollection.databinding.ActivityTimer1Binding;

public class Timer1Activity extends AppCompatActivity {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTimer1Binding bind = DataBindingUtil.setContentView(this,R.layout.activity_timer1);
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
