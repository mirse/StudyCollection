package com.example.mychoosedialog.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.mychoosedialog.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class TimerPicker implements View.OnClickListener, PickerView.OnSelectListener {

    private Context mContext;
    private TimeSelectCallback mCallback;
    private Calendar  mSelectedTime;
    private HashMap<String,Integer> map;
    private boolean mCanDialogShow;
    private Dialog mPickerDialog;
    private PickerView mDpvHour, mDpvMinute;
    private List<String> mHourUnits = new ArrayList<>(), mMinuteUnits = new ArrayList<>();
    private DecimalFormat mDecimalFormat = new DecimalFormat("00");

    private int mScrollUnits = SCROLL_UNIT_HOUR + SCROLL_UNIT_MINUTE;
    private int TIME_MIN = 0;
    private int HOUR_MAX = 24;
    private int MINUTE_MAX = 59;
    /**
    *  是否刷新分钟栏
    */
    private boolean refreshMinute = false;

    /**
     * 选择器类型
     */
    public static final int HOUR_MINUTE = 1;
    public static final int MINUTE_SECOND = 2;
    /**
    * true---时钟逻辑 显示（23：59）/false---计时器逻辑 显示（24：00）
    */
    public  boolean isClock = true;
    /**
     * COLUMN1_TYPE-控件第一栏时间格式
     * COLUMN2_TYPE-第二栏时间格式
     * */
    private static int COLUMN1_TYPE = Calendar.HOUR_OF_DAY;
    private static  int COLUMN2_TYPE = Calendar.MINUTE;
    /**
     * 时间单位：时、分 二进制
     */
    private static final int SCROLL_UNIT_HOUR = 0b1;
    private static final int SCROLL_UNIT_MINUTE = 0b10;


    private static final String TAG = "TimerPicker";
    private int  minMinute, maxMinute;
    private int mMinute = 0,mHour = 0;

    public interface TimeSelectCallback {
        /**
         * 选择时间的接口回调
         * @param hour 小时
         * @param minute 分钟
         */
        void onTimeSelected(long hour, long minute);

        /**
         * 点击取消的接口回调
         */
        void onCancel();
    }

    /**
     * 初始化时间选择器
     * @param context        Activity Context
     * @param callback       选择结果回调
     */
    public TimerPicker(Context context, TimeSelectCallback callback) {
        if (context == null || callback == null ) {
            mCanDialogShow = false;
            return;
        }
        mContext = context;
        mCallback = callback;
        mSelectedTime = Calendar.getInstance();
        map = new HashMap<>();
        initView();
        mCanDialogShow = true;
    }

    private void initView() {
        mPickerDialog = new Dialog(mContext, R.style.date_picker_dialog);
        mPickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mPickerDialog.setContentView(R.layout.picker_timer);

        setGravity(Gravity.BOTTOM);

        mPickerDialog.findViewById(R.id.iv_cancel).setOnClickListener(this);
        mPickerDialog.findViewById(R.id.iv_confirm).setOnClickListener(this);

        mDpvHour = mPickerDialog.findViewById(R.id.dpv_hour);
        mDpvMinute = mPickerDialog.findViewById(R.id.dpv_minute);
        mDpvMinute.setOnSelectListener(this);
        mDpvHour.setOnSelectListener(this);
    }

    /**
     * 设置弹窗位置
     * @param gravity 位置属性 默认为Gravity.BOTTOM
     */
    public void setGravity(int gravity) {
        Window window = mPickerDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = gravity;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
    }

    /**
     * 设置显示时间格式
     * @param type  1、小时|分钟 2、分钟|秒
     */
    public void setType(int type){
        switch (type){
            case HOUR_MINUTE:
                COLUMN1_TYPE = Calendar.HOUR_OF_DAY;
                COLUMN2_TYPE = Calendar.MINUTE;
                break;
            case MINUTE_SECOND:
                COLUMN1_TYPE = Calendar.MINUTE;
                COLUMN2_TYPE = Calendar.SECOND;
                break;
            default:
                    break;
        }
    }

    /**
     * @param isClock 是否是时钟逻辑
     */
    public void isClock(boolean isClock){
        this.isClock = isClock;
    }


    /**
     * 设置范围值
     * @param minHour 第一列的最小值
     * @param maxHour 第一列的最大值
     * @param minMinute 第二列的最小值
     * @param maxMinute 第二列的最大值
     */
    public void setRange(int minHour,int maxHour,int minMinute,int maxMinute){
        this.minMinute = minMinute;
        this.maxMinute = maxMinute;
        for (int i = minHour; i <= maxHour; i++) {
            mHourUnits.add(String.valueOf(i));
        }
        for (int i = minMinute; i <= maxMinute; i++) {
            mMinuteUnits.add(mDecimalFormat.format(i));
        }
        mDpvHour.setDataList(mHourUnits);
        mDpvHour.setSelected(0);
        mDpvMinute.setDataList(mMinuteUnits);
        mDpvMinute.setSelected(0);
        mSelectedTime.set(COLUMN1_TYPE, Integer.parseInt(mHourUnits.get(0)));
        mSelectedTime.set(COLUMN2_TYPE, Integer.parseInt(mMinuteUnits.get(0)));
        setCanScroll();
    }

    /**
     * 设置当前的时间
     * @param hour 设置显示的小时数
     * @param minute 设置显示的分钟数
     */
    public void setTime(int hour,int minute){
        if (hour>HOUR_MAX){
            hour = HOUR_MAX;
        }
        else if (hour<TIME_MIN){
            hour = TIME_MIN;
        }
        else if (minute>MINUTE_MAX){
            minute = MINUTE_MAX;
        }
        else if (minute<TIME_MIN){
            minute = TIME_MIN;
        }
        mSelectedTime.set(COLUMN1_TYPE, Integer.parseInt(mHourUnits.get(hour)));
        mSelectedTime.set(COLUMN2_TYPE, Integer.parseInt(mMinuteUnits.get(minute)));
        mDpvHour.setSelected(hour);
        mDpvMinute.setSelected(minute);
        mHour = hour;
        mMinute = minute;
    }

    /**
     * 设置是否可以滚动
     */
    private void setCanScroll() {
        mDpvHour.setCanScroll(mHourUnits.size() > 1 && (mScrollUnits & SCROLL_UNIT_HOUR) == SCROLL_UNIT_HOUR);
        mDpvMinute.setCanScroll(mMinuteUnits.size() > 1 && (mScrollUnits & SCROLL_UNIT_MINUTE) == SCROLL_UNIT_MINUTE);
    }


    /**
     * @return 弹窗是否显示
     */
    private boolean canShow() {
        return mCanDialogShow && mPickerDialog != null;
    }


    /**
     * 展示时间选择器
     */
    public void show() {
        if (!canShow()) {
            return;
        }
            mPickerDialog.show();
    }


    /**
     * 设置是否允许点击屏幕或物理返回键关闭
     */
    public void setCancelable(boolean cancelable) {
        if (!canShow()) {
            return;
        }

        mPickerDialog.setCancelable(cancelable);
    }
    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setScrollLoop(boolean canLoop) {
        if (!canShow()) {
            return;
        }
        mDpvHour.setCanScrollLoop(canLoop);
        mDpvMinute.setCanScrollLoop(canLoop);
    }

    /**
     * 设置日期控件是否展示滚动动画
     */
//    public void setCanShowAnim(boolean canShowAnim) {
//        if (!canShow()) {
//            return;
//        }
//
//        mDpvHour.setCanShowAnim(canShowAnim);
//        mDpvMinute.setCanShowAnim(canShowAnim);
//    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_cancel) {
            if (mCallback != null) {
                mCallback.onCancel();
            }
        }
        else if (i == R.id.iv_confirm) {
            if (mCallback != null) {
                if (isClock){
                    mCallback.onTimeSelected(mSelectedTime.get(COLUMN1_TYPE), mSelectedTime.get(COLUMN2_TYPE));
                }
                else{
                    mCallback.onTimeSelected(mHour, mMinute);
                }

            }
        }

        if (mPickerDialog != null && mPickerDialog.isShowing()) {
            mPickerDialog.dismiss();
        }
    }

    @Override
    public void onSelect(View view, String selected) {
        if (view == null || TextUtils.isEmpty(selected)) {
            return;
        }
        int timeUnit;
        try {
            timeUnit = Integer.parseInt(selected);
        } catch (Throwable ignored) {
            return;
        }

        int i = view.getId();
        if (i == R.id.dpv_hour) {

            if (!isClock){
                linkageMinuteUnit(timeUnit);
            }
            mHour = timeUnit;

        } else if (i == R.id.dpv_minute) {
            mSelectedTime.set(COLUMN2_TYPE, timeUnit);
            mMinute = timeUnit;
        }
    }
    
    private void linkageMinuteUnit(int timeUnit){
        Log.i(TAG,mSelectedTime.get(Calendar.HOUR_OF_DAY)+"");
        if (timeUnit==HOUR_MAX){
            mMinuteUnits.clear();
            mMinuteUnits.add(mDecimalFormat.format(0));
            mDpvMinute.setDataList(mMinuteUnits);
            refreshMinute = true;
            mHour = HOUR_MAX;
            mMinute = 0;
        }
        else if (refreshMinute){
            mMinuteUnits.clear();
            for (int i = minMinute; i <= maxMinute; i++) {
                mMinuteUnits.add(mDecimalFormat.format(i));
            }
            mDpvMinute.setDataList(mMinuteUnits);
            refreshMinute = false;

            // 确保联动时不会溢出或改变关联选中值
//            int selectedMinute = getValueInRange(mSelectedTime.get(Calendar.SECOND) + 1, minMinute, maxMinute);
//            mSelectedTime.set(Calendar.MONTH, selectedMinute - 1);
//            mDpvMinute.setSelected(selectedMinute - minMinute);
        }

    }

    //阀值界限，防止溢出
    private int getValueInRange(int value, int minValue, int maxValue) {
        if (value < minValue) {
            return minValue;
        } else if (value > maxValue) {
            return maxValue;
        } else {
            return value;
        }
    }

}
