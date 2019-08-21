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
import java.util.List;


public class TimerPicker implements View.OnClickListener, PickerView.OnSelectListener {

    private Context mContext;
    private TimeSelectCallback mCallback;
    private Calendar  mSelectedTime;
    private boolean mCanDialogShow;
    private Dialog mPickerDialog;
    private PickerView mDpvHour, mDpvMinute;
    private List<String> mHourUnits = new ArrayList<>(), mMinuteUnits = new ArrayList<>();
    private DecimalFormat mDecimalFormat = new DecimalFormat("00");

    private int mScrollUnits = SCROLL_UNIT_HOUR + SCROLL_UNIT_MINUTE;


    /**
     * 选择器类型
     */
    public static final int HOUR_MINUTE = 1;
    public static final int MINUTE_SECOND = 2;
    //是否是时钟逻辑 显示（23：59）/计时器逻辑 显示（24：00）
    public  boolean isClock = true;

    private static int COLUMN1_TYPE = Calendar.HOUR_OF_DAY;
    private static  int COLUMN2_TYPE = Calendar.MINUTE;
    /**
     * 时间单位：时、分 二进制
     */
    private static final int SCROLL_UNIT_HOUR = 0b1;
    private static final int SCROLL_UNIT_MINUTE = 0b10;


    private static final String TAG = "TimerPicker";
    private int  minColumn2, maxColumn2;

    public interface TimeSelectCallback {
        /**
         * 选择时间的接口回调
         * @param hour 小时
         * @param minute 分钟
         */
        void onTimeSelected(long hour,long minute);

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
     * @param minColumn1 第一列的最小值
     * @param maxColumn1 第一列的最大值
     * @param minColumn2 第二列的最小值
     * @param maxColumn2 第二列的最大值
     */
    public void setRange(int minColumn1,int maxColumn1,int minColumn2,int maxColumn2){
        this.minColumn2 = minColumn2;
        this.maxColumn2 = maxColumn2;
        for (int i = minColumn1; i <= maxColumn1; i++) {
            mHourUnits.add(String.valueOf(i));
        }
        for (int i = minColumn2; i <= maxColumn2; i++) {
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
                mCallback.onTimeSelected(mSelectedTime.get(COLUMN1_TYPE), mSelectedTime.get(COLUMN2_TYPE));
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
            mSelectedTime.set(COLUMN1_TYPE, timeUnit);
            if (!isClock){
                linkageMinuteUnit(timeUnit);
            }

        } else if (i == R.id.dpv_minute) {
            mSelectedTime.set(COLUMN2_TYPE, timeUnit);
        }
    }

    private void linkageMinuteUnit(int timeUnit){
        Log.i(TAG,mSelectedTime.get(Calendar.HOUR_OF_DAY)+"");
        if (timeUnit==24){
            mMinuteUnits.clear();
            mMinuteUnits.add(mDecimalFormat.format(0));
            mDpvMinute.setDataList(mMinuteUnits);
        }
        else {
            mMinuteUnits.clear();
            for (int i = minColumn2; i <= maxColumn2; i++) {
                mMinuteUnits.add(mDecimalFormat.format(i));
            }
            mDpvMinute.setDataList(mMinuteUnits);
        }

    }

}
