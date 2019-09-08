package com.example.dezhiwang.studycollection.myview;

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

import com.example.dezhiwang.studycollection.R;


/**
 * Created by wdz on 2018/9/14.
 */

public class ColorPickerHSV extends View {

    private Paint mPaint;
    private static final int[] COLOR=new int[]{
            Color.RED,Color.BLUE,Color.GREEN,Color.RED
    };
//        private static final int[] COLOR = new int[] { 0xFFFF0000, 0xFFFF00FF,
//            0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
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


        SweepGradient sweepGradient = new SweepGradient(0, 0, colors, null);
        RadialGradient radialGradient = new RadialGradient(0, 0, circleWidth, Color.WHITE, 0x00FFFFFF, Shader.TileMode.CLAMP);
        mPaint.setShader(sweepGradient);
        sPaint.setShader(radialGradient);
        iPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int RealSize=2*circleWidth+2*innerWidth;
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }
        else if (widthMode==MeasureSpec.AT_MOST){
            width=RealSize;
        }else{
            width=widthSize;
        }
        if (heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }
        else if (heightMode==MeasureSpec.AT_MOST){
            height=RealSize;
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
        canvas.drawCircle(centerX,centerY,innerWidth,iPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX()-offset;
        int y = (int) event.getY()-offset;
        r = Math.sqrt(x * x + y * y);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                //    Log.i("text","distance==="+distance);
                if (distance<30){//点击圆内圆点位置
                    //   Log.i("text","可滑动----");
                    isMove=true;
                    lastX = x;
                    lastY = y;
                }
                else if(r<circleWidth){//点击圆内非圆点位置
                    centerX=x;
                    centerY=y;
                    lastX = x;
                    lastY = y;
                    isMove=true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                // Log.i("text","r==="+r+"circleWidth=="+circleWidth);
                if (isMove){
                    if (r >circleWidth){//滑动半径大于实际半径
                        x*=circleWidth/ r;
                        y*=circleWidth/ r;
                        centerX=x;
                        centerY=y;
                        lastX=x;
                        lastY=y;
                        invalidate();
                    }else {//处于圆内
                        offsetX = x - lastX;
                        offsetY = y - lastY;
                        centerX += offsetX;
                        centerY += offsetY;
                        lastX = x;
                        lastY = y;
                        invalidate();
                    }
                    float[] hsv={0,0,1};
                    hsv[0]= (float) (Math.atan2(y,x)/Math.PI*180f)+180;
                    hsv[1]=Math.max(0f,Math.min(1f,(float) (r/circleWidth)));
                    int color = Color.HSVToColor(hsv);
                    iPaint.setColor(color);
                    invalidate();
                    int red = (color & 0xff0000) >> 16;
                    int green = (color & 0x00ff00) >> 8;
                    int blue = (color & 0x0000ff);
                    Log.i("text","red="+red+" green"+green+" blue"+blue);
                }
                break;
            case MotionEvent.ACTION_UP:
                isMove=false;
                break;
                default:break;
        }
        return true;
    }


}
