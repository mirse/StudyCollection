package com.wdz.studycollection.myview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.wdz.studycollection.R;


/**
 * Created by wdz on 2018/8/29.
 */

public class ColorPickerRingRGB extends View {

    private static final String STATE="state";
    private static final String STATE_ANGLE="angle";

    private int circleWidth;
//    private static final int[] COLOR=new int[]{
//            Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN,Color.CYAN,Color.DKGRAY
//    };

//    private static final int[] COLOR=new int[]{
//            Color.BLUE,Color.RED,Color.MAGENTA,Color.YELLOW
//    };

    private static final int[] COLOR = new int[] { 0xFFFF0000, 0xFFFF00FF,
            0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
    //    private static final int[] COLOR = new int[] { 0xFFFF0000, 0xFFFF00FF,
//            0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
    private Paint mPaint;
    private int pointWidth;
    private int circleThick;
    private float offset;
    private Paint pointPaint;
    private float mAngle;
    private float preAngle;
    private float AngleCount;
    private float mSlopX;
    private float mSlopY;
    private boolean isMoving;
    private int mColor;
    private MyColor myColor;
    private OnColorChangeListener colorChangeListener;
    private Rect rect;
    private int width;
    private int height;
    private String text;
    private int level;
    private int prelevel;
    private int mcircleWidth;


//    //1.由于第一次执行newSurface必定为true，需要先创建Surface嘛
//    //为true则会执行else语句，所以第一次执行并不会执行 performDraw方法，即View的onDraw方法不会得到调用
//    //第二次执行则为false，并未创建新的Surface，第二次才会执行 performDraw方法
//        if (!cancelDraw && !newSurface) {
//        if (!skipDraw || mReportNextDraw) {
//            if (mPendingTransitions != null && mPendingTransitions.size() > 0) {
//                for (int i = 0; i < mPendingTransitions.size(); ++i) {
//                    mPendingTransitions.get(i).startChangingAnimations();
//                }
//                mPendingTransitions.clear();
//            }
//
//            performDraw();
//        }
//    } else {
//        //2.viewVisibility是wm.add的那个View的属性，View的默认值都是可见的
//        if (viewVisibility == View.VISIBLE) {
//            // Try again
//            //3.再执行一次 scheduleTraversals，也就是会再执行一次performTraversals
//            scheduleTraversals();
//        } else if (mPendingTransitions != null && mPendingTransitions.size() > 0) {
//            for (int i = 0; i < mPendingTransitions.size(); ++i) {
//                mPendingTransitions.get(i).endChangingAnimations();
//            }
//            mPendingTransitions.clear();
//        }
//    }


    public ColorPickerRingRGB(Context context) {
        super(context);
        init(null,0);
    }

    public ColorPickerRingRGB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public ColorPickerRingRGB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
//        Log.i("text","init!!!!!!!!!!!!!");
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerRingRGB, defStyleAttr, 0);
        final Resources b = getContext().getResources();
        circleWidth = typedArray.getDimensionPixelSize(R.styleable.ColorPickerRingRGB_circle_width, b.getDimensionPixelSize(R.dimen.color_wheel_radius));

        mcircleWidth = circleWidth;
        pointWidth = typedArray.getDimensionPixelSize(R.styleable.ColorPickerRingRGB_point_width, b.getDimensionPixelSize(R.dimen.color_pointer_radius));
        circleThick = typedArray.getDimensionPixelSize(R.styleable.ColorPickerRingRGB_circle_thick, b.getDimensionPixelSize(R.dimen.color_wheel_thickness));
        typedArray.recycle();

