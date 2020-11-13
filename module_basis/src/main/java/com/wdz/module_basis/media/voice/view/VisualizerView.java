package com.wdz.module_basis.media.voice.view;


import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wdz.module_basis.R;

import java.util.Arrays;
import java.util.List;


/**
 * 自定义View——随音乐频谱跳动的线条
 */
public class VisualizerView extends View {
  private final String TAG = this.getClass().getSimpleName();
  private int mColor;// 主色调
  private int mLineWidth;// 频谱线条宽度
  private int mSpeceNum;// 空隙个数(不设置自己计算)
  private int mSpeceWidth;// 空隙宽度
  private int mBaseHeight;// 基础高度
  private boolean mLineIsSingleColor; // 线条只有一种颜色
  private int mFirstPartColor; // 频谱线条支持多种颜色
  private int mSecondPartColor;
  private int mThirdPartColor;
  private int mFourthPartColor;

  private Integer[] mBytes = new Integer[128];
  private Integer[] mLastBytes = new Integer[]{};
  private Integer[] mCurrentBytes = new Integer[]{};
  private float[] mPoints;
  private Rect mRect = new Rect();

  private Paint mPaint = new Paint();
  private int mMinPoint;
  private int mhalfPoint;

  public VisualizerView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    TypedArray t = context.obtainStyledAttributes(attributeSet, R.styleable.VisualizerView, 0, 0);
    mColor = t.getColor(R.styleable.VisualizerView_lineColor, Color.parseColor("#FFFFFF"));
    mSpeceNum = t.getInteger(R.styleable.VisualizerView_spaceNum, 0);
    mSpeceWidth = t.getDimensionPixelSize(R.styleable.VisualizerView_spaceWidth, 0);
    mLineWidth = t.getDimensionPixelSize(R.styleable.VisualizerView_lineWidth, 5);
    mBaseHeight = t.getDimensionPixelSize(R.styleable.VisualizerView_baseHeight, 1);
    mLineIsSingleColor = t.getBoolean(R.styleable.VisualizerView_lineIsSingleColor, true);
    mFirstPartColor = t.getColor(R.styleable.VisualizerView_firstPartColor, Color.parseColor("#FFFFFF"));
    mSecondPartColor = t.getColor(R.styleable.VisualizerView_secondPartColor, Color.parseColor("#FFFFFF"));
    mThirdPartColor = t.getColor(R.styleable.VisualizerView_thirdPartColor, Color.parseColor("#FFFFFF"));
    mFourthPartColor = t.getColor(R.styleable.VisualizerView_fourthPartColor, Color.parseColor("#FFFFFF"));
    t.recycle();
    init();
  }

  private void init() {
    mMinPoint = dip2px(getContext(), 2);
    mhalfPoint = dip2px(getContext(), 3);
    mBytes = null;
    mPaint.setStrokeWidth(mLineWidth);
    mPaint.setAntiAlias(true);
    mPaint.setStrokeCap(Paint.Cap.ROUND);
    if (mLineIsSingleColor) {
      mPaint.setColor(mColor);
    } else {
      mPaint.setColor(mColor);
    }
  }
  public void updateVisualizer(final List<Integer> list) {
    Integer[] model = new Integer[list.size()];
    if (mSpeceNum == 0) {
      int width = getWidth();
      mSpeceNum = (width + mLineWidth) / (mSpeceWidth + mLineWidth);
    }
    for (int i = 0; i<list.size(); i++ ) {
      model[i] = list.get(i);
    }
    mCurrentBytes = model;
    if (mLastBytes.length == 0){
      mLastBytes = mCurrentBytes;
    }


    ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<Integer[]>() {
      @Override
      public Integer[] evaluate(float fraction, Integer[] startValue, Integer[] endValue) {
        if (startValue.length<=0 || endValue.length<=0){

        }
        else{
          //根据插值刷新view
          mBytes = new Integer[list.size()];
          for (int i = 0; i < startValue.length; i++) {
            int db = endValue[i]-startValue[i];
            mBytes[i] = (int)(startValue[i] + db*fraction);
          }
          invalidate();
        }
        return mBytes;
      }
    },mLastBytes,mCurrentBytes);
    valueAnimator.setDuration(100);
    valueAnimator.start();

  }
