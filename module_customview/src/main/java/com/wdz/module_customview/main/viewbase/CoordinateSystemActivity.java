package com.wdz.module_customview.main.viewbase;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
@Route(path = ARouterConstant.ACTIVITY_COORDINATE_SYSTEM)
public class CoordinateSystemActivity extends AppCompatActivity {
    private static final String TAG = "CoordinateSystemActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_system);

        initHeight();
        getStatusBarHeight();
        getNavigationBarHeight();
        WebView viewById = findViewById(R.id.web_view);
        String url = "file:///android_asset/weddingInvitations-master/index.html";
        Log.i(TAG,url);
        viewById.getSettings().setJavaScriptEnabled(true); // 允许JavaScript脚本运行
        viewById.getSettings().setDomStorageEnabled(true); // 开启本地DOM存储
        viewById.getSettings().setBlockNetworkImage(false);
        viewById.getSettings().setBlockNetworkLoads(false);
        viewById.loadUrl(url);


    }

    private void initHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //getRealMetrics 获得真实的宽高度    getMetrics高度需要加状态栏高度
        int screenHeight = metrics.heightPixels;
        int screenWidth  = metrics.widthPixels;
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
