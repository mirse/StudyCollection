package com.example.dezhiwang.studycollection.ViewBase;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.example.dezhiwang.studycollection.R;

public class CoordinateSystemActivity extends AppCompatActivity {
    private static final String TAG = "CoordinateSystemActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_system);
        initHeight();
        getStatusBarHeight();
        getNavigationBarHeight();
    }

    private void initHeight() {
        WindowManager windowManager = getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        int screenWidth = defaultDisplay.getWidth();
        int screenHeight = defaultDisplay.getHeight();
        Log.i(TAG,"屏幕宽度："+screenWidth+" 屏幕高度："+screenHeight);

        //2340*1080    宽1080 高2232

        Rect outRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        int height = outRect.height();
        int width= outRect.width();
        Log.i(TAG,"Rect-屏幕宽度："+width+" Rect-屏幕高度："+height);
    }


    private void getStatusBarHeight(){
        Resources resources = getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        float statusBarHeight = resources.getDimension(resourceId);
        Log.i(TAG,"statusBarHeight："+statusBarHeight);
    }


    private void getNavigationBarHeight(){
        Resources resources = getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationBarHeight = resources.getDimensionPixelSize(resourceId);
        Log.i(TAG,"navigationBarHeight："+navigationBarHeight);
    }
}
