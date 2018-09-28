package com.example.dezhiwang.studycollection.MyDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by dezhi.wang on 2018/9/28.
 */

public class ImageWithIcon extends android.support.v7.widget.AppCompatImageView {

    private Bitmap bitmap;
    private int num;
    private Rect rect;
    private Paint paint;
    private Paint textPaint;
    private int width;
    private int height;

    public ImageWithIcon(Context context) {
        this(context,null);
    }

    public ImageWithIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageWithIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffff4444);
        paint.setStyle(Paint.Style.FILL);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(20);

    }
    public void setNum(int num){
        this.num=num;
        invalidate();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i("text","getwidth=="+getWidth());
//        Log.i("text","getmeasurewidth=="+getMeasuredWidth());
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (widthMode==MeasureSpec.EXACTLY){
//            width = widthSize;
//        }else{
//            //width=
//        }
//        if (heightMode==MeasureSpec.EXACTLY){
//            height = heightSize;
//        }else{
//            //height=
//        }
//        setMeasuredDimension(width,height);
//    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int radius = getWidth() / 6;
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
       // Log.i("text",paddingRight+"r");
        canvas.drawCircle(getWidth()-radius,0+radius,radius,paint);
        num=13;
        String text=Integer.toString(num);
        textPaint.setTextSize(radius);
        textPaint.getTextBounds(text,0,text.length(),rect);
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        canvas.drawText(text,num<10?getWidth()-radius-width:getWidth()-radius-width/2,radius+height/2,textPaint);

    }



}
