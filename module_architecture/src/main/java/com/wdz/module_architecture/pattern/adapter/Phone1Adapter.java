package com.wdz.module_architecture.pattern.adapter;

import android.util.Log;

/*
 * 类适配器模式：采用继承源目标来实现，无需持有源目标对象
 * 继承被适配类，实现接口
 */
public class Phone1Adapter extends Electric implements Adapter {
    private static final String TAG = "PhoneAdapter";

    @Override
    public int convert_5v() {
        Log.i(TAG, "输入电压: "+getOutPut());
        return 5;
    }
}
