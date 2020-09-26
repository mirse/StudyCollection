package com.wdz.module_customview.materialdesign.coordinator;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

/**
 * Created by dezhi.wang on 2020/5/14.
 */
public class TitleBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "TitleBehavior";
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    public TitleBehavior() {
    }

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (deltaY == 0) {
            deltaY = (dependency.getY() - (float) child.getHeight());
        }
        Log.i(TAG, "onDependentViewChanged: dependency.getY():"+dependency.getY()+" child.getHeight():"+child.getHeight()+" deltaY:"+deltaY);

        float dy = dependency.getY() - (float) child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float scale = (1- dy / deltaY);
        Log.i(TAG, "onDependentViewChanged: alpha:"+scale+" dy:"+dy);
        if (scale<0){
            scale = (float) 0;
        }
        if (scale >= 1) {
            child.setAlpha(scale);
        }
        else{
            child.setAlpha(0);
        }


        return true;
    }



}
