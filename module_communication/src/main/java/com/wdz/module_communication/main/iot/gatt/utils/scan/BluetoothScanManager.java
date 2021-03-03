package com.wdz.module_communication.main.iot.gatt.utils.scan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wdz.module_communication.main.iot.gatt.listener.OnBleScanListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 蓝牙扫描管理类
 *
 * @Author dezhi.wang
 * @Date 2021/2/25 11:05
 */
public class BluetoothScanManager {
    private final String TAG = this.getClass().getSimpleName();
    public static int FILTER_MAC = 1;
    public static int FILTER_UUID = 2;
    private String scanFilter = null;
    /**
     * 默认扫描过滤条件使用mac地址过滤
     */
    private int scanFilterType = FILTER_MAC;
    /**
     * 默认为低功耗扫描模式
     */
    private int scanMode = ScanSettings.SCAN_MODE_LOW_POWER;
    private long scanTimeOut = 0;
    private BluetoothLeScanner bluetoothLeScanner;
    private OnBleScanListener onBleScanListener;
    private ScanStrategy scanStrategy;
    private LifecycleOwner lifecycleOwner;
    private Disposable disposable;

    private BluetoothScanManager(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //是否支持蓝牙硬件
        if (bluetoothAdapter != null) {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        } else {
            if (onBleScanListener != null) {
                onBleScanListener.onScanFail();
            }
        }
    }

    /**
     * 开始扫描
     */
    public void startScan() {
        //蓝牙是否开启
        if (checkIsSupportBle()) {
            if (scanFilterType == FILTER_MAC) {
                scanStrategy = new ScanStrategy(new ScanStrategyByMac(bluetoothLeScanner, scanCallback));
            } else {
                scanStrategy = new ScanStrategy(new ScanStrategyByUuid(bluetoothLeScanner, scanCallback));
            }
            createScanObservable();
        } else {
            if (onBleScanListener != null) {
                onBleScanListener.onScanFail();
            }
        }
    }

    /**
     * 创建扫描事件流
     */
    private void createScanObservable() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                scanStrategy.scan(scanMode, scanFilter);
            }
        })
                .timeout(scanTimeOut, TimeUnit.MILLISECONDS)
                .as(bindAutoDispose(lifecycleOwner))
                .subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Log.i(TAG, "onSubscribe: ");
                if (onBleScanListener != null) {
                    onBleScanListener.onStartScan();
                }

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
                if (onBleScanListener != null) {
                    onBleScanListener.onScanTimeOut();
                }
                stopScan();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        });
    }


    /**
     * 停止扫描
     */
    public void stopScan() {
        if (checkIsSupportBle()) {
            if (disposable!=null && !disposable.isDisposed()){
                disposable.dispose();
            }
            bluetoothLeScanner.stopScan(scanCallback);
            if (onBleScanListener != null) {
                onBleScanListener.onStopScan();
            }
        } else {
            if (onBleScanListener != null) {
                onBleScanListener.onScanFail();
            }
        }
    }


    /**
     * 蓝牙扫描回调
     */
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (onBleScanListener != null) {
                onBleScanListener.onScanResult(result);
            }
        }
    };


    private <T> AutoDisposeConverter<T> bindAutoDispose(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(lifecycleOwner, Lifecycle.Event.ON_DESTROY));
    }


    /**
     * 检查蓝牙是否打开/是否有蓝牙硬件
     *
     * @return
     */
    private boolean checkIsSupportBle() {
        //&&具有短路功能
        boolean isSupportBle = BluetoothAdapter.getDefaultAdapter() != null && BluetoothAdapter.getDefaultAdapter().isEnabled();
        Log.i(TAG, "checkIsSupportBle: " + isSupportBle);
        return isSupportBle;
    }


    /**
     * 扫描管理类建造者
     */
    public static class Builder {
        private BluetoothScanManager bluetoothScanManager;

        public Builder(LifecycleOwner lifecycleOwner) {
            bluetoothScanManager = new BluetoothScanManager(lifecycleOwner);
        }

        /**
         * 设置扫描过滤条件
         *
         * @return
         */
        public Builder setScanFilterType(int filterType) {
            bluetoothScanManager.scanFilterType = filterType;
            return this;
        }

        /**
         * 设置扫描过滤条件
         *
         * @return
         */
        public Builder setScanFilter(String scanFilter) {
            bluetoothScanManager.scanFilter = scanFilter;
            return this;
        }

        /**
         * 设置扫描模式
         *
         * @return
         */
        public Builder setScanMode(int scanMode) {
            bluetoothScanManager.scanMode = scanMode;
            return this;
        }

        /**
         * 设置扫描超时
         *
         * @return
         */
        public Builder setScanTimeOut(long scanTimeOut) {
            bluetoothScanManager.scanTimeOut = scanTimeOut;
            return this;
        }

        /**
         * 设置蓝牙扫描监听
         *
         * @param onBleScanListener
         * @return
         */
        public Builder setOnBluetoothScanListener(OnBleScanListener onBleScanListener) {
            bluetoothScanManager.onBleScanListener = onBleScanListener;
            return this;
        }

        public BluetoothScanManager build() {
            return bluetoothScanManager;
        }
    }
}
