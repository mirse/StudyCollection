package com.wdz.mychoosedialog.datepicker;


import android.app.Dialog;
import android.content.Context;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.wdz.mychoosedialog.R;

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


    //属性
    public boolean isCancelable;
    public boolean isScrollLoop;
    public String showTime;


    /**
     * 时间单位的最大显示值
     */
    private static final int MAX_MONTH_UNIT = 12;
    private static final int MIN_MONTH_UNIT = 1;
    private static final int MIN_DAY_UNIT = 1;

    /**
     * 级联滚动延迟时间
     */
    private static final long LINKAGE_DELAY_DEFAULT = 100L;
    private long beginTimestamp;
    private long endTimestamp;
    private static final String TAG = "CalendarPicker";
    private Dialog mCalendarPicker;
    private final boolean mCanDialogShow;
    public Callback mCallBack;
    private List<String> mYearUnits;
    private List<String> mMonthUnits;
    private List<String> mDayUnits;
    private String[] monthArray;



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


    /**
     * @param context
     * 默认初始日期2000.01.01
     *     终止日期当前日期
     */
    public CalendarPicker(Context context) {
        if (context == null) {
            mCanDialogShow = false;
            return;
        }
        mContext = context;
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

    public void setTime(long beginTime,long endTime){
        //起始时间
        mBeginTime = Calendar.getInstance();
        beginTimestamp =beginTime;
        mBeginTime.setTimeInMillis(beginTimestamp);
        //终止时间
        mEndTime = Calendar.getInstance();
        endTimestamp = endTime;
        mEndTime.setTimeInMillis(endTimestamp);
        //选择器选择时间
        mSelectedTime = Calendar.getInstance();
        //加载数据资源
        initData();
    }



    private void initData() {
        monthArray = mContext.getResources().getStringArray(R.array.month);
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
        Log.i(TAG,"canSpanYear:"+canSpanYear+" mBeginYear:"+mBeginYear+" mEndYear:"+mEndYear);
        Log.i(TAG,"canSpanMon:"+canSpanMon+" mBeginMonth:"+mBeginMonth+" mEndMonth:"+mEndMonth);
        Log.i(TAG,"canSpanDay:"+canSpanDay+" mBeginDay:"+mBeginDay+" mEndDay:"+mEndDay);
        if (canSpanYear) {
            initDateUnits(mEndMonth, mBeginTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        else if (canSpanMon) {
            initDateUnits(mEndMonth, mBeginTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        else if (canSpanDay) {
            initDateUnits(mEndMonth, mEndDay);
        }

    }

    /**
     * @param endMonth 当前设置的最后月份
     * @param endDay  指定日期的当月总天数
     */
    private void initDateUnits(int endMonth, int endDay) {
        mYearUnits.clear();
        mMonthUnits.clear();
        mDayUnits.clear();
        for (int i = mBeginYear; i <= mEndYear; i++) {
            mYearUnits.add(String.valueOf(i));
        }
        Log.i(TAG,"mBeginMonth:"+mBeginMonth+" endMonth:"+endMonth);
        Log.i(TAG,"mBeginDay:"+mBeginDay+" endDay:"+endDay);
        // TODO: 2020/1/2 当前是否可以滑动
        if (mBeginMonth<endMonth){
            for (int i = mBeginMonth; i <= endMonth; i++) {
                mMonthUnits.add(monthArray[i-1]);
            }
        }
        if (mBeginDay<endDay){
            for (int i = mBeginDay; i <= endDay; i++) {
                mDayUnits.add(mDecimalFormat.format(i));
            }
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
        Log.i(TAG,"canSpanYear:"+(mYearUnits.size())+" canSpanMon:"+ (mMonthUnits.size())+" canSpanDay----------"+(mDayUnits.size()));
        mDpvYear.setCanScroll(mYearUnits.size() > 1);
        mDpvMonth.setCanScroll(mMonthUnits.size() > 1);
        mDpvDay.setCanScroll(mDayUnits.size() > 1);
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

        int i = view.getId();
        if (i == R.id.dpv_year) {
            timeUnit = Integer.parseInt(selected);
            mSelectedTime.set(Calendar.YEAR, timeUnit);
            linkageMonthUnit(true, LINKAGE_DELAY_DEFAULT);
        }
        else if (i == R.id.dpv_month) {
            timeUnit = month2Num(selected)+1;
            // TODO: 2020/1/2 修改选择时间位置
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
        for (int i=0;i<monthArray.length;i++){
            if (month.equals(monthArray[i])){
                return i;
            }
        }
        return 0;
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
            mMonthUnits.add(monthArray[i-1]);
        }
        mDpvMonth.setDataList(mMonthUnits);

        // 确保联动时不会溢出或改变关联选中值
        int selectedMonth = getValueInRange(mSelectedTime.get(Calendar.MONTH) + 1, minMonth, maxMonth);
        mSelectedTime.set(Calendar.MONTH, selectedMonth - 1);
        mDpvMonth.setSelected(selectedMonth - minMonth);
        // TODO: 2020/1/2 添加滑动判断
        setCanScroll();
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
        // TODO: 2020/1/2 添加滑动判断
        setCanScroll();
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
