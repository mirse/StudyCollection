package com.wdz.module_customview.myview.columnarView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.wdz.module_customview.R;
import com.wdz.module_customview.myview.colorpick.ColorPickerHSV;

public class ColumnarView extends View {
    private final String TAG = this.getClass().getSimpleName();
    private int radius;
    private Paint framePaint;
    private Paint circlePaint;
    private Paint gradientPaint;
    private int circleRadius = dp2px(getContext(), (float) 8);
    private float centerY=circleRadius;
    private onMoveListener onMoveListener;
    private int gradientStartColor;
    private int gradientEndColor;
    private int gradientCenterColor;
    private LinearGradient gradient;
    private float viewHeight;
    private float viewWidth;

    public ColumnarView(Context context) {
        super(context);
        init(context,null);
    }

    public ColumnarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ColumnarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ColumnarView);
        radius = array.getDimensionPixelSize(R.styleable.ColumnarView_radius, 0);
        gradientStartColor = array.getColor(R.styleable.ColumnarView_gradientColorStart, 0);
        gradientCenterColor = array.getColor(R.styleable.ColumnarView_gradientColorCenter, 0);
        gradientEndColor = array.getColor(R.styleable.ColumnarView_gradientColorEnd, 0);
        viewHeight = array.getDimension(R.styleable.ColumnarView_viewHeight, 0);
        viewWidth = array.getDimension(R.styleable.ColumnarView_viewWidth, 0);
        array.recycle();
        initPaint();
    }

    private void initPaint() {
        framePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setColor(getResources().getColor(R.color.color_DCDCDC,null));
        framePaint.setStrokeWidth(dp2px(getContext(),1));

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(getResources().getColor(R.color.color_54565A,null));
        circlePaint.setStrokeWidth(dp2px(getContext(),1));

    }

    /**
     * 设置渐变尾部颜色，头部默认白色
     * @param gradientEndColor
     */
    public void setGradientEndColor(int gradientEndColor){
        this.gradientEndColor = gradientEndColor;
        invalidate();
    }

    /**
     * 根据百分比确定圆环位置
     * @param percent 0~100
     */
    public void setPercent(int percent){
        Log.i(TAG, "setPercent: getWidth:"+getMeasuredWidth()+" getHeight:"+getMeasuredHeight());
        this.centerY = (float) ((((percent-1)/0.99)/100)*(viewHeight-2*circleRadius-dp2px(getContext(),2)));
        if (centerY>viewHeight-circleRadius-dp2px(getContext(), 1)){
            this.centerY = viewHeight-circleRadius-dp2px(getContext(), 1);
        }
        else if (centerY<circleRadius+dp2px(getContext(), 1)){
            this.centerY = circleRadius+dp2px(getContext(), 1);
        }
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: getWidth:"+getWidth()+" getHeight:"+getHeight());
        float width = viewWidth;
        float height = viewHeight-circleRadius;


        if (gradientCenterColor==0){
            gradient = new LinearGradient((float) width/2, 0, (float) width/2, height,gradientStartColor,gradientEndColor, Shader.TileMode.CLAMP);
        }
        else{
            gradient = new LinearGradient((float) width/2, 0, (float) width/2, height,new int[]{gradientStartColor,gradientCenterColor,gradientEndColor},null, Shader.TileMode.CLAMP);
        }
        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gradientPaint.setShader(gradient);
        RectF rectF = new RectF((float) dp2px(getContext(), 1) / 2, (float) dp2px(getContext(), 1) / 2+circleRadius,
                width - (float) dp2px(getContext(), 1) / 2, height - (float) dp2px(getContext(), 1) / 2);
        //绘制渐变色
        canvas.drawRoundRect(rectF,radius,radius,gradientPaint);

        //绘制边框
        canvas.drawRoundRect(rectF, radius, radius, framePaint);

        //绘制可滑动圆环
        canvas.drawCircle((float) width/2,centerY,circleRadius,circlePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                centerY = y;
                if (y>viewHeight-circleRadius-dp2px(getContext(), 1)){
                    centerY = viewHeight-circleRadius-dp2px(getContext(), 1);
                }
                else if (y<circleRadius+dp2px(getContext(), 1)){
                    centerY = circleRadius+dp2px(getContext(), 1);
                }
                invalidate();


                break;
            case MotionEvent.ACTION_MOVE:
                centerY = y;
                if (y>viewHeight-circleRadius-dp2px(getContext(), 1)){
                    centerY = viewHeight-circleRadius-dp2px(getContext(), 1);
                }
                else if (y<circleRadius+dp2px(getContext(), 1)){
                    centerY = circleRadius+dp2px(getContext(), 1);
                }
                invalidate();
                if (onMoveListener!=null){
                    int percent = (int) ((centerY - (circleRadius+dp2px(getContext(), 1)))/(viewHeight-2*circleRadius-dp2px(getContext(),2))*100);

                    onMoveListener.onMoveUp((int) (0.99*percent+1));
                }

                break;
            case MotionEvent.ACTION_UP:

                if (onMoveListener!=null){
                    int percent = (int) ((centerY - (circleRadius+dp2px(getContext(), 1)))/(viewHeight-2*circleRadius-dp2px(getContext(),2))*100);
                    onMoveListener.onMoveUp((int) (0.99*percent+1));
                }

                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        Log.i(TAG, "onMeasure: ");

    }

    /**
     * 设置触摸监听
     * @param listener
     */
    public void setOnMoveListener(onMoveListener listener){
        this.onMoveListener = listener;
    }

    public interface onMoveListener{

        /**触摸滑动
         * @param percent
         */
        void onMove(int percent);

        /**
         * 滑动抬手
         * @param percent
         */
        void onMoveUp(int percent);
    }


    /**
     * dp->px
     */
    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
