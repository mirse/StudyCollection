package com.wdz.module_customview.myview.datepick;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;
import com.wdz.mychoosedialog.datepicker.CalendarPicker;
import com.wdz.mychoosedialog.datepicker.CityPicker;
import com.wdz.mychoosedialog.datepicker.DateFormatUtils;
import com.wdz.mychoosedialog.datepicker.DatePicker;
import com.wdz.mychoosedialog.datepicker.RoomPicker;
import com.wdz.mychoosedialog.datepicker.TimerPicker;
import com.wdz.mychoosedialog.datepicker.bean.ChinaAddressInfo;
import com.wdz.mychoosedialog.datepicker.bean.City;
import com.wdz.mychoosedialog.datepicker.bean.District;
import com.wdz.mychoosedialog.datepicker.bean.Province;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_DATE_PICKER)
public class DatePickerActivity extends AppCompatActivity {
    @BindView(R2.id.bt_show_timer) Button mBtnShowTimer;
    @BindView(R2.id.bt_show_date) Button mBtnShowDate;

    private static final String TAG = "DatePickerActivity";
    private TimerPicker timerPicker;
    private Handler handler = new Handler();


    /**
     * 6天为一单位，long
     */
    private long DAY_6 = 6*24*60*60*1000;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_date_picker);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        mList.add("客厅");
        mList.add("卧室");
        mList.add("厨房");
        mList.add("浴室");
        mList.add("餐厅");

    }

    @OnClick({R2.id.bt_show_timer,R2.id.bt_show_date,R2.id.bt_show_room,R2.id.bt_show_city})
    public void onViewClicked(View view){
        int id = view.getId();
        if (id == R.id.bt_show_timer) {
            timerPicker = new TimerPicker("H", "M", this, new TimerPicker.TimeSelectCallback() {
                @Override
                public void onTimeSelected(long hour, long minute) {
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onTimeSelecting(long hour, long minute) {
                    Log.d(TAG, "onTimeSelecting: hour:" + hour + "    min:" + minute);
                    if (hour == 0 && minute < 1) {
                        timerPicker.setTime(0, 1);
                    }
                }
            });

            timerPicker.setType(TimerPicker.HOUR_MINUTE);
            timerPicker.setRange(0, 24, 0, 59);
            timerPicker.isClock(false);
            timerPicker.setTime(0, 01);
            timerPicker.show();
        } else if (id == R.id.bt_show_date) {
            Log.i(TAG, "System.currentTimeMillis():" + System.currentTimeMillis());
            Log.i(TAG, "6*DAY_6:" + 6 * DAY_6);
            Log.i(TAG, "start:" + (System.currentTimeMillis() - 6 * DAY_6));
            Log.i(TAG, "end:" + (System.currentTimeMillis() + DAY_6));
            new DatePicker(this)
                    .setTime((System.currentTimeMillis() - 60 * DAY_6), System.currentTimeMillis() + DAY_6)
                    .showTime(DateFormatUtils.long2Str(System.currentTimeMillis()))
                    .isCancelable(true)
                    .isScrollLoop(true)
                    .create()
                    .setOnSelectListener(new CalendarPicker.Callback() {
                        @Override
                        public void selectDate(Long date) {
                            Log.i(TAG, "data:" + System.currentTimeMillis());
                        }

                        @Override
                        public void onCancel() {

                        }
                    });

        } else if (id == R.id.bt_show_room) {
            RoomPicker roomPicker = new RoomPicker("取消", "保存", mList, DatePickerActivity.this, new RoomPicker.TimeSelectCallback() {
                @Override
                public void onRoomSelected(String roomName) {
                    Log.i(TAG, "onRoomSelected: " + roomName);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onRoomSelecting(String roomName) {
                    Log.i(TAG, "onRoomSelecting: " + roomName);
                }
            });
            roomPicker.show();




        }
        else if (id == R.id.bt_show_city){
            ChinaAddressInfo chinaAddressInfo = new ChinaAddressInfo();
            List<Province> provinceList = chinaAddressInfo.provinceList;
            for (int k=0;k<3;k++){
                Province province = new Province();
                province.provinceCode = "1";
                province.provinceName = "省"+k;
                List<City> cityList = province.cityList;
                for (int i=0;i<5;i++){
                    City city = new City();
                    city.cityName=k+"城市"+i;
                    city.cityCode = String.valueOf(i);
                    List<District> districtList = city.districtList;
                    for (int j=0;j<5;j++){
                        District district = new District();
                        district.districtName = k+"区"+i+j;
                        districtList.add(district);
                    }
                    cityList.add(city);

                }
                provinceList.add(province);
            }


            CityPicker cityPicker = new CityPicker(this, chinaAddressInfo, new CityPicker.Callback() {
                @Override
                public void selectCity(Province province, City city, District district) {
                    Log.i(TAG, "selectCity: province:"+province.provinceName+" city:"+city.cityName+" district:"+district.districtName);
                }

                @Override
                public void onCancel() {

                }
            });
            cityPicker.setCity("省1","1城市2","1区22");

            cityPicker.show();
        }

    }

}
