package com.wdz.module_communication.main.iot.gatt.utils.scan;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.os.ParcelUuid;

import java.util.ArrayList;
import java.util.List;

/**
 * 按ServiceUuid地址扫描
 * @Author dezhi.wang
 * @Date 2021/3/3 9:04
 */
public class ScanStrategyByUuid extends AbstractScanner {

    ScanStrategyByUuid(BluetoothLeScanner bluetoothLeScanner, ScanCallback callback) {
        super(bluetoothLeScanner, callback);
    }

    @Override
    public void scan(int scanMode, String scanFilter) {
        if (scanFilter!=null){
            ScanSettings settings = new ScanSettings.Builder()
                    .setScanMode(scanMode)
                    .build();

            ParcelUuid parcelUuid = ParcelUuid.fromString(scanFilter);
            List<ScanFilter> filters = new ArrayList<>();
            ScanFilter filter = new ScanFilter.Builder()
                    .setServiceUuid(parcelUuid)
                    .build();
            filters.add(filter);
            bluetoothLeScanner.startScan(filters,settings,callback);
        }
        else{
            bluetoothLeScanner.startScan(callback);
        }




    }

}
