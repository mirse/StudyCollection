package com.wdz.module_communication.main.iot.gatt.utils.scan;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;

/**
 * 抽象策略角色 - 扫描
 * @Author dezhi.wang
 * @Date 2021/3/3 9:04
 */
public abstract class AbstractScanner {
    BluetoothLeScanner bluetoothLeScanner;
    public ScanCallback callback;
    AbstractScanner(BluetoothLeScanner bluetoothLeScanner, ScanCallback callback) {
        this.bluetoothLeScanner = bluetoothLeScanner;
        this.callback = callback;
    }

    /**
     *
     */
    public abstract void scan(int scanMode,String scanFilter);
}
