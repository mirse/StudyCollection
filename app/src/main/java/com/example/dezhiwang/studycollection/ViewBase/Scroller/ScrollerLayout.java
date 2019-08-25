package com.example.dezhiwang.studycollection.ViewBase.Scroller;//package com.example.dezhiwang.studycollection.ViewBase.Scroller;

import android.content.Context;
import androidx.core.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ScrollerLayout extends ViewGroup {
    private static final String TAG = "ScrollerLayout";
    private int rightBorder;
    private int leftBorder;
    private Scroller mScroller;
    private int mTouchSlop;
    private float mXlastMove;
    private float mXdwon;
    private float mxMove;


    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed){
            int childCount = getChildCount();
            for (int i=0;i<childCount;i++){
                View child = getChildAt(i);
                child.layout(i*child.getMeasuredWidth(),0,(i+1)*child.getMeasuredWidth(),child.getMeasuredHeight());
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mXdwon = ev.getRawX();
                mXlastMove = mXdwon;
                break;
            case MotionEvent.ACTION_MOVE:
                mxMove = ev.getRawX();
                float diff = Math.abs(mxMove - mXdwon);
                mXlastMove = mxMove;
                if (diff > mTouchSlop){
                    Log.i(TAG,"是个滑动操作");
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mxMove = event.getRawX();
                int scrollX = (int) (mXlastMove - mxMove);
                if (getScrollX()+scrollX < leftBorder){
                    Log.i(TAG,"超出左边界");
                    scrollTo(leftBorder,0);
                    return true;
                }
                else if (getScrollX()+getWidth()+scrollX>rightBorder){
                    Log.i(TAG,"超出右边界");
                    scrollTo(rightBorder-getWidth(),0);
                    return true;
                }
                scrollBy(scrollX,0);
                mXlastMove = mxMove;
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG,"getScrollX:"+getScrollX()+" getWidth:"+getWidth());
                int index = (getScrollX() + getWidth() / 2) / getWidth();
                Log.i(TAG,"index:"+index);
                int dx = index*getWidth()-getScrollX();
                mScroller.startScroll(getScrollX(),0,dx,0);
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}


