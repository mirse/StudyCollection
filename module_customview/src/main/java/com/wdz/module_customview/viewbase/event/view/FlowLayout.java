package com.wdz.module_customview.viewbase.event.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class FlowLayout extends ViewGroup {

    private int childWidthSpec;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //提取margin值
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(MATCH_PARENT, MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //每一行宽度
        int lineWidth = 0;
        //每一行高度
        int lineHeight = 0;
        //控件宽度
        int width = 0;
        //控件高度
        int height = 0;
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        for (int i=0;i<getChildCount();i++){
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            int childHeight = childView.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            if (lineWidth+childWidth>measureWidth){
                //需要换行
                width = Math.max(lineWidth,childWidth);
                height += lineHeight;

                //初始化行宽高
                lineHeight = childHeight;
                lineWidth = childWidth;
            }
            else {
                //不需要换行
                lineHeight = Math.max(lineHeight,childHeight);
                lineWidth += childWidth;
            }

            if (i==getChildCount()-1){
                //最后一行
                height += lineHeight;
                width = Math.max(width,lineWidth);
            }


        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY?measureWidth:width,
                heightMode == MeasureSpec.EXACTLY?measureHeight:height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0,left = 0;
        //每一行宽度
        int lineWidth = 0;
        //每一行高度
        int lineHeight = 0;
        for (int i=0;i<getChildCount();i++){
            View childView = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            int childHeight = childView.getMeasuredHeight()+layoutParams.topMargin+layoutParams.bottomMargin;
            if (lineWidth+childWidth>getMeasuredWidth()){
                //换行
                top+=childHeight;
                left = 0;
                lineHeight = childHeight;
                lineWidth = childWidth;
            }
            else{
                lineHeight = Math.max(childHeight,lineHeight);
                lineWidth+= childWidth;
            }

            int lc = left+layoutParams.leftMargin;
            int tc = top+layoutParams.topMargin;
            int rc = lc+childView.getMeasuredWidth();
            int bc = tc+childView.getMeasuredHeight();
            childView.layout(lc,tc,rc,bc);
            left+=childWidth;
        }
    }
}
