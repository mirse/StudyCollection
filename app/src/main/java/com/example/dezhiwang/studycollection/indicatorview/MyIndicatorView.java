package com.example.dezhiwang.studycollection.indicatorview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.dezhiwang.studycollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dezhi.wang on 2018/9/29.
 * 自定义指示栏
 */

public class MyIndicatorView extends View implements ViewPager.OnPageChangeListener {
    private static final String LETTER[] = new String[]{"A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private int mNormalColor;
    private int mTextColor;
    private int mSelectColor;
    private int mSpace;
    private int mStrokeWidth;
    private int mRadius;
    private boolean mIsSwitch;
    private int fillMode;
    private Paint mTextPaint;
    private Paint mCirclePaint;
    private List<Indicator> mIndicators;
    private int mCount=3;//indicator的数量
    private int mSelectPosition=0;//选中的位置
    private FillMode mFillMode = FillMode.NUMBER;
    private ViewPager mViewPager;

    public MyIndicatorView(Context context) {
        this(context,null);
    }

    public MyIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyIndicatorView, defStyleAttr, 0);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.MyIndicatorView_indicator_rad, DisplayUtils.dpToPx(20));
        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.MyIndicatorView_indicator_border_width, DisplayUtils.dpToPx(5));
        mSpace = typedArray.getDimensionPixelSize(R.styleable.MyIndicatorView_indicator_space, DisplayUtils.dpToPx(100));
        mSelectColor = typedArray.getColor(R.styleable.MyIndicatorView_indicator_select_color, Color.RED);
        mTextColor = typedArray.getColor(R.styleable.MyIndicatorView_indicator_text_color, Color.BLACK);
        mNormalColor = typedArray.getColor(R.styleable.MyIndicatorView_indicator_normal_color, Color.GRAY);
        mIsSwitch = typedArray.getBoolean(R.styleable.MyIndicatorView_enableIndicatorSwitch, false);
        fillMode = typedArray.getInt(R.styleable.MyIndicatorView_fill_mode, 2);
        typedArray.recycle();
        init(); 
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCirclePaint.setColor(mNormalColor);
        mCirclePaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mIndicators = new ArrayList<>();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=(mRadius+mStrokeWidth)*2*mCount+mSpace*(mCount-1);
        int height=(mRadius)*2;
        setMeasuredDimension(width,height);
        measureIndicator();
    }

    private void measureIndicator() {
        mIndicators.clear();
        float cx=0;
        for (int i=0;i<mCount;i++){
            Indicator indicator = new Indicator();
            if (i==0){
                cx=mRadius+mStrokeWidth;
            }else{
                cx+=(mRadius+mStrokeWidth)*2+mSpace;
            }
            indicator.cx=cx;
            indicator.cy=getMeasuredHeight()/2;
            mIndicators.add(indicator);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i=0;i<mIndicators.size();i++){
            Indicator indicator = mIndicators.get(i);
            float x = indicator.cx;
            float y = indicator.cy;
            if (mSelectPosition==i){
                mCirclePaint.setStyle(Paint.Style.FILL);
                mCirclePaint.setColor(mSelectColor);
            }
            else{
                mCirclePaint.setColor(mNormalColor);
            }
            canvas.drawLine(x-mRadius,y+mRadius,x+mRadius,y+mRadius,mCirclePaint);
       //     canvas.drawCircle(x,y,mRadius,mCirclePaint);
            if (mFillMode!=FillMode.NONE){
                String text="";
                if (mFillMode==FillMode.LETTER){
                    if (i>=0&&i<LETTER.length){
                        text=LETTER[i];
                    }
                }
                else{
                    text=String.valueOf(i+1);
                }
                Rect bound = new Rect();
                mTextPaint.getTextBounds(text,0,text.length(),bound);
                int textWidth = bound.width();
                int textHeight = bound.height();

                float textStartX = x -textWidth/2;
                float textStartY = y + textHeight / 2;
                canvas.drawText(text,textStartX,textStartY, mTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                handleActionDown(x,y);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void handleActionDown(float x, float y) {
        for (int i=0;i<mIndicators.size();i++){
            Indicator indicator = mIndicators.get(i);
            if (x<(indicator.cx+mRadius+mStrokeWidth)&&x>=(indicator.cx-(mRadius+mStrokeWidth))&&y<(indicator.cy+mRadius+mStrokeWidth)&&y>=(indicator.cy-mRadius-mStrokeWidth)){
                if (mIsSwitch){
                    mViewPager.setCurrentItem(i,true);//false：代表快速切换 true：表示切换速度慢
                }
            }
        }
    }
    public void setUpWithViewPager(ViewPager viewPager){
        releaseViewPager();
        if (viewPager==null){
            return;
        }
        mViewPager=viewPager;
        mViewPager.addOnPageChangeListener(this);
        int count = mViewPager.getAdapter().getCount();
        mCount=3;
        invalidate();
    }

    private void releaseViewPager() {
        if (mViewPager!=null){
            mViewPager.removeOnPageChangeListener(this);
            mViewPager=null;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mSelectPosition=position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setNormalColor(int mNormalColor) {
        this.mNormalColor = mNormalColor;
        init();
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        init();
    }

    public void setSelectColor(int mSelectColor) {
        this.mSelectColor = mSelectColor;
        init();
    }

    public void setSpace(int mSpace) {
        this.mSpace = mSpace;
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        init();
    }

    public void setRadius(int mRadius) {
        this.mRadius = mRadius;
        init();
    }

    public void setIsSwitch(boolean mIsSwitch) {
        this.mIsSwitch = mIsSwitch;
    }

    public void setFillMode(int fillMode) {
        this.fillMode = fillMode;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
        invalidate();
    }

    public void setSelectPosition(int mSelectPosition) {
        this.mSelectPosition = mSelectPosition;
        invalidate();
    }

    public static class Indicator{
        public float cx;
        public float cy;
    }
    public enum FillMode{
        LETTER,
        NUMBER,
        NONE
    }
}
