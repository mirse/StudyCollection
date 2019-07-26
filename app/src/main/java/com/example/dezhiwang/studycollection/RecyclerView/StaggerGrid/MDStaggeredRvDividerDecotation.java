package com.example.dezhiwang.studycollection.RecyclerView.StaggerGrid;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class MDStaggeredRvDividerDecotation extends RecyclerView.ItemDecoration  {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private final Drawable mDivider;

    public MDStaggeredRvDividerDecotation(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();//20
        int spanCount = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount();//4
        int orientation = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getOrientation();//1
        boolean isDrawHorizantalDivider = true;
        boolean isDrawVerticalDivider = true;
        int extra = childCount % spanCount;
        extra = extra == 0?spanCount:extra;//4
        for (int i=0;i<childCount;i++){
            isDrawHorizantalDivider = true;
            isDrawVerticalDivider = true;
            if (orientation == OrientationHelper.VERTICAL && (i+1) % spanCount == 0){
                isDrawVerticalDivider = false;
            }
            if (orientation == OrientationHelper.VERTICAL &&i >= childCount-extra){
                isDrawHorizantalDivider = false;
            }
            if (orientation == OrientationHelper.HORIZONTAL && (i+1) % spanCount == 0){
                isDrawHorizantalDivider = false;
            }
            if (orientation == OrientationHelper.HORIZONTAL &&i >= childCount-extra){
                isDrawVerticalDivider = false;
            }
            if (isDrawHorizantalDivider){
                drawHorizantalDivider(c,parent,i);
            }
            if (isDrawVerticalDivider){
                drawVerticalDivider(c,parent,i);
            }
        }

    }

    private void drawVerticalDivider(Canvas canvas, RecyclerView parent, int position) {
        View child = parent.getChildAt(position);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
        final int top = child.getTop() - params.topMargin;
//        final int bottom = child.getBottom() + params.bottomMargin + mDivider.getIntrinsicHeight();
        final int bottom = child.getBottom() + params.bottomMargin ;
        final int left = child.getRight() + params.rightMargin;
        final int right = left + mDivider.getIntrinsicWidth();
        mDivider.setBounds(left,top,right,bottom);
        mDivider.draw(canvas);
    }

    private void drawHorizantalDivider(Canvas canvas, RecyclerView parent, int position) {
        View child = parent.getChildAt(position);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
        final int top = child.getBottom() + params.bottomMargin;
        final int bottom = top + mDivider.getIntrinsicHeight();
        final int left = child.getLeft() - params.leftMargin;
        final int right = child.getRight()+params.rightMargin+mDivider.getIntrinsicWidth();
        mDivider.setBounds(left,top,right,bottom);
        mDivider.draw(canvas);
    }


    //设置ItemView的内嵌偏移长度（inset）
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int spanCount = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount();
        int orientation = ((StaggeredGridLayoutManager) parent.getLayoutManager()).getOrientation();
        int position = parent.getChildLayoutPosition(view);

        if (orientation == OrientationHelper.HORIZONTAL && (position+1) % spanCount == 0){
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
            return;
        }
        if (orientation == OrientationHelper.VERTICAL && (position+1) % spanCount == 0){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
            return;
        }
        outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
    }
}
