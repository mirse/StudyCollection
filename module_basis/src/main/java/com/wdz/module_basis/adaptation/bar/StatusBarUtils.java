package com.wdz.module_basis.adaptation.bar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class StatusBarUtils {
    private static final String TAG = "StatusBarUtils";

    /**
     * 获取statusBar高度
     */
    private static int getStatusBarHeight(Activity activity){
        int height = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 反射获取statusBarHeight
     */
    @SuppressLint("PrivateApi")
    private static int getStatusBarHeight1(Activity activity) {
        int barHeight = 0;
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            barHeight = activity.getResources().getDimensionPixelSize(height);
            Log.i(TAG, "height:" + barHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return barHeight;
    }
    /**
     * 创建一个与statusBar一样的View
     * @param color View颜色
     */
    private static View createStatusBarView(Activity activity, int color) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        Log.i(TAG,"statusBarHeight:"+getStatusBarHeight(activity));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }
    /**
     * setFitsSystemWindows
     */
    private static void setRootView(Activity activity) {
        //获取到activity_main.xml文件
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        //如果不设置参数，会使内容显示到状态栏上
        rootView.setFitsSystemWindows(true);
    }
    /**
     * 创建barView
     */
    static void createBarView(Activity activity, int color) {
        //1、创建与statusBar相同高度的view
        View statusView = createStatusBarView(activity, color);
        //2、添加statusView到布局中
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(statusView);
        //3、让布局适应屏幕
        setRootView(activity);
    }


    /**
     * 设置statusBar颜色，Android5.0及以上可以使用
     */
    public static void setStatusBarColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(color);
        }
    }

    /**
     * Android9.0及以上可以使用，适配全面屏、刘海屏等异形屏
     */
    public static void displayEdges(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(lp);
        }
    }
}
