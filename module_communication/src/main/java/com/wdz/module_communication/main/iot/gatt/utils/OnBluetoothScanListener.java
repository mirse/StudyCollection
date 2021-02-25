package com.wdz.module_communication.main.iot.gatt.utils;

import android.bluetooth.le.ScanResult;

/**
 * 蓝牙扫描监听类
 * @Author dezhi.wang
 * @Date 2021/2/25 11:31
 */
public interface OnBluetoothScanListener {
    /**
     * 开始扫描
     */
    void onStartScan();

    /**
     * 停止扫描
     */
    void onStopScan();

    /**
     * 未扫描到指定设备导致超时
     */
    void onScanTimeOut();

    /**
     * 扫描结果
     */
    void onScanResult(ScanResult result);

    /**
     * 扫描失败
     */
    void onScanFail();
}
