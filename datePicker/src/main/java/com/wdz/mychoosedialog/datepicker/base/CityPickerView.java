/*
 * Copyright {c} liuwan1992
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.wdz.mychoosedialog.datepicker.base;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.wdz.mychoosedialog.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author dezhi.wang
 */
public class CityPickerView extends View {

    private Context mContext;

    private Paint mPaint;
    private int mLightColor, mDarkColor;
    private float mHalfWidth, mHalfHeight;
    private float mMinTextSize;
    private float mTextSpacing, mHalfTextSpacing;

    private float mScrollDistance;
    private float mLastTouchY;
    private List<String> mDataList = new ArrayList<>();
    private int mSelectedIndex;
    private int maxIndex;
    private boolean mCanScroll = true;
    private boolean mCanScrollLoop = false;
    private OnSelectListener mOnSelectListener;
    private ObjectAnimator mScrollAnim;
    private boolean mCanShowAnim = false;

    private Timer mTimer = new Timer();
    private TimerTask mTimerTask;
    private Handler mHandler = new ScrollHandler(this);
    private static final String TAG = "PickerView";

    /**
     * 自动回滚到中间的速度
     */
    private static final float AUTO_SCROLL_SPEED = 50;

    private int height;
    private float maxTextSize;

    /**
     * 选择结果回调接口
     */
    public interface OnSelectListener {
        void onSelect(View view, String selected, int position);
    }

    private static class ScrollTimerTask extends TimerTask {
        private WeakReference<Handler> mWeakHandler;

        private ScrollTimerTask(Handler handler) {
            mWeakHandler = new WeakReference<>(handler);
        }

        @Override
        public void run() {
            Handler handler = mWeakHandler.get();
            if (handler == null){
                return;
            }
            handler.sendEmptyMessage(0);
        }
    }

    private static class ScrollHandler extends Handler {
        private WeakReference<CityPickerView> mWeakView;

        private ScrollHandler(CityPickerView view) {
            mWeakView = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            CityPickerView view = mWeakView.get();
            if (view == null){
                return;
            }
            view.keepScrolling();
        }
    }

