package com.wdz.module_customview.myview.columnarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wdz.module_customview.R;

/**
 * @author dezhi.wang
 */
public class ColorCircleView extends View {

    private Drawable colorViewSrc;
    /**
     * 圆外边框是否可见
     */
    private boolean isFrameVisible = false;
    /**
     * 圆内边框是否可见
     */
    private boolean isInnerFrameVisible = false;
    private int frameWidth;
    private int frameColor;
    private Paint framePaint;
    private Paint circlePaint;
    private Paint innerPaint;

    public ColorCircleView(Context context) {
        super(context);
        init(context,null);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ColorCircleView);

        colorViewSrc = array.getDrawable(R.styleable.ColorCircleView_colorViewSrc);
        frameWidth = array.getDimensionPixelSize(R.styleable.ColorCircleView_frameWidth, 0);
        frameColor = array.getColor(R.styleable.ColorCircleView_frameColor,0);
        array.recycle();
        initPaint();
    }

    private void initPaint() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        if (colorViewSrc instanceof ColorDrawable){
            ColorDrawable colorDrawable = (ColorDrawable) colorViewSrc;
            circlePaint.setColor(colorDrawable.getColor());
        }

        if (frameWidth!=0){
            framePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            framePaint.setColor(frameColor);
            framePaint.setStyle(Paint.Style.STROKE);
            framePaint.setStrokeWidth(frameWidth);
        }

        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setColor(getContext().getResources().getColor(R.color.white,null));
        innerPaint.setStrokeWidth(dp2px(getContext(),2));
        innerPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 圆填充颜色
     * @param colorViewSrc
     */
    public void setColorViewSrc(Drawable colorViewSrc){
        this.colorViewSrc = colorViewSrc;
        invalidate();
    }

    /**
     * 设置圆外边框是否可见
     * @param isFrameVisible
     */
    public void setColorViewFrameVisible(boolean isFrameVisible){
        this.isFrameVisible = isFrameVisible;
        invalidate();
    }

    /**
     * 设置圆内框是否可见
     * @param isInnerFrameVisible
     */
    public void setColorViewInnerFrameVisible(boolean isInnerFrameVisible){
        this.isInnerFrameVisible = isInnerFrameVisible;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆填充颜色
        canvas.drawCircle((float) getWidth()/2,(float) getHeight()/2,(float) getWidth()/2,circlePaint);

        if (isInnerFrameVisible){
            //绘制圆内框
            canvas.drawCircle((float) getWidth()/2,(float) getHeight()/2,dp2px(getContext(),13),innerPaint);
        }

        if (isFrameVisible){
            //绘制圆外边框
            float radius = Math.min((getWidth()-frameWidth) / 2, (getHeight()-frameWidth) / 2);
            canvas.drawCircle((float) getWidth()/2,(float) getHeight()/2,radius,framePaint);
        }



    }

    /**
     * dp->px
     */
    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
