package com.wdz.studycollection.myview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.wdz.studycollection.R;


/**
 *
 * @author wdz
 * @date 2018/9/14
 */

public class ColorPickerHSV extends View {
    private static final String TAG = "ColorPickerHSV";
    private Paint mPaint;

    private static final int[] COLOR = new int[]{
            Color.rgb(255, 0, 0), Color.rgb(255, 255, 0),
            Color.rgb(0, 255, 0), Color.rgb(0, 255, 255),
            Color.rgb(0, 0, 255), Color.rgb(255, 0, 255),
            Color.rgb(255, 0, 0)



//            Color.rgb(0, 255, 255), Color.rgb(0, 0, 255),
//            Color.rgb(255, 0, 255), Color.rgb(255, 0, 0),
//            Color.rgb(255, 255, 0), Color.rgb(0, 255, 0),
//            Color.rgb(0, 255, 255)
    };

    private int circleWidth;
    private int offsetX;
    private int offsetY;
    private Paint sPaint;
    private Paint iPaint;
    private int centerX=0;
    private int centerY=0;
    private int innerWidth;
    private boolean isMove=false;
    private int lastY;
    private int lastX;
    private int offset;
    private double r;
    private onMoveListener onMoveListener;
    private int red;
    private int green;
    private int blue;
    private boolean mIsVisible;

    public ColorPickerHSV(Context context) {
        super(context);
        init(null,0);
    }

    public ColorPickerHSV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public ColorPickerHSV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPickerHSV, defStyleAttr, 0);
        Resources resources = getContext().getResources();
        circleWidth = typedArray.getDimensionPixelSize(R.styleable.ColorPickerHSV_color_rad, resources.getDimensionPixelSize(R.dimen.color_wheel_radius));
        innerWidth = typedArray.getDimensionPixelSize(R.styleable.ColorPickerHSV_inner_rad, resources.getDimensionPixelSize(R.dimen.color_pointer_radius));
        typedArray.recycle();
        initPaint();

    }

    /**
     * 初始化paint
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //绘制颜色
        int colorCount = 12;
        int colorAngleStep = 360 / 12;
        int colors[] = new int[colorCount];
        float hsv[] = new float[]{0f, 1f, 1f};
        for (int i = 0; i < colors.length; i++) {
            hsv[0] = (i * colorAngleStep + 180) % 360;
            colors[i] = Color.HSVToColor(hsv);
        }
        SweepGradient sweepGradient = new SweepGradient(0, 0, COLOR, null);
        RadialGradient radialGradient = new RadialGradient(0, 0, circleWidth, Color.WHITE, 0x00FFFFFF, Shader.TileMode.CLAMP);
        mPaint.setShader(sweepGradient);
        sPaint.setShader(radialGradient);
        iPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int realSize=2*circleWidth+2*innerWidth;
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode== MeasureSpec.EXACTLY){
            width=widthSize;
        }
        else if (widthMode== MeasureSpec.AT_MOST){
            width=realSize;
        }else{
            width=widthSize;
        }
        if (heightMode== MeasureSpec.EXACTLY){
            height=heightSize;
        }
        else if (heightMode== MeasureSpec.AT_MOST){
            height=realSize;
        }else {
            height = heightSize;
        }
        int min = Math.min(width, height);
        offset = min/2;
        setMeasuredDimension(min,min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(offset,offset);
        canvas.drawCircle(0,0,circleWidth,mPaint);
        canvas.drawCircle(0,0,circleWidth,sPaint);
        if (mIsVisible){
            canvas.drawCircle(centerX,centerY,innerWidth,iPaint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX()-offset;
        int y = (int) event.getY()-offset;
        r = Math.sqrt(x * x + y * y);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                if (distance<30){
                    //点击圆内圆点位置
                    isMove=true;
                    lastX = x;
                    lastY = y;

                }
                else if(r<circleWidth){
                    //点击圆内非圆点位置
                    centerX=x;
                    centerY=y;
                    lastX = x;
                    lastY = y;
                    isMove=true;
                    invalidate();
                }
                if (onMoveListener!=null){
                    onMoveListener.onMoveStart();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (isMove){
                    if (r >circleWidth){
                        //滑动半径大于实际半径
                        x*=circleWidth/ r;
                        y*=circleWidth/ r;
                        centerX=x;
                        centerY=y;
                        lastX=x;
                        lastY=y;
                        invalidate();
                    }else {
                        //处于圆内
                        offsetX = x - lastX;
                        offsetY = y - lastY;
                        centerX += offsetX;
                        centerY += offsetY;
                        lastX = x;
                        lastY = y;
                        invalidate();
                    }
                    float[] hsv={0,0,1};
                    hsv[0]= (float) (Math.atan2(y,x)/ Math.PI*180f) ;
                    hsv[1]= Math.max(0f, Math.min(1f,(float) (r/circleWidth)));
                    int color = Color.HSVToColor(hsv);
                    invalidate();
                    red = (color & 0xff0000) >> 16;
                    green = (color & 0x00ff00) >> 8;
                    blue = (color & 0x0000ff);
                    if (onMoveListener!=null) {
                        onMoveListener.onMove(red, green, blue);
                    }
                    Log.i(TAG,"red="+ red +" green"+ green +" blue"+ blue);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (onMoveListener!=null) {
                    onMoveListener.onMoveUp(red, green, blue);
                }
                isMove=false;
                break;

            default:
                break;
        }
        return true;
    }
    public interface onMoveListener{
        void onMoveStart();
        void onMove(int r, int g, int b);
        void onMoveUp(int r,int g,int b);
    }


    public void setOnMoveListener(onMoveListener listener){
        this.onMoveListener = listener;
    }


    /**
     * 圆点指示器是否可见
     * @param isVisible
     */
    public void setPointVisible(boolean isVisible){
        mIsVisible = isVisible;
        invalidate();
    }

    /**
     * 根据rgb确定圆点指示器位置
     */
    public void setPointPosition(int rgb){
        //hsv h:色调（0°~360°） s:饱和度（0~1） v:明度
        Log.i(TAG, "rgb: "+rgb);
        float[] hsv = new float[3];
        Color.colorToHSV(rgb,hsv);
        Log.i(TAG, "hsv[0]: "+hsv[0]+" hsv[1]:"+hsv[1]+" hsv[2]:"+hsv[2]);
        float centerX = 0;
        float centerY = 0;
        float radius = hsv[1] * Math.min(centerX, centerY);
        Log.i(TAG, "setPointPosition radius: "+radius+" circleWidth:"+circleWidth);
        //Math.toRadians将角度转换成弧度
        int pointX = (int) (radius * Math.cos(Math.toRadians(hsv[0])) + centerX);
        int pointY = (int) (-radius * Math.sin(Math.toRadians(hsv[0])) + centerY);
        this.centerX = pointX;
        this.centerY = pointY;
        Log.i(TAG, "centerX: "+centerX+" centerY:"+centerY);
        invalidate();
    }


}
