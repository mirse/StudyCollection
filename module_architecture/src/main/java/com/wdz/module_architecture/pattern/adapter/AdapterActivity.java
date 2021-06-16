package com.wdz.module_architecture.pattern.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;

/**
 * 适配器模式 适用场景：
 * 1、需要一个统一的输出接口，输入端的接口不可预知
 * 2、系统需要使用现有的类，而此类的接口不满足系统的需求，接口不兼容
 */
@Route(path = ARouterConstant.ACTIVITY_ADAPTER_DEMO)
public class AdapterActivity extends AppCompatActivity {
    private static final String TAG = "AdapterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        Phone1Adapter phone1Adapter = new Phone1Adapter();
        Log.i(TAG, "-------类适配器模式-------");
        int convert5v = phone1Adapter.convert_5v();
        Log.i(TAG, "转换后的输出电压："+convert5v);


        PhoneAdapter phoneAdapter = new PhoneAdapter(new Electric());
        Log.i(TAG, "-----对象适配器模式------");
        int convert_5v = phoneAdapter.convert_5v();
        Log.i(TAG, "转换后的输出电压："+convert_5v);
    }
}