package com.wdz.studycollection.immersive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.wdz.studycollection.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.bt_image,R.id.bt_actionbar})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_image:
                startActivity(new Intent(this,SplashImageActivity.class));
                break;
            case R.id.bt_actionbar:
                startActivity(new Intent(this,SplashBarActivity.class));
                break;

        }
    }
}
