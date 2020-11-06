package com.wdz.module_customview.myview.columnarView;

import android.content.Context;
import android.content.res.TypedArray;
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
    private int innerRadius;

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
        innerRadius = array.getDimensionPixelSize(R.styleable.ColorCircleView_innerRadius, 0);
        colorViewSrc = array.getDrawable(R.styleable.ColorCircleView_colorViewSrc);

        array.recycle();
        initPaint();
    }

    private void initPaint() {
        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (colorViewSrc instanceof ColorDrawable){
            //circlePaint.setColor();
        }

    }
}
