package com.wdz.studycollection.immersive;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wdz.studycollection.R;

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
    @OnClick({R.id.setBar1,R.id.setBar2,R.id.setBar3,R.id.setBar4})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setBar1:
                //android4.4 - android5.0
                decorView.setSystemUiVisibility(system_ui_flag_fullscreen|system_ui_flag_hide_navigation);

                break;
            case R.id.setBar2:
                //android5.0 ->
                decorView.setSystemUiVisibility(system_ui_flag_layout_fullscreen);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.holo_blue_dark));
                break;
            case R.id.setBar3:
                //全面屏适配
                getWindow().getDecorView().setSystemUiVisibility(system_ui_flag_layout_fullscreen);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                getWindow().setAttributes(lp);
                window.setStatusBarColor(Color.TRANSPARENT);
                break;
            case R.id.setBar4:
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                WindowManager.LayoutParams lp1 = getWindow().getAttributes();
                lp1.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                getWindow().setAttributes(lp1);
                break;
        }
    }
}
