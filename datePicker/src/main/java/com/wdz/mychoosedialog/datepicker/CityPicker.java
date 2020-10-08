package com.wdz.mychoosedialog.datepicker;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wdz.mychoosedialog.R;
import com.wdz.mychoosedialog.datepicker.base.CityPickerView;
import com.wdz.mychoosedialog.datepicker.bean.ChinaAddressInfo;
import com.wdz.mychoosedialog.datepicker.bean.City;
import com.wdz.mychoosedialog.datepicker.bean.District;
import com.wdz.mychoosedialog.datepicker.bean.Province;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CityPicker implements View.OnClickListener, CityPickerView.OnSelectListener {

    private Context mContext;
    private CityPickerView mDpvProvince, mDpvCity, mDpvDistrict;

    //属性
    public boolean isCancelable;
    public boolean isScrollLoop;
    public String showTime;



    /**
     * 级联滚动延迟时间
     */
    private static final long LINKAGE_DELAY_DEFAULT = 100L;

    private static final String TAG = "CalendarPicker";
    private Dialog mCityPicker;
    private final boolean mCanDialogShow;
    public Callback mCallBack;

    private ChinaAddressInfo chinaAddressInfo;
    private List<String> provinceNameList = new ArrayList<>();
    private List<String> cityNameList = new ArrayList<>();
    private List<String> districtNameList = new ArrayList<>();
    private List<Province> provinceList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private List<District> districtList = new ArrayList<>();
    private Province selectProvince;
    private City selectCity;
    private District selectDistrict;
    private int selectPosition;




    /**
     * 时间选择结果回调接口
     */
    public interface Callback {

        /**
         * 选择地址的接口回调
         * @param province 省
         * @param city 市
         * @param district 区
         */
        void selectCity(Province province,City city,District district);
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
    public CityPicker(Context context, ChinaAddressInfo chinaAddressInfo,Callback callback) {
        if (context == null) {
            mCanDialogShow = false;
            return;
        }
        this.mCallBack = callback;
        this.chinaAddressInfo = chinaAddressInfo;
        mContext = context;
        //加载视图资源
        initView();
        //加载数据资源
        initData();

        mCanDialogShow = true;

    }


    private void initView() {
        mCityPicker = new Dialog(mContext, R.style.date_picker_dialog);
        mCityPicker.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCityPicker.setContentView(R.layout.picker_city);
        setGravity(Gravity.BOTTOM);
        mCityPicker.findViewById(R.id.iv_cancel).setOnClickListener(this);
        mCityPicker.findViewById(R.id.iv_confirm).setOnClickListener(this);

        mDpvProvince = mCityPicker.findViewById(R.id.dpv_province);
        mDpvCity = mCityPicker.findViewById(R.id.dpv_city);
        mDpvDistrict = mCityPicker.findViewById(R.id.dpv_district);
        mDpvProvince.setOnSelectListener(this);
        mDpvCity.setOnSelectListener(this);
        mDpvDistrict.setOnSelectListener(this);


    }
    /**
     * 设置弹窗位置
     * @param gravity 位置属性 默认为Gravity.BOTTOM
     */
    private void setGravity(int gravity) {
        Window window = mCityPicker.getWindow();
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
     * 设置当前显示的地区
     * @param province 省名
     * @param city 城市名
     * @param district 区名
     */
    public void setCity(String province, String city, String district) {
        cityNameList.clear();
        districtNameList.clear();
        int provinceIndex = provinceNameList.indexOf(province);

        cityList = chinaAddressInfo.provinceList.get(provinceIndex).cityList;
        for (City city1: chinaAddressInfo.provinceList.get(provinceIndex).cityList) {
            cityNameList.add(city1.cityName);
        }
        int cityIndex = cityNameList.indexOf(city);
        districtList = chinaAddressInfo.provinceList.get(provinceIndex).cityList.get(cityIndex).districtList;
        for (District district1: chinaAddressInfo.provinceList.get(provinceIndex).cityList.get(cityIndex).districtList) {
            districtNameList.add(district1.districtName);
        }
        int districtIndex = districtNameList.indexOf(district);


        if (provinceIndex!=-1){
            mDpvProvince.setSelected(provinceIndex);
            selectProvince = provinceList.get(provinceIndex);
        }


        if (cityIndex!=-1){
            mDpvCity.setSelected(cityIndex);
            selectCity = cityList.get(cityIndex);
        }


        if (districtIndex!=-1){
            mDpvDistrict.setSelected(districtIndex);
            selectDistrict = districtList.get(districtIndex);
        }

    }




    private void initData() {
        initDateUnits();
    }

    /**
     */
    private void initDateUnits() {

        provinceList = chinaAddressInfo.provinceList;
        for (Province pro: chinaAddressInfo.provinceList) {
            provinceNameList.add(pro.provinceName);
        }
        cityList = chinaAddressInfo.provinceList.get(0).cityList;
        for (City city: chinaAddressInfo.provinceList.get(0).cityList) {
            cityNameList.add(city.cityName);
        }
        districtList = chinaAddressInfo.provinceList.get(0).cityList.get(0).districtList;
        for (District district: chinaAddressInfo.provinceList.get(0).cityList.get(0).districtList) {
            districtNameList.add(district.districtName);
        }

        mDpvProvince.setDataList(provinceNameList);
        mDpvProvince.setSelected(0);
        selectProvince = provinceList.get(0);
        mDpvCity.setDataList(cityNameList);
        mDpvCity.setSelected(0);
        selectCity = cityList.get(0);
        mDpvDistrict.setDataList(districtNameList);
        mDpvDistrict.setSelected(0);
        selectDistrict = districtList.get(0);
        setCanScroll();
    }

    /**
     * 是否可以滑动
     */
    private void setCanScroll() {
        mDpvProvince.setCanScroll(provinceNameList.size() > 1);
        mDpvCity.setCanScroll(cityNameList.size() > 1);
        mDpvDistrict.setCanScroll(districtNameList.size() > 1);
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
                mCallBack.selectCity(selectProvince,selectCity,selectDistrict);
            }
        }
        if (mCityPicker != null && mCityPicker.isShowing()) {
            mCityPicker.dismiss();
        }

    }

    @Override
    public void onSelect(View view, String selected,int position) {
        if (view == null || TextUtils.isEmpty(selected)) {
            return;
        }
        selectPosition = position;
        int i = view.getId();
        if (i == R.id.dpv_province) {
            selectProvince = chinaAddressInfo.provinceList.get(position);
            linkageCityUnit(true, LINKAGE_DELAY_DEFAULT);
        }
        else if (i == R.id.dpv_city) {
            selectCity = cityList.get(position);
            linkageDistrictUnit(true, LINKAGE_DELAY_DEFAULT);
        }
        else if (i == R.id.dpv_district) {
            selectDistrict = districtList.get(position);
        }
    }



    /**
     * 联动“市”变化
     *
     * @param showAnim 是否展示滚动动画
     * @param delay    联动下一级延迟时间
     */
    private void linkageCityUnit(final boolean showAnim, final long delay) {

        Province province = chinaAddressInfo.provinceList.get(selectPosition);
        cityList = province.cityList;
        cityNameList.clear();
        for (City city: cityList) {
            cityNameList.add(city.cityName);
        }
        selectCity = cityList.get(0);
        mDpvCity.setDataList(cityNameList);

        setCanScroll();
        if (showAnim) {
            mDpvCity.startAnim();
        }
        mDpvCity.postDelayed(new Runnable() {
            @Override
            public void run() {
                linkageDistrictUnit(showAnim, delay);
            }
        }, delay);
    }

    /**
     * 联动“区”变化
     *
     * @param showAnim 是否展示滚动动画
     * @param delay    联动下一级延迟时间
     */
    private void linkageDistrictUnit(final boolean showAnim, final long delay) {

        City city = cityList.get(selectPosition);
        districtList = city.districtList;
        districtNameList.clear();
        for (District district:districtList) {
            districtNameList.add(district.districtName);
        }
        selectDistrict = districtList.get(0);
        mDpvDistrict.setDataList(districtNameList);

        // TODO: 2020/1/2 添加滑动判断
        setCanScroll();
        if (showAnim) {
            mDpvDistrict.startAnim();
        }

    }



    /**
     * 展示时间选择器
     */
    public void show() {
        if (!canShow()) {
            return;
        }
        mCityPicker.show();
    }





    /**
     * 设置日期控件是否可以循环滚动
     */
    public void setScrollLoop(boolean canLoop) {
        if (!canShow()) {
            return;
        }


        mDpvProvince.setCanScrollLoop(canLoop);
        mDpvCity.setCanScrollLoop(canLoop);
        mDpvDistrict.setCanScrollLoop(canLoop);

    }

    private boolean canShow() {
        return mCanDialogShow && mCityPicker != null;
    }
    /**
     * 设置是否允许点击屏幕或物理返回键关闭
     */
    public void setCancelable(boolean cancelable) {
        if (!canShow()) {
            return;
        }

        mCityPicker.setCancelable(cancelable);
    }
    /**
     * 设置日期控件是否展示滚动动画
     */
    public void setCanShowAnim(boolean canShowAnim) {
        if (!canShow()) {
            return;
        }
        mDpvProvince.setCanShowAnim(canShowAnim);
        mDpvCity.setCanShowAnim(canShowAnim);
        mDpvDistrict.setCanShowAnim(canShowAnim);

    }


}
