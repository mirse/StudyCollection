package com.wdz.module_customview.myview.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;


import com.wdz.module_customview.R;

/**
 * 可选边框的圆/圆角矩形/矩形
 * 只能全圆角
 * @author wdz
 */
public class CornerView extends View {

    private static final String TAG = "RoundView";
    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private static final Xfermode sXfermode_dst_in = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private Paint mPaint;
    public static final int CIRCLE = 0;//圆形
    public static final int ROUNDRECT = 1;//圆角



    private int frameWidth;
    private int frameColor;
    private int type;
    private Paint framePaint;
    private Path rectPath;
    private Drawable src;

    private Bitmap bitmap;

    /**
     * 边框是否可见
     */
    private boolean frameVisible = false;
    private int cornerRadius;
    private final String BLACK = "000000";
    private final String FFBLACK = "ff000000";

    public CornerView(Context context) {
        super(context);
        init(context,null);
    }

    public CornerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CornerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CornerView);
        // 默认为Circle
        type = array.getInt(R.styleable.CornerView_type, 0);
        frameWidth = array.getDimensionPixelSize(R.styleable.CornerView_frameWidth, 0);
        cornerRadius = array.getDimensionPixelSize(R.styleable.CornerView_cornerRadius, 0);
        frameColor = array.getColor(R.styleable.CornerView_frameColor,0);
        frameVisible = array.getBoolean(R.styleable.CornerView_frameVisible,false);
        src = array.getDrawable(R.styleable.CornerView_viewSrc);

        array.recycle();
        initPaint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    /**
     * 设置背景色
     * @param viewSrc
     */
    public void setViewSrc(Drawable viewSrc){
        if (viewSrc instanceof ColorDrawable){
            //将黑色过滤成白色
            String hexColor = Integer.toHexString(((ColorDrawable)viewSrc).getColor());
            if (hexColor.equals(BLACK)||hexColor.equalsIgnoreCase(FFBLACK)){
                this.src = new ColorDrawable(hex2Int("FFFFFF"));
            }
            else{
                this.src = viewSrc;
            }

        }

        else{
            this.src = viewSrc;
        }

        invalidate();
    }

    /**
     * 设置边框颜色
     * @param frameColor
     */
    public void setFrameColor(int frameColor){
        this.frameColor = frameColor;

        if (frameWidth!=0){
            framePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            framePaint.setColor(frameColor);
            framePaint.setStyle(Paint.Style.STROKE);
            framePaint.setStrokeWidth(frameWidth);
        }

        invalidate();
    }

    /**
     * 设置边框是否可见
     * @param visible
     */
    public void setFrameVisible(boolean visible){
        this.frameVisible = visible;
        invalidate();
    }

    /**
     * 初始化paint
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);

        if (frameWidth!=0){
            framePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            framePaint.setColor(frameColor);
            framePaint.setStyle(Paint.Style.STROKE);
            framePaint.setStrokeWidth(frameWidth);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (src ==null){
            return;
        }
        //绘制图形以color为底色
        if (src instanceof ColorDrawable){
            drawViewByColor(canvas);
        }
        else{
            drawViewByBitmap(canvas);
        }

    }

    /**
     * src为以图片为底色
     * @param canvas
     */
    private void drawViewByBitmap(Canvas canvas) {
        bitmap = Bitmap.createBitmap(getWidth(),
                getHeight(), Bitmap.Config.ARGB_8888);
        Canvas bitmapCanvas = new Canvas(bitmap);
        src.setBounds(0, 0, getWidth(), getHeight());
        src.draw(bitmapCanvas);

        if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
            if (type == CIRCLE){
                mMaskBitmap = getBitmapCircle(getWidth(), getHeight());

            }
            else if (type ==ROUNDRECT){
                mMaskBitmap = getBitmapRoundRect(getWidth(), getHeight());

            }
        }

        mPaint.setXfermode(sXfermode_dst_in);

        bitmapCanvas.drawBitmap(mMaskBitmap, 0.0f, 0.0f, mPaint);
        //这里混合模式，上面设置完后，要再次设置为null
        mPaint.setXfermode(null);

        canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);


        //绘制外边框
        if (frameWidth != 0) {
            if (frameVisible){
                if (type == CIRCLE) {
                    float radius = Math.min((getWidth()-frameWidth) / 2, (getHeight()-frameWidth) / 2);
                    canvas.drawCircle((float) getWidth() / 2, (float) getHeight() / 2, radius, framePaint);
                }
                else if (type ==ROUNDRECT){
                    RectF rect = new RectF((float) frameWidth/2, (float) frameWidth/2, getWidth()-(float) frameWidth/2, getHeight()-(float) frameWidth/2);
                    canvas.drawRoundRect(rect,cornerRadius,cornerRadius,framePaint);
                }
            }

        }
    }

    /**
     * src为以颜色为底色
     * @param canvas
     */
    private void drawViewByColor(Canvas canvas) {
        ColorDrawable colorDrawable = (ColorDrawable) src;
        mPaint.setColor(colorDrawable.getColor());

        //颜色
        if (type == CIRCLE){

            int radius = Math.min((getWidth())/2,(getHeight())/2);

            canvas.drawCircle((float) getWidth()/2,(float) getHeight()/2,radius,mPaint);

            //绘制外边框
            if (frameWidth != 0) {
                if (frameVisible){
                    radius = radius-frameWidth/2;
                    canvas.drawCircle((float) getWidth()/2,(float) getHeight()/2,radius,framePaint);

                }
            }

        }
        else{

            RectF rect = new RectF((float) frameWidth/2, (float) frameWidth/2, getWidth()-(float) frameWidth/2, getHeight()-(float) frameWidth/2);
            canvas.drawRoundRect(rect,cornerRadius,cornerRadius,mPaint);
            //绘制外边框
            if (frameWidth != 0) {
                if (frameVisible){
                    //RectF connerRect = new RectF((float) frameWidth/2, (float) frameWidth/2, getWidth()-(float) frameWidth/2, getHeight()-(float) frameWidth/2);
                    canvas.drawRoundRect(rect,cornerRadius,cornerRadius,framePaint);
                }
            }

        }
    }

    /**
     * 圆角矩形
     * @param width
     * @param height
     * @return
     */
    public Bitmap getBitmapRoundRect(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);


        RectF rect = new RectF((float) frameWidth/2, (float) frameWidth/2, getWidth()-(float) frameWidth/2, getHeight()-(float) frameWidth/2);
        canvas.drawRoundRect(rect,cornerRadius,cornerRadius, paint);


        return bitmap;
    }




    //圆形
    private Bitmap getBitmapCircle(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        float radius = 0;

        radius = Math.min((width-frameWidth)/2,(height-frameWidth)/2);

        canvas.drawCircle((float) width / 2, (float) height / 2, radius, paint);


        return bitmap;
    }

    /**
     * dp->px
     */
    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**Color的16进制颜色值 转 Color的Int整型
     * colorHex - Color的16进制颜色值——#3FE2C5
     * return colorInt - -12590395
     * */
    public static int hex2Int(String colorHex){
        int colorInt = 0;
        if (colorHex != null){
            if (colorHex.startsWith("#")){
                colorInt = Color.parseColor(colorHex);
                return colorInt;
            }
            else{
                if (!"".equals(colorHex)){
                    colorInt = Color.parseColor("#"+colorHex);
                }
                return colorInt;
            }
        }
        return colorInt;


    }

}
