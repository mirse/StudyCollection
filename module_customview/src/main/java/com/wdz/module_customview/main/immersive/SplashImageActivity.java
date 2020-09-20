package com.wdz.module_customview.main.immersive;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashImageActivity extends AppCompatActivity {

    private Window window;
    private View decorView;
    private int system_ui_flag_immersive;
    private int system_ui_flag_immersive_sticky;
    private int system_ui_flag_fullscreen;
    private int system_ui_flag_layout_fullscreen;
    private int system_ui_flag_hide_navigation;
    private int system_ui_flag_layout_hide_navigation;
    private int system_ui_flag_layout_stable;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_image);
        ButterKnife.bind(this);
        system_ui_flag_immersive = View.SYSTEM_UI_FLAG_IMMERSIVE;
        system_ui_flag_immersive_sticky = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        system_ui_flag_fullscreen = View.SYSTEM_UI_FLAG_FULLSCREEN;
        system_ui_flag_layout_fullscreen = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        system_ui_flag_hide_navigation = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        system_ui_flag_layout_hide_navigation = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        system_ui_flag_layout_stable = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


        window = getWindow();

        decorView = window.getDecorView();

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.hide();
        }



    }
    @OnClick({R2.id.setBar1,R2.id.setBar2,R2.id.setBar3,R2.id.setBar4})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.setBar1) {//android4.4 - android5.0
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(system_ui_flag_fullscreen | system_ui_flag_hide_navigation);
        } else if (id == R.id.setBar2) {//android5.0 ->
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().getDecorView().setSystemUiVisibility(system_ui_flag_fullscreen | system_ui_flag_hide_navigation);
        } else if (id == R.id.setBar3) {//全面屏适配
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            StatusBarUtils.displayEdges(this);
            getWindow().getDecorView().setSystemUiVisibility(system_ui_flag_fullscreen | system_ui_flag_hide_navigation);
        } else if (id == R.id.setBar4) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarUtils.displayEdges(this);
        }
    }
}
