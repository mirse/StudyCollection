package com.wdz.studycollection.materialdesign;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

/**
 * Created by dezhi.wang on 2020/5/14.
 */
public class TranslateBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "TranslateBehavior";
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    public TranslateBehavior() {
    }

    public TranslateBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.i(TAG, "onDependentViewChanged: ");

        Log.i(TAG, "onDependentViewChanged: dependency.getY():"+dependency.getY()+" child.getHeight():"+child.getHeight()+" deltaY:"+deltaY);

        float dy = dependency.getY() - child.getHeight();

        Log.i(TAG, "onDependentViewChanged: dy:"+dy);

        child.setY(dependency.getY());
        return true;
    }



}