    public CityPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }



    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Style.FILL);
        //居中显示
        mPaint.setTextAlign(Align.CENTER);
        mLightColor = ContextCompat.getColor(mContext, R.color.city_picker_text_light);
        mDarkColor = ContextCompat.getColor(mContext, R.color.city_picker_text_dark);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //文字起点，控件宽度一半
        mHalfWidth = getMeasuredWidth() / 2f;
        height = getMeasuredHeight();
        mHalfHeight = height / 2f;
        maxTextSize = height / 5f;
        mMinTextSize = maxTextSize / 2.2f;
        mTextSpacing = mMinTextSize * 2f;
        mHalfTextSpacing = mTextSpacing / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mSelectedIndex >= mDataList.size()){
            return;
        }
        drawLine(canvas);

        // 绘制选中的 text
        drawText(canvas, mDarkColor, mScrollDistance, mDataList.get(mSelectedIndex));
        // 绘制选中上方的 text
        for (int i = 1; i <= mSelectedIndex; i++) {
            drawText(canvas, mLightColor, mScrollDistance - i * mTextSpacing,
                    mDataList.get(mSelectedIndex - i));
        }

        // 绘制选中下方的 text
        int size = mDataList.size() - mSelectedIndex;
        for (int i = 1; i < size; i++) {
            drawText(canvas, mLightColor, mScrollDistance + i * mTextSpacing,
                    mDataList.get(mSelectedIndex + i));
        }


    }

    private void drawLine(Canvas canvas){
        Paint paint = new Paint();
        paint.setStrokeWidth(dip2px(1));
        paint.setColor( ContextCompat.getColor(mContext, R.color.city_picker_bg));


        Path path = new Path();
        path.moveTo(0,mHalfHeight-maxTextSize/2);
        path.lineTo(getMeasuredWidth(),mHalfHeight-maxTextSize/2);
        path.lineTo(getMeasuredWidth(),mHalfHeight+maxTextSize/2);
        path.lineTo(0,mHalfHeight+maxTextSize/2);
        path.close();
        canvas.drawPath(path,paint);


        paint.setColor( ContextCompat.getColor(mContext, R.color.city_picker_line ));
        canvas.drawLine(0,mHalfHeight-maxTextSize/2,getMeasuredWidth(),mHalfHeight-maxTextSize/2,paint);
        canvas.drawLine(0,mHalfHeight+maxTextSize/2,getMeasuredWidth(),mHalfHeight+maxTextSize/2,paint);
    }

    private void drawText(Canvas canvas, int textColor, float offsetY, String text) {
        if (TextUtils.isEmpty(text)){
            return;
        }
        if (textColor == mLightColor){
            mPaint.setTypeface(Typeface.DEFAULT);
        }
        else{
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        }

        mPaint.setTextSize(sp2px(15));
        mPaint.setColor(textColor);

        // text 居中绘制，mHalfHeight + offsetY 是 text 的中心坐标
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        //底线坐标
        float baseline = mHalfHeight + offsetY - (fm.ascent + fm.descent) / 2f;
        canvas.drawText(text, mHalfWidth, baseline, mPaint);

    }

    /**
     * dx->px
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mCanScroll && super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                cancelTimerTask();
                mLastTouchY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float offsetY = event.getY();
                mScrollDistance += offsetY - mLastTouchY;
                if (mScrollDistance > mHalfTextSpacing) {
                    if (!mCanScrollLoop) {
                        if (mSelectedIndex == 0) {
                            mLastTouchY = offsetY;
                            invalidate();
                            break;
                        } else {
                            mSelectedIndex-=Math.round(Math.abs(mScrollDistance)/mHalfTextSpacing);
                            if (mSelectedIndex <= 0){
                                mSelectedIndex = 0;
                            }
                        }
                    } else {
                        // 往下滑超过离开距离，将末尾元素移到首位
                        moveTailToHead();
                    }
                    mScrollDistance -= mTextSpacing;
                } else if (mScrollDistance < -mHalfTextSpacing) {
                    //滑动至末尾
                    if (!mCanScrollLoop) {
                        if (mSelectedIndex == mDataList.size() - 1) {
                            mLastTouchY = offsetY;
                            invalidate();
                            break;
                        } else {
                            mSelectedIndex+=Math.round(Math.abs(mScrollDistance)/mHalfTextSpacing);
                            if (mSelectedIndex >= (maxIndex-1)) {
                                mSelectedIndex = (maxIndex - 1);
                            }
                        }
                    } else {
                        // 往上滑超过离开距离，将首位元素移到末尾
                        moveHeadToTail();
                    }
                    mScrollDistance += mTextSpacing;
                }
                mLastTouchY = offsetY;
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起手后 mSelectedIndex 由当前位置滚动到中间选中位置
                if (Math.abs(mScrollDistance) < 0.01) {
                    mScrollDistance = 0;
                    break;
                }
                cancelTimerTask();
                mTimerTask = new ScrollTimerTask(mHandler);
                mTimer.schedule(mTimerTask, 0, 10);
                break;
            default:
                break;
        }
        return true;
    }

    private void cancelTimerTask() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.purge();
        }
    }

    private void moveTailToHead() {
        if (!mCanScrollLoop || mDataList.isEmpty()){
            return;
        }

        String tail = mDataList.get(mDataList.size() - 1);
        mDataList.remove(mDataList.size() - 1);
        mDataList.add(0, tail);
    }

    private void moveHeadToTail() {
        if (!mCanScrollLoop || mDataList.isEmpty()){
            return;
        }

        String head = mDataList.get(0);
        mDataList.remove(0);
        mDataList.add(head);
    }

    private void keepScrolling() {
        if (Math.abs(mScrollDistance) < AUTO_SCROLL_SPEED) {

            mScrollDistance = 0;
            if (mTimerTask != null) {
                cancelTimerTask();
                if (mOnSelectListener != null && mSelectedIndex < mDataList.size()) {
                    mOnSelectListener.onSelect(this, mDataList.get(mSelectedIndex),mSelectedIndex);
                }
            }
        } else if (mScrollDistance > 0) {
            //Log.i(TAG,"向下滚动");
            // 向下滚动
            mScrollDistance -= AUTO_SCROLL_SPEED;
        } else {
            //Log.i(TAG,"向上滚动");
            // 向上滚动
            mScrollDistance += AUTO_SCROLL_SPEED;
        }
        invalidate();
    }

    /**
     * 设置数据源
     */
    public void setDataList(List<String> list) {
        if (list == null || list.isEmpty()){
            return;
        }
        mDataList = list;
        // 重置 mSelectedIndex，防止溢出
        mSelectedIndex = 0;
        maxIndex = list.size();

        invalidate();
    }

    /**
     * 选择选中项
     */
    public void setSelected(int index) {
        if (index >= mDataList.size()){
            return;
        }

        mSelectedIndex = index;

        ifCanLoop();
    }

    public void ifCanLoop(){
        if (mCanScrollLoop) {

            // 可循环滚动时，mSelectedIndex 值固定为 mDataList / 2
            int position = mDataList.size() / 2 - mSelectedIndex;
            if (position < 0) {
                for (int i = 0; i < -position; i++) {
                    moveHeadToTail();
                    mSelectedIndex--;
                }
            } else if (position > 0) {
                for (int i = 0; i < position; i++) {
                    moveTailToHead();
                    mSelectedIndex++;
                }
            }
        }
        invalidate();
    }


    /**
     * 设置选择结果监听
     */
    public void setOnSelectListener(OnSelectListener listener) {
        mOnSelectListener = listener;
    }

    /**
     * 是否允许滚动
     */
    public void setCanScroll(boolean canScroll) {
        mCanScroll = canScroll;
    }

    /**
     * 是否允许循环滚动
     */
    public void setCanScrollLoop(boolean canLoop) {
        mCanScrollLoop = canLoop;
        ifCanLoop();
    }

    /**
     * 执行滚动动画
     */
    public void startAnim() {
        if (!mCanShowAnim){
            return;
        }

        if (mScrollAnim == null) {
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.3f, 1f);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.3f, 1f);
            mScrollAnim = ObjectAnimator.ofPropertyValuesHolder(this, alpha, scaleX, scaleY).setDuration(200);
        }

        if (!mScrollAnim.isRunning()) {
            mScrollAnim.start();
        }
    }

    /**
     * 是否允许滚动动画
     */
    public void setCanShowAnim(boolean canShowAnim) {
        mCanShowAnim = canShowAnim;
    }

    /**
     * 销毁资源
     */
    public void onDestroy() {
        mOnSelectListener = null;
        mHandler.removeCallbacksAndMessages(null);
        if (mScrollAnim != null && mScrollAnim.isRunning()) {
            mScrollAnim.cancel();
        }
        cancelTimerTask();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

}