package com.example.dezhiwang.studycollection.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.R;
import com.example.mychoosedialog.datepicker.CalendarPicker;
import com.example.mychoosedialog.datepicker.DateFormatUtils;
import com.example.mychoosedialog.datepicker.TimerPicker;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatePickerActivity extends AppCompatActivity {
    @BindView(R.id.bt_show_timer) Button mBtnShowTimer;
    @BindView(R.id.bt_show_date) Button mBtnShowDate;

    private static final String TAG = "DatePickerActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_show_timer,R.id.bt_show_date})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.bt_show_timer:
                TimerPicker timerPicker = new TimerPicker(this, new TimerPicker.TimeSelectCallback() {
                    @Override
                    public void onTimeSelected(long hour, long minute) {
                        Log.d(TAG,"hour:"+hour+" minute:"+minute);
                    }
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onTimeSelecting(long hour, long minute) {
                        Log.d(TAG,"正在滑动hour:"+hour+" minute:"+minute);
                    }

                });
                timerPicker.setGravity(Gravity.BOTTOM);
                timerPicker.setType(TimerPicker.HOUR_MINUTE);
                //设置每列显示范围
                timerPicker.setRange(0,24,0,59);
                timerPicker.isClock(false);
                timerPicker.setTime(20,20);
                // 允许点击屏幕或物理返回键关闭
                timerPicker.setCancelable(true);
                // 允许循环滚动
                timerPicker.setScrollLoop(false);
                timerPicker.show();
                break;
            case R.id.bt_show_date:

                CalendarPicker calendarPicker = new CalendarPicker(this, new CalendarPicker.Callback() {
                    @Override
                    public void selectDate(Long date) {

                    }

                    @Override
                    public void onCancel() {

                    }
                });
                //设置显示范围
                calendarPicker.setTime(DateFormatUtils.str2Long("1999-05-03"),System.currentTimeMillis());
                //设置当前显示日期
                calendarPicker.showTime(DateFormatUtils.long2Str(System.currentTimeMillis()));
                //允许点击屏幕或物理返回键关闭
                calendarPicker.setCancelable(true);
                // 允许循环滚动
                calendarPicker.setScrollLoop(false);
                // 允许滚动动画
                calendarPicker.setCanShowAnim(true);
                calendarPicker.show();
                break;
            default:
                break;
        }

    }

}
