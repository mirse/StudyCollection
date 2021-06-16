package com.wdz.module_architecture.pattern.adapter;

import android.util.Log;

/*
 * 对象适配器模式
 */
public class PhoneAdapter implements Adapter {
    private static final String TAG = "PhoneAdapter";
    private Electric electric;

    public PhoneAdapter(Electric electric) {
        this.electric = electric;
    }

    @Override
    public int convert_5v() {
        Log.i(TAG, "输入电压: "+electric.getOutPut());
        return 5;
    }
}
