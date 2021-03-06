package com.wdz.module_customview.viewbase.scroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.wdz.module_customview.R;


public class ShadowCardView extends FrameLayout {
    private static final String TAG = "ShadowCardView";
    private static final int CORNERS_RADIUS = 0;
    private static final int SHADOW_RADIUS = 20;
    private static final int SHADOW_TOP_HEIGHT = 15;
    private static final int SHADOW_LEFT_HEIGHT = 15;
    private static final int SHADOW_RIGHT_HEIGHT = 15;
    private static final int SHADOW_BOTTOM_HEIGHT = 15;
    private static final int SHADOW_OFFSET_Y = 5;
    private static final int SHADOW_OFFSET_X = 0;

    /**
     * 圆角半径
     */
    private int cornersRadius;
    /**
     * 阴影颜色
     */
    private int shadowColor;
    /**
     * view颜色
     */
    private int cardColor;
    /**
     * 阴影模糊度
     */
    private int shadowRadius;
    /**
     * 阴影偏移y轴距离
     */
    private int shadowOffsetY;
    /**
     * 阴影偏移x轴距离
     */
    private int shadowOffsetX;
    /**
     * 阴影距上部分距离
     */
    private int shadowTopHeight;
    /**
     * 阴影距左部分距离
     */
    private int shadowLeftHeight;
    /**
     * 阴影距右部分距离
     */
    private int shadowRightHeight;
    /**
     * 阴影距下部分距离
     */
    private int shadowBottomHeight;
    private Drawable src;
    private Paint bitmapPaint;
    private Paint shadowDownPaint;
    private Paint shadowUpPaint;
    private boolean isSetShadow = false;

    public ShadowCardView(Context context) {
        this(context, null);
    }

    public ShadowCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 设置是否显示阴影
     * @param isSetShadow
     */
    public void setShadowColor(boolean isSetShadow) {
        this.isSetShadow = isSetShadow;
        invalidate();
    }

    public void setBitmap(Drawable drawable) {
        this.src = drawable;
        invalidate();
    }


    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowCardView);
        cornersRadius = typedArray.getDimensionPixelSize(R.styleable.ShadowCardView_cornersRadius, CORNERS_RADIUS);
        shadowColor = typedArray.getColor(R.styleable.ShadowCardView_shadowColor, getResources().getColor(R.color.shadow_color));
        cardColor = typedArray.getColor(R.styleable.ShadowCardView_cardColor, getResources().getColor(R.color.black_deep));
        shadowTopHeight = typedArray.getDimensionPixelSize(R.styleable.ShadowCardView_shadowTopHeight, dp2px(context, SHADOW_TOP_HEIGHT));
        shadowLeftHeight = typedArray.getDimensionPixelSize(R.styleable.ShadowCardView_shadowLeftHeight, dp2px(context, SHADOW_LEFT_HEIGHT));
        shadowRightHeight = typedArray.getDimensionPixelSize(R.styleable.ShadowCardView_shadowRightHeight, dp2px(context, SHADOW_RIGHT_HEIGHT));
        shadowBottomHeight = typedArray.getDimensionPixelSize(R.styleable.ShadowCardView_shadowBottomHeight, dp2px(context, SHADOW_BOTTOM_HEIGHT));
        shadowOffsetX = typedArray.getDimensionPixelSize(R.styleable.ShadowCardView_shadowOffsetX, dp2px(context, SHADOW_OFFSET_X));
        shadowOffsetY = typedArray.getDimensionPixelSize(R.styleable.ShadowCardView_shadowOffsetY, dp2px(context, SHADOW_OFFSET_Y));
        shadowRadius = typedArray.getInteger(R.styleable.ShadowCardView_shadowRadius, SHADOW_RADIUS);
        src = typedArray.getDrawable(R.styleable.ShadowCardView_src);

        typedArray.recycle();
        setPadding(shadowLeftHeight, shadowTopHeight, shadowRightHeight, shadowBottomHeight);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    private void initPaint() {
        shadowUpPaint = new Paint();
        shadowUpPaint.setColor(cardColor);
        shadowUpPaint.setStyle(Paint.Style.FILL);
        shadowUpPaint.setAntiAlias(true);



        shadowDownPaint = new Paint();
        shadowDownPaint.setColor(cardColor);
        shadowDownPaint.setStyle(Paint.Style.FILL);
        shadowDownPaint.setAntiAlias(true);

        if (isSetShadow){
            //偏离上半部分-1，达到ui效果
            shadowUpPaint.setShadowLayer(shadowRadius, 0, -1, shadowColor);
            shadowDownPaint.setShadowLayer(shadowRadius, shadowOffsetX, shadowOffsetY, shadowColor);
        }


        bitmapPaint = new Paint();
    }

    /**
     * dp->px
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        initPaint();

        float left = shadowLeftHeight;
        float top = shadowTopHeight;
        float right = getWidth() - shadowRightHeight;
        float bottom = getHeight() - shadowBottomHeight;

        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, cornersRadius, cornersRadius, shadowUpPaint);



        rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, cornersRadius, cornersRadius, shadowDownPaint);

        if (src!=null){
            Bitmap mBackGround = ((BitmapDrawable) src).getBitmap();
            mBackGround= resizeBitmap(mBackGround);
            canvas.drawBitmap(mBackGround, (float) (getWidth()-mBackGround.getWidth())/2, (float) (getHeight()-mBackGround.getHeight())/2, bitmapPaint);

        }


        canvas.save();
        super.dispatchDraw(canvas);
    }


    /**
     * 原始ui宽度，以此为参考
     */
    private final int originWidth = dp2px(getContext(), 100);
    /**
     * 原始ui高度，以此为参考
     */
    private final int originHeight = dp2px(getContext(), 100);

    public Bitmap resizeBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int newWidth = getWidth()-shadowLeftHeight-shadowRightHeight;
            int newHeight = getHeight()-shadowTopHeight-shadowBottomHeight;
            float scaleWight = ((float) newWidth) / originWidth;
            float scaleHeight = ((float) newHeight) / originHeight;
            Log.i(TAG, "resizeBitmap: newWidth:"+newWidth+" newHeight:"+newHeight);
            Log.i(TAG, "resizeBitmap: scaleWight:"+scaleWight+" scaleHeight:"+scaleHeight);
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWight, scaleHeight);
            Bitmap res = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return res;

        } else {
            return null;
        }


    }
}