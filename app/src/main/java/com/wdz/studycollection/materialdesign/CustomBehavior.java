package com.wdz.studycollection.materialdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
            deltaY = dependency.getY() - child.getHeight();
        }
        Log.i(TAG, "onDependentViewChanged: dependency.getY():"+dependency.getY()+" child.getHeight():"+child.getHeight()+" deltaY:"+deltaY);

        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float alpha = dy / deltaY;
        Log.i(TAG, "onDependentViewChanged: alpha:"+alpha);
        if (alpha<0.2){
            alpha = (float) 0.2;
        }
        child.setScaleX(alpha);
        child.setScaleY(alpha);
        //child.setY(dy);
        return true;
    }



}
