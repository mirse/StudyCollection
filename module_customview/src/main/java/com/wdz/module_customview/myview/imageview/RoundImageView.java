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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import com.wdz.module_customview.R;

/**
 * 圆角ImageView
 * @author wdz
 */
public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final String TAG = "RoundImageView";
    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private static final Xfermode sXfermode_dst_in = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private Paint mPaint;
    public static final int CIRCLE = 0;//圆形
    public static final int ROUNDRECT = 1;//圆角
    /**
     * 默认圆角为0dp,即直角
     */
    private int topLeftRadius;
    private int topRightRadius;
    private int bottomLeftRadius;
    private int bottomRightRadius;

    private int frameWidth;
    private int frameColor;
    private int type;
    private Paint framePaint;
    private Path rectPath;


    public RoundImageView(Context context) {
        super(context);
        init(context,null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);
        // 默认为Circle
        type = array.getInt(R.styleable.RoundImageView_type, 0);
        frameWidth = array.getDimensionPixelSize(R.styleable.RoundImageView_frameWidth, 0);
        frameColor = array.getColor(R.styleable.RoundImageView_frameColor,0);
        topLeftRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_topLeftRadius, 0);
        topRightRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_topRightRadius, 0);
        bottomLeftRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_bottomLeftRadius, 0);
        bottomRightRadius = array.getDimensionPixelSize(R.styleable.RoundImageView_bottomRightRadius, 0);
        array.recycle();


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);

        if (frameWidth!=0){
            framePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            framePaint.setColor(frameColor);
            framePaint.setStyle(Paint.Style.STROKE);
            framePaint.setStrokeWidth(frameWidth);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = null;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            bitmap = Bitmap.createBitmap(getWidth(),
                    getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, getWidth(), getHeight());
            drawable.draw(bitmapCanvas);

            if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                if (type == CIRCLE){
                    mMaskBitmap = getBitmapCircle(getWidth(), getHeight());

                }
                else if (type ==ROUNDRECT){
                    mMaskBitmap = getBitmapRoundRect(getWidth(), getHeight());

                }
            }

            if (type == CIRCLE){
                mPaint.setXfermode(sXfermode_dst_in);
            }
            else if (type ==ROUNDRECT){
                mPaint.setXfermode(sXfermode);
            }

            bitmapCanvas.drawBitmap(mMaskBitmap, 0.0f, 0.0f, mPaint);
            //这里混合模式，上面设置完后，要再次设置为null
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);

            if (frameWidth != 0) {
                if (type == CIRCLE) {
                    canvas.drawCircle((float) getWidth() / 2, (float) getHeight() / 2, Math.min(getWidth() / 2, getHeight() / 2), framePaint);
                }
                else if (type ==ROUNDRECT){
                    canvas.drawPath(rectPath,framePaint);
                }
            }

        }

    }

    //圆角矩形
    public Bitmap getBitmapRoundRect(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        rectPath = new Path();
        drawLeftUp(canvas,paint);
        drawRightUp(canvas,paint);
        drawRightDown(canvas,paint);
        drawLeftDown(canvas,paint);


        rectPath.close();

        return bitmap;
    }

    private void drawLeftUp(Canvas canvas,Paint paint) {
        if (topLeftRadius!=0){
            Path path = new Path();
            //从（0，roundHeight）开始绘制
            path.moveTo(0, topLeftRadius);
            path.lineTo(0, 0);
            path.lineTo(topLeftRadius, 0);
            //画出一个直角
            path.arcTo(new RectF(0, 0, topLeftRadius * 2, topLeftRadius * 2), 180, 90);

            rectPath.moveTo(0, topLeftRadius);
            rectPath.lineTo(0, 0);
            rectPath.lineTo(topLeftRadius, 0);
            //画出一个直角
            rectPath.arcTo(new RectF(0, 0, topLeftRadius * 2, topLeftRadius * 2), 180, 90);

            path.close();
            canvas.drawPath(path, paint);
        }
        else{
            rectPath.moveTo(0, 0);

        }

    }

    private void drawRightUp(Canvas canvas,Paint paint) {
        if (topRightRadius!=0) {
            Path path = new Path();
            path.moveTo(getWidth(), topRightRadius);
            path.lineTo(getWidth(), 0);
            path.lineTo(getWidth() - topRightRadius, 0);
            path.arcTo(new RectF(getWidth() - topRightRadius * 2, 0, getWidth(), 0 + topRightRadius * 2), 270, 90);

            rectPath.lineTo(getWidth() - topRightRadius, 0);
            rectPath.lineTo(getWidth(), 0);
            rectPath.lineTo(getWidth(), topRightRadius);


            rectPath.arcTo(new RectF(getWidth() - topRightRadius * 2, 0, getWidth(), 0 + topRightRadius * 2), 270, 90);

            path.close();
            canvas.drawPath(path, paint);
        }
        else{
            rectPath.lineTo(getWidth(), 0);
        }
    }


    private void drawRightDown(Canvas canvas,Paint paint) {
        if (bottomRightRadius!=0) {
            Path path = new Path();
            path.moveTo(getWidth() - bottomRightRadius, getHeight());
            path.lineTo(getWidth(), getHeight());
            path.lineTo(getWidth(), getHeight() - bottomRightRadius);
            path.arcTo(new RectF(getWidth() - bottomRightRadius * 2, getHeight() - bottomRightRadius * 2, getWidth(), getHeight()), 0, 90);


            rectPath.lineTo(getWidth(), getHeight() - bottomRightRadius);
            rectPath.lineTo(getWidth(), getHeight());
            rectPath.lineTo(getWidth() - bottomRightRadius, getHeight());
            rectPath.arcTo(new RectF(getWidth() - bottomRightRadius * 2, getHeight() - bottomRightRadius * 2, getWidth(), getHeight()), 0, 90);


            path.close();
            canvas.drawPath(path, paint);
        }
        else{
            rectPath.lineTo(getWidth(), getHeight());
        }
    }

    private void drawLeftDown(Canvas canvas,Paint paint) {
        if (bottomLeftRadius!=0) {
            Path path = new Path();
            path.moveTo(0, getHeight() - bottomLeftRadius);
            path.lineTo(0, getHeight());
            path.lineTo(bottomLeftRadius, getHeight());
            path.arcTo(new RectF(0, getHeight() - bottomLeftRadius * 2, bottomLeftRadius * 2, getHeight()), 90, 90);


            rectPath.lineTo(bottomLeftRadius, getHeight());
            rectPath.lineTo(0, getHeight());
            rectPath.lineTo(0, getHeight() - bottomLeftRadius);
            rectPath.arcTo(new RectF(0, getHeight() - bottomLeftRadius * 2, bottomLeftRadius * 2, getHeight()), 90, 90);



            path.close();
            canvas.drawPath(path, paint);
        }
        else {
            rectPath.lineTo(0, getHeight());
        }
    }




    //圆形
    private Bitmap getBitmapCircle(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle((float) width / 2, (float) height / 2, Math.min(width / 2, height / 2), paint);


        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (frameWidth!=0){
            //控件宽度
            width = measureWidth+2*frameWidth;
            //控件高度
            height = measureHeight+2*frameWidth;
        }
        Log.i(TAG, "onMeasure: "+dp2px(getContext(),300));
        Log.i(TAG, "onMeasure: measureHeight:"+measureHeight);
        Log.i(TAG, "onMeasure: "+(widthMode == MeasureSpec.EXACTLY));

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY?960:960,
                heightMode == MeasureSpec.EXACTLY?960:960);
    }

    /**
     * dp->px
     */
    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
