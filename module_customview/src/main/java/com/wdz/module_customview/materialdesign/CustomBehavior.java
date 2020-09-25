package com.wdz.module_customview.materialdesign;

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
public class CustomBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "CustomBehavior";
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    public CustomBehavior() {
    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.i(TAG, "onDependentViewChanged: ");

        if (deltaY == 0) {
            deltaY = (dependency.getY() - (float) child.getHeight()*1/2);
        }
        Log.i(TAG, "onDependentViewChanged: dependency.getY():"+dependency.getY()+" child.getHeight():"+child.getHeight()+" deltaY:"+deltaY);

        float dy = dependency.getY() - (float) child.getHeight()*1/2;
        dy = dy < 0 ? 0 : dy;
        float scale = dy / deltaY;
        Log.i(TAG, "onDependentViewChanged: alpha:"+scale+" dy:"+dy);
        if (scale<0){
            scale = (float) 0;
        }
        child.setScaleX(scale);
        child.setScaleY(scale);
        child.setY(child.getTop()*scale);
        return true;
    }



}
