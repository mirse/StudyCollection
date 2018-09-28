package com.example.dezhiwang.studycollection.MyDrawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

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
    }
        @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            BitmapDrawable bd= (BitmapDrawable) viewImage;
            bitmap = bd.getBitmap();
        int realWidthSize=bitmap.getWidth()+bitmap.getWidth()/6;
        int realHeightSize=bitmap.getHeight()+bitmap.getWidth()/6;
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
        int radius=bitmap.getWidth()/6;
        canvas.translate(radius/2,radius/2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffff4444);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        Rect rect = new Rect();

        canvas.drawBitmap(bitmap,0,0,null);

        canvas.drawCircle(getWidth(),0,getWidth()/6,paint);
        int num=1;
        String text=Integer.toString(num);
        textPaint.getTextBounds(text,0,text.length(),rect);
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        canvas.drawText(text,num<10?getWidth()-radius-width:getWidth()-radius-width/2,radius+height/2,textPaint);
    }
}
