package com.wdz.module_communication.main.iot.gatt.utils;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.le.ScanResult;

/**
 * 蓝牙连接监听类
 * @Author dezhi.wang
 * @Date 2021/2/25 11:31
 */
public interface OnBleStateListener {
    /**
     * gatt连接成功
     */
    void onGattConnected();

    /**
     * gatt连接失败
     */
    void onGattDisconnected();


}
