package com.example.dezhiwang.studycollection.TurnPage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.Scroller;

import com.example.dezhiwang.studycollection.Utils.ViewUtils;

import javax.xml.parsers.FactoryConfigurationError;

/**
 * Created by dezhi.wang on 2018/9/27.
 */

public class CoverPageView extends View {
    private PageFctory pageFctory;
    private int pageNum;
    private Bitmap currentPage;
    private Bitmap nextPage;
    private Bitmap previousPage;
    private int defaultWidth;
    private int defaultHeight;
    public static final int TOUCH_MIDDLE=0;
    public static final int TOUCH_LEFT=1;
    public static final int TOUCH_RIGHT=2;
    private MyPoint touchPoint;
    private int viewWidth;
    private int viewHeight;
    private int touchStyle;
    private float xDonwx;
    private float scrollPageLeft;

    private int pageState;//翻页状态，用于限制翻页动画结束前的触摸操作
    public static final int PAGE_STAY = 0;//处于静止状态
    public static final int PAGE_NEXT = 1;//翻至下一页
    public static final int PAGE_PREVIOUS = 2;//翻至上一页
    private Scroller mScroller;
    private int scrollTime;


    public CoverPageView(Context context) {
        super(context);
        init(context);
    }

    public CoverPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        defaultWidth = 600;
        defaultHeight = 1000;
        pageNum = 1;
        scrollTime = 300;
        scrollPageLeft= 0;
        touchStyle = TOUCH_RIGHT;
        touchPoint = new MyPoint(-1, -1);
        pageState=PAGE_STAY;
        mScroller = new Scroller(context, new LinearInterpolator());
    }
    /**
     * 设置工厂类
     * @param factory
     */
    public void setPageFactory(final PageFctory factory){
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {//	当视图树将要被绘制时，会调用的接口
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                if (factory.hasData){
                    pageFctory=factory;
                    pageFctory.drawCurrent(currentPage,pageNum);
                   // pageFctory.drawNext(nextPage,pageNum);
                    postInvalidate();
                }
                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = ViewUtils.measureSize(defaultHeight, heightMeasureSpec);
        int width = ViewUtils.measureSize(defaultWidth, widthMeasureSpec);
        setMeasuredDimension(width,height);
        viewWidth = width;
        viewHeight = height;
        currentPage=Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
        nextPage = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
        previousPage=Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pageFctory!=null){
//            drawCurrentPage(canvas);
            if (touchPoint.x==-1&&touchPoint.y==-1){
                drawCurrentPage(canvas);
                pageState=PAGE_STAY;
            }
            else{
                if (touchStyle==TOUCH_RIGHT){
                    drawCurrentPage(canvas);
                    drawPreviousPage(canvas);
                }
                else{
                    drawNextPage(canvas);
                    drawCurrentPage(canvas);
                }

            }
        }
    }
    private void drawPreviousPage(Canvas canvas){
        canvas.drawBitmap(previousPage, scrollPageLeft, 0,null);
    }

    private void drawNextPage(Canvas canvas) {
        canvas.drawBitmap(nextPage,0,0,null);
    }

    private void drawCurrentPage(Canvas canvas) {
        if(touchStyle == TOUCH_RIGHT){
            canvas.drawBitmap(currentPage, 0, 0,null);
        }else if(touchStyle == TOUCH_LEFT){
            canvas.drawBitmap(currentPage, scrollPageLeft, 0,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        if (pageState==PAGE_STAY) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDonwx = x;
                    if (x <= viewWidth / 3) {
                        touchStyle = TOUCH_LEFT;
                        if(pageNum>1){

                            pageNum--;
                            Log.i("text","pageNum==========="+pageNum);
                            pageFctory.drawCurrent(currentPage,pageNum);
                            pageFctory.drawNext(nextPage,pageNum);
                            pageNum++;
                            Log.i("text","pageNum==========="+pageNum);
                        }
                    } else if (x > viewWidth * 2 / 3) {
                        touchStyle = TOUCH_RIGHT;
                        if(pageNum<pageFctory.pageTotal){

                            pageNum++;
                            Log.i("text","pageNum==========="+pageNum);
                            pageFctory.drawPrevious(previousPage,pageNum);
                            pageFctory.drawCurrent(currentPage,pageNum);
                            pageNum--;
                            Log.i("text","pageNum==========="+pageNum);
                        }
                    } else {
                        touchStyle = TOUCH_MIDDLE;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                   // scrollPage(x, y);
                    float moveX = event.getX();
                    float offsetX = moveX - xDonwx;
                    if(touchStyle == TOUCH_LEFT&& offsetX>0){
                        if(pageNum>1){
                            scrollPage(x,y);
                        }
                    }else if(touchStyle == TOUCH_RIGHT&&offsetX<0){
                        if(pageNum<pageFctory.pageTotal){
                            scrollPage(x,y);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    autoScroll();
                    break;
            }
        }
        return true;
    }

    private void autoScroll() {
        Log.i("text","pageNum------------"+pageNum);
        Log.i("text","pageFctory.pageTotal------------"+pageFctory.pageTotal);
        switch (touchStyle){
            case TOUCH_LEFT:
                if(pageNum>=1){
                    autoScrollToPreviousPage();
                }
                break;
            case TOUCH_RIGHT:
                if(pageNum<=pageFctory.pageTotal){
                    autoScrollToNextPage();
                }
                break;
        }
    }
    /**
     * 自动完成翻到下一页操作
     */
    private void autoScrollToNextPage(){
        pageState = PAGE_NEXT;

        int dx,dy;
        dx = (int) -(viewWidth+scrollPageLeft);
        dy = (int) (touchPoint.y);

        int time =(int) ((1+scrollPageLeft/viewWidth) * scrollTime);
        mScroller.startScroll((int) (viewWidth+scrollPageLeft), (int) touchPoint.y, dx, dy, time);
    }

    /**
     * 自动完成返回上一页操作
     */
    private void autoScrollToPreviousPage(){
        pageState = PAGE_PREVIOUS;

        int dx,dy;
        dx = (int) -scrollPageLeft;
        dy = (int) (touchPoint.y);

        int time =(int) (-scrollPageLeft/viewWidth * scrollTime);
        mScroller.startScroll((int) (viewWidth+scrollPageLeft), (int) touchPoint.y, dx, dy, time);
    }

    /**
     * 取消翻页动画,计算滑动位置与时间
     */
    private void startCancelAnim(){
        int dx,dy;
        dx = (int) (viewWidth-1-touchPoint.x);
        dy = (int) (touchPoint.y);
        mScroller.startScroll((int) touchPoint.x, (int) touchPoint.y, dx, dy, scrollTime);
    }

    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {//滚动是否完成
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();
            scrollPageLeft = 0 - (viewWidth - x);

            if (mScroller.getFinalX() == x && mScroller.getFinalY() == y){//getFinalX返回滚动结束的位置
                Log.i("text","compute.....");
                if(touchStyle == TOUCH_RIGHT){
                    pageNum++;
                }else if(touchStyle == TOUCH_LEFT){
                    pageNum--;
                }
                resetView();
            }
            postInvalidate();
        }
    }
    private void resetView(){
        scrollPageLeft = 0;
        touchPoint.x = -1;
        touchPoint.y = -1;
    }

    private void scrollPage(float x, float y) {
        touchPoint.x=x;
        touchPoint.y=y;
        if (touchStyle==TOUCH_RIGHT){//向左滑动

            scrollPageLeft = touchPoint.x - xDonwx;
            Log.i("text","right-------scrollPageLeft==="+scrollPageLeft);
        }
        else if (touchStyle==TOUCH_LEFT){//向右滑动

            scrollPageLeft=-viewWidth+(touchPoint.x-xDonwx);//改变起始位置
            Log.i("text","left---------scrollPageLeft==="+scrollPageLeft);
        }
        if (scrollPageLeft>0){
            scrollPageLeft=0;
        }
        postInvalidate();

    }
}
 class MyPoint{
    public MyPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    float x;
    float y;
}
