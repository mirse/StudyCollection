package com.wdz.studycollection.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by dezhi.wang on 2018/9/26.
 */

public class SurfaceViewDemo extends SurfaceView implements SurfaceHolder.Callback {

    private Paint mPaint;
    private SurfaceHolder holder;
    private MyThread mThread;
    private boolean isDraw;
    private Canvas canvas;
    private int cx;
    private int cy;

    public SurfaceViewDemo(Context context) {
        super(context);
        init(null,0);
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        holder = getHolder();
        holder.addCallback(this);
        mThread = new MyThread();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.start();
        isDraw=true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw=false;
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (isDraw){

               // DrawView();
                try {
                    DrawView();
                    sleep(16);//view刷新时间16ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void DrawView() {
        try {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawCircle(cx,cy,20,mPaint);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (canvas!=null){
                //判断画布是否为空，从而避免黑屏情况
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                cx = (int) event.getX();
                cy = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                cx = (int) event.getX();
                cy = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                cx = (int) event.getX();
                cy = (int) event.getY();
                break;
        }
        return true;
    }
}
