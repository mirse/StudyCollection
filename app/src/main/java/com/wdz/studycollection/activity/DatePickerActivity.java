package com.wdz.studycollection.activity;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.wdz.mychoosedialog.datepicker.DatePicker;
import com.wdz.studycollection.R;
import com.wdz.mychoosedialog.datepicker.CalendarPicker;
import com.wdz.mychoosedialog.datepicker.DateFormatUtils;
import com.wdz.mychoosedialog.datepicker.TimerPicker;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatePickerActivity extends AppCompatActivity {
    @BindView(R.id.bt_show_timer) Button mBtnShowTimer;
    @BindView(R.id.bt_show_date) Button mBtnShowDate;

    private static final String TAG = "DatePickerActivity";
    private TimerPicker timerPicker;
    private Handler handler = new Handler();
    private int DAY_6 = 6*24*60*60*1000;

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
//                timerPicker = new TimerPicker("H","M",this, new TimerPicker.TimeSelectCallback() {
//                    @Override
//                    public void onTimeSelected(long hour, long minute) {
//                        Log.d(TAG,"hour:"+hour+" minute:"+minute);
//
//                    }
//                    @Override
//                    public void onCancel() {
//                    }
//
//                    @Override
//                    public void onTimeSelecting(long hour, long minute) {
//                        Log.d(TAG,"正在滑动hour:"+hour+" minute:"+minute);
//                        if (hour<3){
//                            //优化，滑动时强制转化的动画流畅性
//                            timerPicker.setTime(3,(int) minute);
//                        }
//                    }
//
//                });
//                timerPicker.setGravity(Gravity.BOTTOM);
//                timerPicker.setType(TimerPicker.HOUR_MINUTE);
//
//                //设置每列显示范围
//                timerPicker.setRange(0,24,0,59);
//                timerPicker.isClock(true);
//                timerPicker.setTime(2,10);
//                // 允许点击屏幕或物理返回键关闭
//                timerPicker.setCancelable(true);
//                // 允许循环滚动
//                timerPicker.setScrollLoop(false);
//                timerPicker.show();


                timerPicker = new TimerPicker("H","M",this, new TimerPicker.TimeSelectCallback() {
                    @Override
                    public void onTimeSelected(long hour, long minute) {
//                        setHour = (int) hour;
//                        setMin = (int) minute;
//                        String hourStr = "";
//                        String minutStr = "";
//                        if (setHour < 10)
//                        {
//                            hourStr = "0"+setHour;
//                        }else
//                        {
//                            hourStr =  setHour+"";
//                        }
//
//                        if (setMin < 10)
//                        {
//                            minutStr = "0"+setMin;
//                        }else
//                        {
//                            minutStr =  setMin+"";
//                        }
//                        pickTime.setText(hourStr+":"+minutStr);
//                        currentTime = System.currentTimeMillis();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onTimeSelecting(long hour, long minute) {
                        Log.d(TAG, "onTimeSelecting: hour:"+hour+"    min:"+minute);
                        if (hour==0&&minute<1) {
                            timerPicker.setTime(0, 1);
                        }
                    }
                });

                timerPicker.setType(TimerPicker.HOUR_MINUTE);
                timerPicker.setRange(0,24,0,59);
                timerPicker.isClock(false);
//                String[] split1 = pickTime.getText().toString().split(":");
//                if (split1.length == 2)
//                {
//                    int h = Integer.parseInt(split1[0]);
//                    int m = Integer.parseInt(split1[1]);
//
//                    timerPicker.setTime(h,m);
//                }
                timerPicker.setTime(0,01);
                timerPicker.show();


                break;
            case R.id.bt_show_date:

                new DatePicker(this, new CalendarPicker.Callback() {
                    @Override
                    public void selectDate(Long date) {
                        Log.i(TAG,"data:"+System.currentTimeMillis());
                    }

                    @Override
                    public void onCancel() {

                    }
                })
                        .setTime(System.currentTimeMillis(),System.currentTimeMillis()+DAY_6)
                        .showTime(DateFormatUtils.long2Str(System.currentTimeMillis()))
                        .isCancelable(true)
                        .isScrollLoop(true)
                        .create();

//                CalendarPicker calendarPicker = new CalendarPicker(this, new CalendarPicker.Callback() {
//                    @Override
//                    public void selectDate(Long date) {
//                        Log.i(TAG,"data:"+System.currentTimeMillis());
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//                //设置显示范围
////                calendarPicker.setTime(DateFormatUtils.str2Long("1999-05-03"),System.currentTimeMillis());
//                calendarPicker.setTime(System.currentTimeMillis(),System.currentTimeMillis()+DAY_6);
//                //设置当前显示日期
//                calendarPicker.showTime(DateFormatUtils.long2Str(System.currentTimeMillis()));
//                //允许点击屏幕或物理返回键关闭
//                calendarPicker.setCancelable(true);
//                // 允许循环滚动
//                calendarPicker.setScrollLoop(false);
//                // 允许滚动动画
//                calendarPicker.setCanShowAnim(true);
//                calendarPicker.show();
                break;
            default:
                break;
        }

    }

}
