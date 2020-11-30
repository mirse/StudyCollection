package com.wdz.module_basis.adaptation.bar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;


import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashBarActivity extends AppCompatActivity {
    private static final String TAG = "SplashBarActivity";
    private Window window;
    private View decorView;
    private int system_ui_flag_immersive;
    private int system_ui_flag_immersive_sticky;
    private int system_ui_flag_fullscreen;
    private int system_ui_flag_layout_fullscreen;
    private int system_ui_flag_hide_navigation;
    private int system_ui_flag_layout_hide_navigation;
    private int system_ui_flag_layout_stable;
    private Toolbar toolbar;
    private RelativeLayout relativeLayout;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_bar);
        ButterKnife.bind(this);
        system_ui_flag_immersive = View.SYSTEM_UI_FLAG_IMMERSIVE;
        system_ui_flag_immersive_sticky = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        system_ui_flag_fullscreen = View.SYSTEM_UI_FLAG_FULLSCREEN;
        system_ui_flag_layout_fullscreen = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        system_ui_flag_hide_navigation = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        system_ui_flag_layout_hide_navigation = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        system_ui_flag_layout_stable = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        relativeLayout = findViewById(R.id.container);

        window = getWindow();

        decorView = window.getDecorView();

    }



    @OnClick({R2.id.setBar1, R2.id.setBar2,R2.id.setBar3,R2.id.setBar4})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.setBar1) {//android4.4 - android5.0
            //  decorView.setSystemUiVisibility(system_ui_flag_fullscreen|system_ui_flag_layout_stable|system_ui_flag_immersive_sticky);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            StatusBarUtils.createBarView(this, getResources().getColor(R.color.colorPrimaryDark));
        } else if (id == R.id.setBar2) {//android5.0 ->
            StatusBarUtils.setStatusBarColor(this, getResources().getColor(R.color.colorPrimaryDark));
        } else if (id == R.id.setBar3) {//全面屏适配
            getWindow().getDecorView().setSystemUiVisibility(system_ui_flag_layout_fullscreen | system_ui_flag_layout_stable);
            StatusBarUtils.displayEdges(this);
            StatusBarUtils.setStatusBarColor(this, Color.TRANSPARENT);
            StatusBarUtils.createBarView(this, getResources().getColor(R.color.colorPrimaryDark));
        } else if (id == R.id.setBar4) {
            StatusBarUtils.setStatusBarColor(this, getResources().getColor(R.color.colorPrimaryDark));
        }
    }


}
