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

public class CalendarPicker implements View.OnClickListener, PickerView.OnSelectListener {

    private Context mContext;
    private Calendar mBeginTime, mEndTime, mSelectedTime;


    private PickerView mDpvYear, mDpvMonth, mDpvDay;

    private int mBeginYear, mBeginMonth, mBeginDay,
            mEndYear, mEndMonth, mEndDay;

    private DecimalFormat mDecimalFormat = new DecimalFormat("00");



    /**
     * 时间单位的最大显示值
     */
    private static final int MAX_MONTH_UNIT = 12;

    /**
     * 级联滚动延迟时间
     */
    private static final long LINKAGE_DELAY_DEFAULT = 100L;
    private long beginTimestamp;
    private long endTimestamp;
    private static final String TAG = "CustomDatePickerTest";
    private Dialog mCalendarPicker;
    private final boolean mCanDialogShow;
    private Callback mCallBack;
    private List<String> mYearUnits;
    private List<String> mMonthUnits;
    private List<String> mDayUnits;
    /**
     * 时间选择结果回调接口
     */
    public interface Callback {

        /**
         * 选择时间的接口回调
         * @param date 返回的日期
         */
        void selectDate(Long date);
        /**
         * 点击取消的接口回调
         */
        void onCancel();
    }



    public CalendarPicker(Context context,  Callback callback) {

        if (context == null || callback == null ) {
            mCanDialogShow = false;
            return;
        }
        mContext = context;
        mCallBack = callback;
        //起始时间
        mBeginTime = Calendar.getInstance();
        beginTimestamp = DateFormatUtils.str2Long("2000-01-01");
        mBeginTime.setTimeInMillis(beginTimestamp);
        //终止时间
        mEndTime = Calendar.getInstance();
        endTimestamp = System.currentTimeMillis();
        mEndTime.setTimeInMillis(endTimestamp);
        //选择时间
        mSelectedTime = Calendar.getInstance();
        //加载视图资源
        initView();
        //加载数据资源
        initData();

        mCanDialogShow = true;

    }
    public void setTime(long beginTime,long endTime){
        //起始时间
        mBeginTime = Calendar.getInstance();
        beginTimestamp =beginTime;
        mBeginTime.setTimeInMillis(beginTimestamp);
        //终止时间
        mEndTime = Calendar.getInstance();
        endTimestamp = endTime;
        mEndTime.setTimeInMillis(endTimestamp);
        //选择时间
        mSelectedTime = Calendar.getInstance();
        //加载数据资源
        initData();
    }



