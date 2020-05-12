package com.wdz.studycollection.viewbase.scroller;

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

import com.wdz.studycollection.R;


public class ShadowCardView extends FrameLayout {
    private static final String TAG = "ShadowCardView";
    //    private static final int SHADOW_COLOR = R.color.shadow_color;
//    private static final int CARD_COLOR =R.color.card_color;
    private static final int CORNERS_RADIUS = 0;
    private static final int SHADOW_RADIUS = 20;
    private static final int SHADOW_TOP_HEIGHT = 10;
    private static final int SHADOW_LEFT_HEIGHT = 10;
    private static final int SHADOW_RIGHT_HEIGHT = 10;
    private static final int SHADOW_BOTTOM_HEIGHT = 10;
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

        typedArray.recycle();
        setPadding(shadowLeftHeight, shadowTopHeight, shadowRightHeight, shadowBottomHeight);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
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
        int i = dp2px(getContext(), 100);
        Log.i(TAG, "dispatchDraw: width:"+getWidth()+" height:"+getHeight()+" i:"+i);
        Paint paint = new Paint();
        paint.setColor(cardColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        float left = shadowLeftHeight;
        float top = shadowTopHeight;
        float right = getWidth() - shadowRightHeight;
        float bottom = getHeight() - shadowBottomHeight;
        //偏离上半部分-1，达到ui效果
        paint.setShadowLayer(shadowRadius, 0, -1, shadowColor);
        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, cornersRadius, cornersRadius, paint);

        Paint paint1 = new Paint();
        paint1.setColor(cardColor);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);


        paint1.setShadowLayer(shadowRadius, shadowOffsetX, shadowOffsetY, shadowColor);

        rectF = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rectF, cornersRadius, cornersRadius, paint1);

        Paint paint2 = new Paint();
        Bitmap mBackGround = ((BitmapDrawable) this.getResources().getDrawable(R.mipmap.img_avatar_adddevice_a60)).getBitmap(); //获取背景图片
        mBackGround= resizeBitmap(mBackGround);
        canvas.drawBitmap(mBackGround, shadowLeftHeight, shadowTopHeight, paint2);


        canvas.save();
        super.dispatchDraw(canvas);
    }


    public Bitmap resizeBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int newWidth = getWidth()-shadowLeftHeight-shadowRightHeight;
            int newHeight = getHeight()-shadowTopHeight-shadowBottomHeight;
            float scaleWight = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
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