        rect = new Rect();
        myColor = new MyColor();
        //AngleCount = (float) (-Math.PI / 2); //  -π/2
        AngleCount = (float) (0); //  -π/2   初始点的位置
        SweepGradient sweepGradient = new SweepGradient(0, 0, COLOR, null);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(sweepGradient);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circleThick);
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //pointPaint.setColor(calculateColor(AngleCount+(float) (-Math.PI / 2)));
        pointPaint.setColor(calculateColor(AngleCount));
        text = myColor.getR()+" "+myColor.getG()+" "+myColor.getB();
        pointPaint.getTextBounds(text,0, text.length(),rect);
        width = rect.right - rect.left;
        height = rect.bottom - rect.height();

    }

    private int calculateColor(float mAngle) {      //1、2象限0-π   3、4象限-π-0
        int v = (int)(mAngle / (Math.PI * 2));
        if (v>0){
            mAngle=(float) (mAngle-Math.PI*2*v);
        }

        float unit = (float)(mAngle / (Math.PI * 2 ));   //0~0.5 -0.5~0

        if (unit<0){
            unit+=1;
        }
        if (unit<=0){
            mColor=COLOR[0];
            myColor=new MyColor();

            myColor.setA(Color.alpha(mColor));
            myColor.setR(Color.red(mColor));
            myColor.setG(Color.green(mColor));
            myColor.setB(Color.blue(mColor));

            return COLOR[0];
        }
        if (unit>=1){
            mColor=COLOR[COLOR.length-1];

            myColor=new MyColor();
            myColor.setA(Color.alpha(mColor));
            myColor.setR(Color.red(mColor));
            myColor.setG(Color.green(mColor));
            myColor.setB(Color.blue(mColor));

            return COLOR[COLOR.length-1];
        }
        float p = unit * (COLOR.length - 1);  //unit  0~1    p 0~5

        int i = (int) p;
        level = i;
        p-=i;   //p 0~1

        int c0=COLOR[i];
        int c1=COLOR[i+1];
        int a=ave(Color.alpha(c0),Color.alpha(c1),p);
        int r=ave(Color.red(c0),Color.red(c1),p);
        int g=ave(Color.green(c0),Color.green(c1),p);
        int b=ave(Color.blue(c0),Color.blue(c1),p);
        mColor=Color.argb(a,r,g,b);
        myColor=new MyColor();
        myColor.setA(a);
        myColor.setR(r);
        myColor.setG(g);
        myColor.setB(b);

        return mColor;
    }

    private int ave(int s, int s1, float p) {
        return s+Math.round((s1-s)*p);
    }


    //当父View是Relativelayout时，
    //当子view是textview时且 android:layout_width="wrap_content" ，mParent.requestLayout()，迫使父View重新onmeasure,
    //父view measureChild,子View 重新onmeasure

    /* 最简单的映射关系是：
        * wrap_parent -> MeasureSpec.AT_MOST
        * match_parent -> MeasureSpec.EXACTLY
        *具体值 -> MeasureSpec.EXACTLY
        但是上面代码由于放在 RelativeLayout 中，RelativeLayout 是一个比较复杂的 ViewGroup，
        其中子 view 的大小不仅跟 layout_width、layout_height 属性相关，
        还更很多属性有关系，所以会改变上述映射情况，使结果变得特别复杂。


      MeasureSpec不是唯一由view的LayoutParams来确定的，view的LayoutParams需要和父容器一起才能决定view的MeasureSpec，
      从而进一步决定view的宽/高关系。（参考开发艺术探索P180页代码），只要提供父容器的MeasureSpec和子元素的LayoutParams，
      就可以快速的确定出子元素的MeasureSpec了，有了MeasureSpec就可以进一步确定出子元素测量后的大小了

      childDimension  父view getChildMeasureSpec决定
        */

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        Log.i("text","onLayout");
//    }