//  public void updateVisualizer(byte[] fft) {
//    Log.i(TAG, "updateVisualizer: "+ Arrays.toString(fft)+" length:"+fft.length);
//    byte[] model = new byte[fft.length / 2 + 1];
//    model[0] = (byte) Math.abs(fft[0]);
//    if (mSpeceNum == 0) {
//      int width = getWidth();
//      mSpeceNum = (width + mLineWidth) / (mSpeceWidth + mLineWidth);
//    }
//    for (int i = 2, j = 1; j < mSpeceNum; ) {
//      model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
//      i += 2;
//      j++;
//    }
//    mBytes = model;
//    invalidate();
//  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);

    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    // 设置wrap_content的默认宽 / 高值
    int mWidth = dip2px(getContext(), 200);
    int mHeight = dip2px(getContext(), 100);

    if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(mWidth, mHeight);
    } else if (widthMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(mWidth, heightSize);
    } else if (heightMode == MeasureSpec.AT_MOST) {
      setMeasuredDimension(widthSize, mHeight);
    }
  }

  @SuppressLint("DrawAllocation")
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mBytes == null) {
      return;
    }

    mRect.set(0, 0, getWidth(), getHeight());
    //两个柱状图间距
    final int spacing = mRect.width() / mSpeceNum;
    final int rectHeight = mRect.height();

    for (int i = 0; i < mBytes.length; i++) {
      if (mBytes[i] < 0) {
        mBytes[i] = 0;
      }

      float offset = mBytes[i] * 10f + mBaseHeight;



      float x0 = 0;
      float x1 = 0;
      //最后一个柱状图不需要间距
      if (i != mSpeceNum - 1) {
        x0 = spacing * i + spacing;
        x1 = spacing * i + spacing;

      }
      else{
        x0 = spacing * i;
        x1 = spacing * i;
      }

      float y1 = rectHeight - mhalfPoint;
      float y0 = y1 - offset;



      if (!mLineIsSingleColor) {
        LinearGradient linearGradient = new LinearGradient(x1,y1,x0,y0,
                new int[]{getContext().getColor(R.color.color_13294B), getContext().getColor(R.color.color_6700FF),getContext().getColor(R.color.color_EF03F3)},
                null,Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawLine(x0,y0,x1,y1, mPaint);
      }
    }
    if (mLineIsSingleColor) {
      canvas.drawLines(mPoints, mPaint);
    }
    mLastBytes = mBytes;
  }


//  @SuppressLint("DrawAllocation")
//  @Override
//  protected void onDraw(Canvas canvas) {
//    super.onDraw(canvas);
//    if (mBytes == null) {
//      return;
//    }
//    if (mPoints == null || mPoints.length < mBytes.length * 4) {
//      mPoints = new float[mBytes.length * 4];
//    }
//    mRect.set(0, 0, getWidth(), getHeight());
//    final int spacing = mRect.width() / mSpeceNum;
//    final int rectHeight = mRect.height();
//
//    for (int i = 0; i < mBytes.length; i++) {
//      if (mBytes[i] < 0) {
//        mBytes[i] = 127;
//      }
//      final int xi = spacing * i + spacing;
//      final int xi2 = spacing * i;
//      if (i != mSpeceNum - 1) {
//        mPoints[i * 4] = xi;
//        mPoints[i * 4 + 2] = xi;
//      } else {
//        mPoints[i * 4] = xi2;
//        mPoints[i * 4 + 2] = xi2;
//      }
//      float offset = mBytes[i] * 3f + mBaseHeight;
//      if (offset <= mMinPoint) {
//        mPoints[i * 4 + 1] = rectHeight + mhalfPoint;
//        mPoints[i * 4 + 3] = rectHeight - mhalfPoint;
//      } else {
//        mPoints[i * 4 + 1] = rectHeight + offset;
//        mPoints[i * 4 + 3] = rectHeight - offset;
//      }
//      if (!mLineIsSingleColor) {
//        LinearGradient linearGradient = new LinearGradient(mPoints[i * 4], mPoints[i * 4 + 1],
//                mPoints[i * 4 + 2], mPoints[i * 4 + 3],
//                new int[]{getContext().getColor(R.color.color_13294B), getContext().getColor(R.color.color_6700FF),getContext().getColor(R.color.color_FC00FF)},
//                null,Shader.TileMode.CLAMP);
//        mPaint.setShader(linearGradient);
//        canvas.drawLine(mPoints[i * 4], mPoints[i * 4 + 1], mPoints[i * 4 + 2], mPoints[i * 4 + 3], mPaint);
//      }
//    }
//    if (mLineIsSingleColor) {
//      canvas.drawLines(mPoints, mPaint);
//    }
//  }


  /**
   * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
   */
  private int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }


}
