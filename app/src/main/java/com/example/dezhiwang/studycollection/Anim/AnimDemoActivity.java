package com.example.dezhiwang.studycollection.Anim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.R;

public class AnimDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        initUI();
    }

    private void initUI() {
        Button mBtnValue = findViewById(R.id.bt_value);
        mBtnValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
