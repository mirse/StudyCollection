package com.example.dezhiwang.studycollection.mydrawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.dezhiwang.studycollection.R;

/**
 * Created by dezhi.wang on 2018/9/28.
 */

public class ViewWithIcon extends View {

    private Drawable viewImage;
    private int iconNum;
    private int height;
    private int width;
    private Bitmap bitmap;
    private int offset;
    private int radius;
    private Paint paint;
    private Paint textPaint;
    private Rect rect;

    public ViewWithIcon(Context context) {
        this(context,null);
    }

    public ViewWithIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewWithIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewWithIcon, defStyleAttr, 0);
        viewImage = typedArray.getDrawable(R.styleable.ViewWithIcon_view_image);
        iconNum = typedArray.getInteger(R.styleable.ViewWithIcon_icon_num, 1);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffff4444);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50f);
        rect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        BitmapDrawable bd= (BitmapDrawable) viewImage;
        bitmap = bd.getBitmap();
        radius = bitmap.getWidth()/6;
        int realWidthSize=bitmap.getWidth()+bitmap.getWidth()/3;
        int realHeightSize=bitmap.getHeight()+bitmap.getWidth()/3;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode==MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            width=realWidthSize;
        }
        if (heightMode==MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            height=realHeightSize;
        }
          //  offset = realSize / 2;
            setMeasuredDimension(width,height);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(radius,radius);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawCircle(bitmap.getWidth(),0,radius,paint);
        //iconNum=21;
        String text=Integer.toString(iconNum);
        textPaint.getTextBounds(text,0,text.length(),rect);
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        canvas.drawText(text,iconNum<10?bitmap.getWidth()-width:bitmap.getWidth()-width/2 ,height/2,textPaint);
    }
    public void setNum(int iconNum){
        this.iconNum=iconNum;
    }
}
