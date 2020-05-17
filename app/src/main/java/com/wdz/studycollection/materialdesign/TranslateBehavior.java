package com.wdz.studycollection.materialdesign;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import java.util.List;

/**
 * Created by dezhi.wang on 2020/5/14.
 */
public class TranslateBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "TranslateBehavior";
    int offsetTotal = 0;
    boolean scrolling = false;

    public TranslateBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        offset(child, dyConsumed);
    }



    public void offset(View child,int dy){
        Log.i(TAG, "offset: dy:"+dy);
        int old = offsetTotal;
        int top = offsetTotal - dy;
        top = Math.max(top, -child.getHeight());
        top = Math.min(top, 0);
        offsetTotal = top;
        if (old == offsetTotal){
            scrolling = false;
            return;
        }
        int delta = offsetTotal-old;
        child.offsetTopAndBottom(delta);
        scrolling = true;
    }


}
