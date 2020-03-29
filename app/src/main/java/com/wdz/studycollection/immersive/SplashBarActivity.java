package com.wdz.studycollection.immersive;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wdz.studycollection.R;

import androidx.core.view.ViewCompat;
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



    @OnClick({R.id.setBar1,R.id.setBar2,R.id.setBar3,R.id.setBar4})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setBar1:
                //android4.4 - android5.0
              //  decorView.setSystemUiVisibility(system_ui_flag_fullscreen|system_ui_flag_layout_stable|system_ui_flag_immersive_sticky);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                StatusBarUtils.createBarView(this,getResources().getColor(R.color.colorPrimaryDark));

                break;
            case R.id.setBar2:
                //android5.0 ->
                StatusBarUtils.setStatusBarColor(this,getResources().getColor(R.color.colorPrimaryDark));
                break;
            case R.id.setBar3:

                //全面屏适配
                getWindow().getDecorView().setSystemUiVisibility(system_ui_flag_layout_fullscreen|system_ui_flag_layout_stable);
                StatusBarUtils.displayEdges(this);
                StatusBarUtils.setStatusBarColor(this,Color.TRANSPARENT);
                StatusBarUtils.createBarView(this,getResources().getColor(R.color.colorPrimaryDark));
                break;
            case R.id.setBar4:
                StatusBarUtils.setStatusBarColor(this,getResources().getColor(R.color.colorPrimaryDark));
                break;
        }
    }


}
