package com.wdz.mychoosedialog.datepicker;

import android.os.Bundle;

import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;

import com.wdz.mychoosedialog.R;


public class MainActivity extends AppCompatActivity {
    private static final int MINUTE_SECOND = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*使用示例*/

        final TimerPicker mTimerPicker = new TimerPicker("M","S",this, new TimerPicker.TimeSelectCallback() {
            @Override
            public void onTimeSelected(long hour, long minute) {

            }

            @Override
            public void onCancel() {
            }
            @Override
            public void onTimeSelecting(long hour, long minute) {
            }
        });
        //设置弹窗显示位置
        mTimerPicker.setGravity(Gravity.BOTTOM);
        //设置选择器类型
        mTimerPicker.setType(MINUTE_SECOND);
        //设置每列显示范围
        mTimerPicker.setRange(0,59,0,59);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(false);



        final CalendarPicker mCalendarPicker = new CalendarPicker(this, new CalendarPicker.Callback() {
            @Override
            public void selectDate(Long str) {

            }

            @Override
            public void onCancel() {

            }
        });
        //设置显示范围
        mCalendarPicker.setTime(DateFormatUtils.str2Long("1999-05-03"),System.currentTimeMillis());
        //设置当前显示日期
        mCalendarPicker.showTime(DateFormatUtils.long2Str(System.currentTimeMillis()));
        //允许点击屏幕或物理返回键关闭
        mCalendarPicker.setCancelable(true);
        // 允许循环滚动
        mCalendarPicker.setScrollLoop(false);
        // 允许滚动动画
        mCalendarPicker.setCanShowAnim(true);




    }
}