//
//    @Override
//    public void layout(int l, int t, int r, int b) {
//
//        Log.i("text","layout");
//        super.layout(l, t, r, b);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("text","onmeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int RealSize=2*(circleWidth+pointWidth);
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode==MeasureSpec.EXACTLY){//match
            width=widthSize;    //自定义View wrap  不变
        }else if (widthMode==MeasureSpec.AT_MOST){
            width=RealSize;
        }else{
            width=RealSize;
        }
        if (heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else if (heightMode==MeasureSpec.AT_MOST){
            height=RealSize;  //自定义View wrap
        }else{
            height=RealSize;
        }
        int min = Math.min(width, height);
        offset = min * 0.5f;

        mcircleWidth=min/2-circleThick-pointWidth/2;
        setMeasuredDimension(min,min);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // float[] pointerPosition = calculatePointerPosition(AngleCount+(float) (-Math.PI / 2));
        float[] pointerPosition = calculatePointerPosition(AngleCount);
        canvas.translate(offset,offset);

        canvas.drawCircle(0,0,mcircleWidth,mPaint);
        //canvas.drawCircle(0,0,circleWidth,mPaint);
        canvas.drawCircle(pointerPosition[0],pointerPosition[1],pointWidth,pointPaint);
        pointPaint.setTextSize(40);

        text = myColor.getR()+" "+myColor.getG()+" "+myColor.getB();
        pointPaint.getTextBounds(text,0, text.length(),rect);
        width = rect.right - rect.left;
        height = rect.bottom - rect.height();


        canvas.drawText(text,-width/2,-height/2,pointPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        float x = event.getX()-offset;
        float y = event.getY() - offset;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //  float[] pointerPosition = calculatePointerPosition(AngleCount+(float) (-Math.PI / 2));  //计算点坐标
                float[] pointerPosition = calculatePointerPosition(AngleCount);  //计算点坐标
                preAngle=(float)Math.atan2(y-mSlopY,x-mSlopX);
                if (mAngle<0){
                    preAngle+=Math.PI*2;
                }
                //  Log.i("text",pointerPosition[0]+" x"+pointerPosition[1]+" y");
                if (x>=pointerPosition[0]-(pointWidth-circleThick)/2&&x<=pointerPosition[0]+circleThick+(pointWidth-circleThick)/2&&y>=pointerPosition[1]-(pointWidth)/2&&y<=pointerPosition[1]+circleThick+(pointWidth)/2){
                    mSlopX = x-pointerPosition[0]; //触摸点与点坐标的距离
                    mSlopY = y-pointerPosition[1];
                    isMoving = true;
                    invalidate();
                }
//                else if (Math.sqrt(x*x+y*y)>=circleWidth&&Math.sqrt(x*x+y*y)<circleWidth+circleThick){
//                    isMoving=true;
//                    invalidate();
//                }
                else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //  Log.i("text",AngleCount+"===AngleCount");

                if (isMoving){
                    mAngle=(float)Math.atan2(y-mSlopY,x-mSlopX);   //1、2象限0-π   3、4象限-π-0
                    //   pointPaint.setColor(calculateColor(AngleCount+(float) (-Math.PI / 2)));
                    pointPaint.setColor(calculateColor(AngleCount));
                    if (mAngle<0){
                        mAngle+=Math.PI*2;
                    }

                    //防止0~2π的跳变
                    if (preAngle > Math.toRadians(270) && mAngle < Math.toRadians(90))
                    {
                        Log.i("text","正滑一圈---");
                        preAngle -= 2 * Math.PI;
                    }
                    else if (preAngle < Math.toRadians(90) && mAngle > Math.toRadians(270))
                    {
                        Log.i("text","回滑一圈---");
                        preAngle = (float) (mAngle + (mAngle - 2 * Math.PI) - preAngle);
                    }
                    AngleCount+=(mAngle-preAngle);
                    preAngle=mAngle;
                    if (AngleCount<0){
                        AngleCount=0;
                    }
                    else if (AngleCount>Math.PI*2*3){
                        AngleCount=(float) (Math.PI*2*3);
                    }
                    invalidate();
                    // colorChangeListener.onRGBChange(AngleCount);
                }
                break;
            case MotionEvent.ACTION_UP:
                isMoving=false;
                invalidate();
                break;
        }
        return true;
    }



    private float[] calculatePointerPosition(float mAngle) {
        float x = (float) (mcircleWidth * Math.cos(mAngle));
        float y = (float) (mcircleWidth * Math.sin(mAngle));
//        float x = (float) (circleWidth * Math.cos(mAngle));
//        float y = (float) (circleWidth * Math.sin(mAngle));
        return new float[]{ x , y };
    }

    public void setonColorChange(OnColorChangeListener colorChangeListener){
        this.colorChangeListener=colorChangeListener;
    }
    public interface OnColorChangeListener{
        void onRGBChange(float mAngle);
        // void onRGBChange(boolean success);
    }

    //防止旋转设备数据丢失
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE,parcelable);
        bundle.putFloat(STATE_ANGLE,mAngle);
        return bundle;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        Parcelable parcelable = bundle.getParcelable(STATE);
        super.onRestoreInstanceState(parcelable);
        AngleCount = bundle.getFloat(STATE_ANGLE);
        pointPaint.setColor(calculateColor(AngleCount+(float) (-Math.PI / 2)));
    }
}
