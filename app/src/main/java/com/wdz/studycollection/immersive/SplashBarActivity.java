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

        toolbar = findViewById(R.id.include);
        relativeLayout = findViewById(R.id.container);

        window = getWindow();

        decorView = window.getDecorView();

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.hide();
        }

        int height = getStatusBarHeight();
        //relativeLayout.addView(new View());
        addView();
//        toolbar.setPadding(0, height,
//                0, 0);


    }
    /**
     * 按钮点击事件，向容器中添加TextView
     */
    public void addView() {
        TextView child = new TextView(this);
        child.setTextSize(20);
        child.setTextColor(getResources().getColor(R.color.colorAccent));
        // 获取当前的时间并转换为时间戳格式, 并设置给TextView
        child.setText("1111");
        // 调用一个参数的addView方法
        relativeLayout.addView(child);
    }


    private View createStatusBarView(Activity activity) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.BLACK);
        return statusBarView;
    }

    private int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }



    @OnClick({R.id.setBar1,R.id.setBar2,R.id.setBar3,R.id.setBar4})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setBar1:
                //android4.4 - android5.0
                decorView.setSystemUiVisibility(system_ui_flag_fullscreen|system_ui_flag_layout_stable|system_ui_flag_immersive_sticky);

                break;
            case R.id.setBar2:



                //android5.0 ->
                //decorView.setSystemUiVisibility(system_ui_flag_layout_fullscreen);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.holo_blue_dark));
                break;
            case R.id.setBar3:

                //全面屏适配
                getWindow().getDecorView().setSystemUiVisibility(system_ui_flag_layout_fullscreen|system_ui_flag_layout_stable);
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
