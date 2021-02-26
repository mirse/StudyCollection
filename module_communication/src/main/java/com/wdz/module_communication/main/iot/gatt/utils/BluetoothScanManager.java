package com.wdz.module_communication.main.iot.gatt.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.ParcelUuid;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙扫描管理类
 * @Author dezhi.wang
 * @Date 2021/2/25 11:05
 */
public class BluetoothScanManager extends ScanCallback {
    private final String TAG = this.getClass().getSimpleName();
    public static int FILTER_MAC = 1;
    public static int FILTER_UUID = 2;
    private String scanFilter = null;
    private int scanFilterType = -1;
    private int scanMode = -1;
    private long scanTimeOut = 0;
    private BluetoothLeScanner bluetoothLeScanner;
    private OnBleScanListener onBleScanListener;

    public BluetoothScanManager() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //是否支持蓝牙硬件
        if (bluetoothAdapter!=null){
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        }
        else{
            if (onBleScanListener !=null){
                onBleScanListener.onScanFail();
            }
        }
    }

    /**
     * 开始扫描
     */
    public void startScan(){
        //蓝牙是否开启
        if (checkIsSupportBle()){
            ScanSettings settings = null;
            List<ScanFilter> filters = null;
            if (scanMode!=-1){
                settings = new ScanSettings.Builder()
                        .setScanMode(scanMode)
                        .build();
            }
            if (scanFilterType != -1){
                if (scanFilterType == FILTER_MAC){
                    if (scanFilter!=null){
                        filters = new ArrayList<>();
                        ScanFilter filter = new ScanFilter.Builder()
                                .setDeviceAddress(scanFilter)
                                .build();
                        filters.add(filter);
                    }
                }
                else  if (scanFilterType == FILTER_UUID){
                    if (scanFilter!=null){
                        ParcelUuid parcelUuid = ParcelUuid.fromString(scanFilter);
                        filters = new ArrayList<>();
                        ScanFilter filter = new ScanFilter.Builder()
                                .setServiceUuid(parcelUuid)
                                .build();
                        filters.add(filter);
                    }
                }
            }
            if (settings != null && filters !=null){
                bluetoothLeScanner.startScan(filters,settings,this);

            }
            else{
                bluetoothLeScanner.startScan(this);
            }
            setTimeout();

            if (onBleScanListener !=null){
                onBleScanListener.onStartScan();
            }
        }
        else{
            if (onBleScanListener !=null){
                onBleScanListener.onScanFail();
            }
        }
    }

    /**
     * 设置扫描超时
     */
    private void setTimeout() {
        if (scanTimeOut != 0){
            TimeoutUtil.setTimeout(scanTimeOut, new TimeoutUtil.OnTimeoutListener() {
                @Override
                public void onTimeoutStart() {

                }

                @Override
                public void onTimeoutFinish() {
                    if (onBleScanListener !=null){
                        onBleScanListener.onScanTimeOut();
                    }
                    stopScan();
                }
            });
        }
    }

    /**
     * 停止扫描
     */
    public void stopScan(){
        if (checkIsSupportBle()){
            if (scanTimeOut != 0){
                TimeoutUtil.clearAllTimeout();
            }
            bluetoothLeScanner.stopScan(this);
            if (onBleScanListener !=null){
                onBleScanListener.onStopScan();
            }
        }
        else{
            if (onBleScanListener !=null){
                onBleScanListener.onScanFail();
            }
        }
    }

    /**
     * 蓝牙扫描回调
     * @param callbackType
     * @param result
     */
    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        if (onBleScanListener !=null){
            onBleScanListener.onScanResult(result);
        }
    }


    /**
     * 检查蓝牙是否打开/是否有蓝牙硬件
     * @return
     */
    private boolean checkIsSupportBle(){
        //&&具有短路功能
        boolean isSupportBle = BluetoothAdapter.getDefaultAdapter() !=null && BluetoothAdapter.getDefaultAdapter().isEnabled();
        Log.i(TAG, "checkIsSupportBle: "+isSupportBle);
        return isSupportBle;
    }


    /**
     * 扫描管理类建造者
     */
    public static class Builder{
        private BluetoothScanManager bluetoothScanManager;

        public Builder() {
            bluetoothScanManager = new BluetoothScanManager();
        }

        /**
         * 设置扫描过滤条件
         * @return
         */
        public Builder setScanFilterType(int filterType){
            bluetoothScanManager.scanFilterType = filterType;
            return this;
        }

        /**
         * 设置扫描过滤条件
         * @return
         */
        public Builder setScanFilter(String scanFilter){
            bluetoothScanManager.scanFilter = scanFilter;
            return this;
        }

        /**
         * 设置扫描模式
         * @return
         */
        public Builder setScanMode(int scanMode){
            bluetoothScanManager.scanMode = scanMode;
            return this;
        }

        /**
         * 设置扫描超时
         * @return
         */
        public Builder setScanTimeOut(long scanTimeOut){
            bluetoothScanManager.scanTimeOut = scanTimeOut;
            return this;
        }

        /**
         * 设置蓝牙扫描监听
         * @param onBleScanListener
         * @return
         */
        public Builder setOnBluetoothScanListener(OnBleScanListener onBleScanListener){
            bluetoothScanManager.onBleScanListener = onBleScanListener;
            return this;
        }
        public BluetoothScanManager build(){
            return bluetoothScanManager;
        }
    }
}
