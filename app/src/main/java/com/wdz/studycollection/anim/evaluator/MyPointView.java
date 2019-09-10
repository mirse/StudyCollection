package com.wdz.studycollection.anim.evaluator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyPointView extends View {

  private final Paint mPaint;
  private final Paint mTextPaint;
  public static final float RADIUS = 70f;// 圆的半径 = 70
  private Point currentPoint;

  public MyPointView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.BLUE);
    mTextPaint.setColor(Color.BLACK);
    mTextPaint.setTextSize(40);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (currentPoint == null) {
      currentPoint = new Point(RADIUS, RADIUS);
      float x = currentPoint.getX();
      float y = currentPoint.getY();
      //canvas.drawText("估值器",x,y,mTextPaint);
      canvas.drawCircle(x, y, RADIUS, mPaint);
      Point startPoint = new Point(RADIUS, RADIUS);
      Point endPoint = new Point(700, 1000);
      ValueAnimator animator = ValueAnimator.ofObject(new MyEvaluator(), startPoint, endPoint);
      animator.setDuration(5000);

      animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
          currentPoint = (Point) animation.getAnimatedValue();
          invalidate();
        }
      });
      animator.start();
    }
    else{
      float x = currentPoint.getX();
      float y = currentPoint.getY();
      canvas.drawCircle(x,y,RADIUS,mPaint);
      canvas.drawText("估值器",x,y,mTextPaint);
    }
  }
}