    private void initData() {

        mYearUnits = new ArrayList<>();
        mMonthUnits = new ArrayList<>();
        mDayUnits = new ArrayList<>();

        mSelectedTime.setTimeInMillis(mBeginTime.getTimeInMillis());

        mBeginYear = mBeginTime.get(Calendar.YEAR);
        // Calendar.MONTH 值为 0-11
        mBeginMonth = mBeginTime.get(Calendar.MONTH) + 1;
        mBeginDay = mBeginTime.get(Calendar.DAY_OF_MONTH);
        mEndYear = mEndTime.get(Calendar.YEAR);
        mEndMonth = mEndTime.get(Calendar.MONTH) + 1;
        mEndDay = mEndTime.get(Calendar.DAY_OF_MONTH);


        boolean canSpanYear = mBeginYear != mEndYear;
        boolean canSpanMon = !canSpanYear && mBeginMonth != mEndMonth;
        boolean canSpanDay = !canSpanMon && mBeginDay != mEndDay;
        if (canSpanYear) {
            initDateUnits(MAX_MONTH_UNIT, mBeginTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        else if (canSpanMon) {
            initDateUnits(mEndMonth, mBeginTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        else if (canSpanDay) {
            initDateUnits(mEndMonth, mEndDay);
        }

    }


    private void initView() {
        mCalendarPicker = new Dialog(mContext, R.style.date_picker_dialog);
        mCalendarPicker.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCalendarPicker.setContentView(R.layout.picker_calendar);
        setGravity(Gravity.BOTTOM);
        mCalendarPicker.findViewById(R.id.iv_cancel).setOnClickListener(this);
        mCalendarPicker.findViewById(R.id.iv_confirm).setOnClickListener(this);

        mDpvYear = mCalendarPicker.findViewById(R.id.dpv_year);
        mDpvMonth = mCalendarPicker.findViewById(R.id.dpv_month);
        mDpvDay = mCalendarPicker.findViewById(R.id.dpv_day);
        mDpvYear.setOnSelectListener(this);
        mDpvMonth.setOnSelectListener(this);
        mDpvDay.setOnSelectListener(this);


    }
    /**
     * 设置弹窗位置
     * @param gravity 位置属性 默认为Gravity.BOTTOM
     */
    private void setGravity(int gravity) {
        Window window = mCalendarPicker.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = gravity;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_cancel) {
            if (mCallBack != null){
                mCallBack.onCancel();
            }
        }
        else if (i == R.id.iv_confirm) {
            if (mCallBack != null) {
                mCallBack.selectDate(mSelectedTime.getTimeInMillis());
            }
        }
        if (mCalendarPicker != null && mCalendarPicker.isShowing()) {
            mCalendarPicker.dismiss();
        }

    }

    @Override
    public void onSelect(View view, String selected) {
        if (view == null || TextUtils.isEmpty(selected)) {
            return;
        }

        int timeUnit;
//        try {
//            timeUnit = Integer.parseInt(selected);
//        } catch (Throwable ignored) {
//            return;
//        }

        int i = view.getId();
        if (i == R.id.dpv_year) {
            timeUnit = Integer.parseInt(selected);
            mSelectedTime.set(Calendar.YEAR, timeUnit);
            linkageMonthUnit(true, LINKAGE_DELAY_DEFAULT);
        }
        else if (i == R.id.dpv_month) {
            timeUnit = month2Num(selected);
            // 防止类似 2018/12/31 滚动到11月时因溢出变成 2018/12/01
            int lastSelectedMonth = mSelectedTime.get(Calendar.MONTH) + 1;
            mSelectedTime.add(Calendar.MONTH, timeUnit - lastSelectedMonth);
            linkageDayUnit(true, LINKAGE_DELAY_DEFAULT);
        }
        else if (i == R.id.dpv_day) {
            timeUnit = Integer.parseInt(selected);
            mSelectedTime.set(Calendar.DAY_OF_MONTH, timeUnit);
        }
    }


    /**
     * 将数字月份转换成英文月份
     * @param month 月份int值
     * @return  转换成月份对应的英语
     */
    private String monthFormat(int month){
        switch (month){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";

        }
    }

    /**
     * 将英文月份转换成数字月份
     * @param month 英文月份
     * @return 数字月份
     */
    private int month2Num(String month){
        switch (month){
            case "January":
                return 1;
            case "February":
                return 2;
            case "March":
                return 3;
            case "April":
                return 4;
            case "May":
                return 5;
            case "June":
                return 6;
            case "July":
                return 7;
            case "August":
                return 8;
            case "September":
                return 9;
            case "October":
                return 10;
            case "November":
                return 11;
            case "December":
                return 12;
            default:
                return 0;

        }
    }



    private void initDateUnits(int endMonth, int endDay) {
        mYearUnits.clear();
        mMonthUnits.clear();
        mDayUnits.clear();
        for (int i = mBeginYear; i <= mEndYear; i++) {
            mYearUnits.add(String.valueOf(i));
        }
        for (int i = mBeginMonth; i <= endMonth; i++) {
            mMonthUnits.add(monthFormat(i));
        }

        for (int i = mBeginDay; i <= endDay; i++) {
            mDayUnits.add(mDecimalFormat.format(i));
        }
        mDpvYear.setDataList(mYearUnits);
        mDpvYear.setSelected(0);
        mDpvMonth.setDataList(mMonthUnits);
        mDpvMonth.setSelected(0);
        mDpvDay.setDataList(mDayUnits);
        mDpvDay.setSelected(0);
        setCanScroll();
    }

    private void setCanScroll() {
        mDpvYear.setCanScroll(mYearUnits.size() > 1);
        mDpvMonth.setCanScroll(mMonthUnits.size() > 1);
        mDpvDay.setCanScroll(mDayUnits.size() > 1);
    }

    /**
     * 联动“月”变化
     *
     * @param showAnim 是否展示滚动动画
     * @param delay    联动下一级延迟时间
     */
    private void linkageMonthUnit(final boolean showAnim, final long delay) {
        int minMonth;
        int maxMonth;
        int selectedYear = mSelectedTime.get(Calendar.YEAR);
        //起始年份==终止年份
        if (mBeginYear == mEndYear) {
            minMonth = mBeginMonth;
            maxMonth = mEndMonth;
        } else if (selectedYear == mBeginYear) {//当前选择年份为起始年份
            minMonth = mBeginMonth;
            maxMonth = MAX_MONTH_UNIT;
        } else if (selectedYear == mEndYear) {//当前选择年份为终止年份
            minMonth = 1;
            maxMonth = mEndMonth;
        } else {
            minMonth = 1;
            maxMonth = MAX_MONTH_UNIT;
        }

        // 重新初始化时间单元容器
        mMonthUnits.clear();
        for (int i = minMonth; i <= maxMonth; i++) {
            mMonthUnits.add(monthFormat(i));
        }
        mDpvMonth.setDataList(mMonthUnits);

        // 确保联动时不会溢出或改变关联选中值
        int selectedMonth = getValueInRange(mSelectedTime.get(Calendar.MONTH) + 1, minMonth, maxMonth);
        mSelectedTime.set(Calendar.MONTH, selectedMonth - 1);
        mDpvMonth.setSelected(selectedMonth - minMonth);
        if (showAnim) {
            mDpvMonth.startAnim();
        }

        // 联动“日”变化
        mDpvMonth.postDelayed(new Runnable() {
            @Override
            public void run() {
                linkageDayUnit(showAnim, delay);
            }
        }, delay);
    }

    /**
     * 联动“日”变化
     *
     * @param showAnim 是否展示滚动动画
     * @param delay    联动下一级延迟时间
     */
    private void linkageDayUnit(final boolean showAnim, final long delay) {
        int minDay;
        int maxDay;
        int selectedYear = mSelectedTime.get(Calendar.YEAR);
        int selectedMonth = mSelectedTime.get(Calendar.MONTH) + 1;
        //年份月份界面为同一时刻
        if (mBeginYear == mEndYear && mBeginMonth == mEndMonth) {
            minDay = mBeginDay;
            maxDay = mEndDay;
        } else if (selectedYear == mBeginYear && selectedMonth == mBeginMonth) {//年份月份为初始年月份
            minDay = mBeginDay;
            maxDay = mSelectedTime.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (selectedYear == mEndYear && selectedMonth == mEndMonth) {//年份月份为终止年月份
            minDay = 1;
            maxDay = mEndDay;
        } else {
            minDay = 1;
            maxDay = mSelectedTime.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        mDayUnits.clear();
        for (int i = minDay; i <= maxDay; i++) {
            mDayUnits.add(mDecimalFormat.format(i));
        }
        mDpvDay.setDataList(mDayUnits);

        int selectedDay = getValueInRange(mSelectedTime.get(Calendar.DAY_OF_MONTH), minDay, maxDay);
        mSelectedTime.set(Calendar.DAY_OF_MONTH, selectedDay);
        mDpvDay.setSelected(selectedDay - minDay);
        if (showAnim) {
            mDpvDay.startAnim();
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

    /**
     * 展示时间选择器
     *
     * @param dateStr 日期字符串，格式为 yyyy-MM-dd 或 yyyy-MM-dd HH:mm
     */
    public void showTime(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return;
        }
        setSelectedTime(dateStr, false);
    }


    /**
     * 展示时间选择器
     */
    public void show() {
        if (!canShow()) {
            return;
        }
        mCalendarPicker.show();
    }



    /**
     * 设置日期选择器的选中时间
     *
     * @param dateStr  日期字符串
     * @param showAnim 是否展示动画
     * @return 是否设置成功
     */
    private boolean setSelectedTime(String dateStr, boolean showAnim) {
        return  !TextUtils.isEmpty(dateStr)
                && setSelectedTime(DateFormatUtils.str2Long(dateStr), showAnim);
    }

    /**
     * 设置日期选择器的选中时间
     *
     * @param timestamp 毫秒级时间戳
     * @param showAnim  是否展示动画
     * @return 是否设置成功
     */
    public boolean setSelectedTime(long timestamp, boolean showAnim) {


        if (timestamp < mBeginTime.getTimeInMillis()) {
            timestamp = mBeginTime.getTimeInMillis();
        } else if (timestamp > mEndTime.getTimeInMillis()) {
            timestamp = mEndTime.getTimeInMillis();
        }
        mSelectedTime.setTimeInMillis(timestamp);

        mYearUnits.clear();
        for (int i = mBeginYear; i <= mEndYear; i++) {
            mYearUnits.add(String.valueOf(i));
        }
        mDpvYear.setDataList(mYearUnits);
        mDpvYear.setSelected(mSelectedTime.get(Calendar.YEAR) - mBeginYear);
        linkageMonthUnit(showAnim, showAnim ? LINKAGE_DELAY_DEFAULT : 0);
        return true;
    }


    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setScrollLoop(boolean canLoop) {
        if (!canShow()) {
            return;
        }


        mDpvYear.setCanScrollLoop(canLoop);
        mDpvMonth.setCanScrollLoop(canLoop);
        mDpvDay.setCanScrollLoop(canLoop);

    }

    private boolean canShow() {
        return mCanDialogShow && mCalendarPicker != null;
    }
    /**
     * 设置是否允许点击屏幕或物理返回键关闭
     */
    public void setCancelable(boolean cancelable) {
        if (!canShow()) {
            return;
        }

        mCalendarPicker.setCancelable(cancelable);
    }
    /**
     * 设置日期控件是否展示滚动动画
     */
    public void setCanShowAnim(boolean canShowAnim) {
        if (!canShow()) {
            return;
        }
        mDpvYear.setCanShowAnim(canShowAnim);
        mDpvMonth.setCanShowAnim(canShowAnim);
        mDpvDay.setCanShowAnim(canShowAnim);

    }


}
