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

    private int radius;
    private Paint framePaint;
    private Paint circlePaint;
    private Paint gradientPaint;
    private int circleRadius = dp2px(getContext(), (float) 8.5);
    private float centerY=circleRadius;
    private onMoveListener onMoveListener;
    private int gradientStartColor;
    private int gradientEndColor;
    private int gradientCenterColor;
    private LinearGradient gradient;

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



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight()-circleRadius;


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
        canvas.drawRoundRect(rectF,radius,radius,gradientPaint);

        canvas.drawRoundRect(rectF, radius, radius, framePaint);


        canvas.drawCircle((float) width/2,centerY,circleRadius,circlePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                centerY = y;
                if (y>getHeight()-circleRadius){
                    centerY = getHeight()-circleRadius;
                }
                else if (y<circleRadius){
                    centerY = circleRadius;
                }
                invalidate();


                break;
            case MotionEvent.ACTION_MOVE:
                centerY = y;
                if (y>getHeight()-circleRadius){
                    centerY = getHeight()-circleRadius;
                }
                else if (y<circleRadius){
                    centerY = circleRadius;
                }
                invalidate();
                if (onMoveListener!=null){
                    onMoveListener.onMove((int) ((centerY-circleRadius)/(getHeight()-2*circleRadius)*100));
                }

                break;
            case MotionEvent.ACTION_UP:

                if (onMoveListener!=null){
                    onMoveListener.onMoveUp((int) ((centerY-circleRadius)/(getHeight()-2*circleRadius)*100));
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


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int realSize=2*circleRadius+measureHeight+dp2px(getContext(),2);


        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY?measureWidth:measureWidth,
                heightMode == MeasureSpec.EXACTLY?measureHeight:realSize);

    }

    /**
     * 设置触摸监听
     * @param listener
     */
    public void setOnMoveListener(onMoveListener listener){
        this.onMoveListener = listener;
    }

    public interface onMoveListener{
        void onMoveStart();
        void onMove(int percent);
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
