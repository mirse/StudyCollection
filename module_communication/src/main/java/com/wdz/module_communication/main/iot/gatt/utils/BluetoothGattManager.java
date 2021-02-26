package com.wdz.module_communication.main.iot.gatt.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wdz.common.util.StringUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 蓝牙管理类：扫描、连接、数据传输及相关回调
 * @Author dezhi.wang
 * @Date 2021/2/25 10:57
 */
public class BluetoothGattManager {
    private final String TAG = this.getClass().getSimpleName();
    private int retryCount;
    private OnBleStateListener onBleStateListener;
    private static Context mContext;

    private static volatile BluetoothGattManager singleton;
    private ObservableEmitter<Object> myEmitter;
    private BluetoothGatt bluetoothGatt;
    private LifecycleOwner lifecycleOwner;

    private BluetoothGattManager(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }


    /**
     * 初始化
     * @param context
     */
    public static void init(Context context){
        mContext = context;
    }

    public void connect(String address){
        if (checkIsSupportBle()){
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            //判断mac地址是否符合规范
            if (StringUtils.isMacPattern(address)) {
                final BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
                gattConnectOperateObservable(bluetoothDevice)
                        .as(bindAutoDispose(lifecycleOwner))
                        .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
                        if (onBleStateListener!=null){
                            onBleStateListener.onGattDisconnected();
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                        if (onBleStateListener!=null){
                            onBleStateListener.onGattConnected();
                        }
                    }
                });
            }
            else{
                if (onBleStateListener!=null){
                    onBleStateListener.onGattDisconnected();
                }
            }
        }
        else{
            if (onBleStateListener!=null){
                onBleStateListener.onGattDisconnected();
            }
        }
    }
    public void disconnect(){
        gattDisconnectOperateObservable().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {
                if (onBleStateListener!=null){
                    onBleStateListener.onGattDisconnected();
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 创建gatt连接被观察者
     * @param bluetoothDevice
     * @return
     */
    private Observable<Object> gattConnectOperateObservable(final BluetoothDevice bluetoothDevice){
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                myEmitter = emitter;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                bluetoothGatt = bluetoothDevice.connectGatt(mContext, false, mBluetoothGattCallback);
            }
            //高于Android6.0，设置双模模式
                else{
                bluetoothGatt = bluetoothDevice.connectGatt(mContext, false, mBluetoothGattCallback);
            }
        }
        }).retry(retryCount).timeout(7*1000, TimeUnit.MILLISECONDS);
    }

    /**
     * 创建gatt断开连接被观察者
     * @return
     */
    private Observable<Object> gattDisconnectOperateObservable(){
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (bluetoothGatt!=null){
                    myEmitter = emitter;
                    bluetoothGatt.disconnect();
                }
            }
        });
    }


    private BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (status == BluetoothGatt.GATT_SUCCESS){
                if (newState == BluetoothProfile.STATE_CONNECTED){
                    if (!myEmitter.isDisposed()){
                        myEmitter.onComplete();
                    }

                }
                else if (newState == BluetoothProfile.STATE_DISCONNECTED){
                    gatt.close();
                    if (!myEmitter.isDisposed()) {
                        myEmitter.onError(new Throwable("Connect Fail"));
                    }
                }

            }
            else{
                gatt.close();
                if (!myEmitter.isDisposed()) {
                    myEmitter.onError(new Throwable("Connect Fail"));
                }
            }
        }
    };

    private <T> AutoDisposeConverter<T> bindAutoDispose(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(lifecycleOwner, Lifecycle.Event.ON_DESTROY));
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
     * 蓝牙管理类建造者
     */
    public static class Builder{
        private BluetoothGattManager bluetoothGattManager;

        public Builder(LifecycleOwner lifecycleOwner) {
            bluetoothGattManager = new BluetoothGattManager(lifecycleOwner);
        }
        /**
         * 设置连接重试次数
         * @param count
         * @return
         */
        public Builder setRetryCount(int count){
            bluetoothGattManager.retryCount = count;
            return this;
        }
        /**
         * 设置蓝牙状态监听
         * @param onBleStateListener
         * @return
         */
        public Builder setOnBleStateListener(OnBleStateListener onBleStateListener){
            bluetoothGattManager.onBleStateListener = onBleStateListener;
            return this;
        }

        public BluetoothGattManager build(){
            return bluetoothGattManager;
        }
    }

}
