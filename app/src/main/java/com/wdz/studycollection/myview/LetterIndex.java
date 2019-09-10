package com.wdz.studycollection.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by dezhi.wang on 2018/10/24.
 */

public class LetterIndex extends View {
    private String words[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};
    private int itemWidth;
    private int itemHeight;
    private int touchIndex=0;
    private Paint bgPaint;
    private Paint wordsPaint;

    public LetterIndex(Context context) {
        this(context,null);
    }

    public LetterIndex(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterIndex(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        bgPaint=new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.GRAY);
        wordsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wordsPaint.setTextSize(40);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        int height = getMeasuredHeight()-10;
        itemHeight = height/words.length;
        Log.i("text","itemwidth:"+itemWidth+" itemheight:"+itemHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<words.length;i++){
            if (touchIndex==i){
              //  canvas.drawCircle(itemWidth/2,itemHeight/2+itemHeight*i,23,bgPaint);
                wordsPaint.setColor(Color.WHITE);
            }
            else{
                wordsPaint.setColor(Color.BLACK);
            }
            Rect rect = new Rect();
            wordsPaint.getTextBounds(words[i],0,1,rect);
            int wordWidth = rect.width();
            int wordHeight = rect.height();
            int wordX = itemWidth / 2 -wordWidth/2;
            int wordY = itemHeight / 2 +wordHeight/2+ i * itemHeight;
            canvas.drawText(words[i],wordX,wordY,wordsPaint);
          //  canvas.drawLine(itemWidth/2,0,itemWidth/2,getHeight(),wordsPaint);
        }

    }
    public void setWords(String first){
        for (int i=0;i<words.length;i++){
            if (first.equals(words[i])) {
                touchIndex = i;
            }
        }
        invalidate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE|MotionEvent.ACTION_DOWN:
                float y = event.getY();
                touchIndex = (int) (y / itemHeight);
                if (touchIndex<0){
                    touchIndex=0;
                }
                else if(touchIndex>words.length-1){
                    touchIndex=words.length-1;
                }
                if (touchIndex>=0&&indexListener!=null){
                    indexListener.setIndexListener(words[touchIndex]);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
                default:
                    break;
        }
        return true;
    }
    private IndexListener indexListener;
    public interface IndexListener{
        void setIndexListener(String word);
    }
    public void setIndexListener(IndexListener indexListener){
        this.indexListener=indexListener;

    }
}
