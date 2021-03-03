package com.wdz.module_communication.main.iot.gatt.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wdz.common.util.StringUtils;
import com.wdz.module_communication.main.iot.gatt.listener.OnBleStateListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 蓝牙管理类：扫描、连接、数据传输及相关回调
 */
public class BluetoothGattManager {
    private final String TAG = this.getClass().getSimpleName();
    private int retryCount;
    private int retryTimeout;
    private OnBleStateListener onBleStateListener;
    private static Context mContext;

    private BluetoothGatt bluetoothGatt;
    private LifecycleOwner lifecycleOwner;
    private MyBluetoothGattCallback<Object> bluetoothGattCallback;

    private BluetoothGattManager(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }


    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
    }

    /**
     * gatt连接操作
     * @param address 设备mac地址
     */
    public void connect(String address) {
        if (checkIsSupportBle()) {
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
                                if (bluetoothGatt != null){
                                    bluetoothGatt.disconnect();
                                    bluetoothGatt.close();
                                }
                                if (onBleStateListener != null) {
                                    onBleStateListener.onGattDisconnected();
                                }
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG, "onComplete: ");
                                if (onBleStateListener != null) {
                                    onBleStateListener.onGattConnected(bluetoothGatt);
                                }
                            }
                        });
            } else {
                if (onBleStateListener != null) {
                    onBleStateListener.onGattDisconnected();
                }
            }
        } else {
            if (onBleStateListener != null) {
                onBleStateListener.onGattDisconnected();
            }
        }
    }

    /**
     * gatt断开连接操作
     */
    public void disconnect() {
        gattDisconnectOperateObservable().timeout(retryTimeout, TimeUnit.MILLISECONDS).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "gattDisconnectOperateObservable onError: ");
                if (bluetoothGatt != null){
                    bluetoothGatt.close();
                }
                if (onBleStateListener != null) {
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
     *
     * @param bluetoothDevice
     * @return
     */
    private Observable<Object> gattConnectOperateObservable(final BluetoothDevice bluetoothDevice) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                Log.i(TAG, "subscribe: bluetoothDevice.connectGatt");
                if (bluetoothGatt!=null){
                    bluetoothGatt.disconnect();
                    bluetoothGatt.close();
                }
                bluetoothGattCallback = new MyBluetoothGattCallback<>(emitter);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    bluetoothGatt = bluetoothDevice.connectGatt(mContext, false, bluetoothGattCallback);
                }//高于Android6.0，设置双模模式
                else {
                    bluetoothGatt = bluetoothDevice.connectGatt(mContext, false, bluetoothGattCallback);
                }

            }
        })
                //出现错误时，将发送的错误传递给一个新的被观察者，并决定是否需要重新订阅被观察者->发送事件
                //.retryWhen(new RetryWithDelay(3, 7 * 1000));
                //先设置重试次数，再设置总超时时间
                //.retry(retryCount).timeout(7*1000, TimeUnit.MILLISECONDS);
                //先设置单次超时，再设置重试次数
                .timeout(retryTimeout, TimeUnit.MILLISECONDS).retry(retryCount);
    }

    /**
     * 创建gatt断开连接被观察者
     *
     * @return
     */
    private Observable<Object> gattDisconnectOperateObservable() {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (bluetoothGatt != null) {
                    bluetoothGattCallback.setEmitter(emitter);
                    bluetoothGatt.disconnect();
                }
            }
        });
    }

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
     * 蓝牙管理类建造者
     */
    public static class Builder {
        private BluetoothGattManager bluetoothGattManager;

        public Builder(LifecycleOwner lifecycleOwner) {
            bluetoothGattManager = new BluetoothGattManager(lifecycleOwner);
        }

        /**
         * 设置连接重试次数
         *
         * @param count
         * @return
         */
        public Builder setRetryCount(int count) {
            bluetoothGattManager.retryCount = count;
            return this;
        }

        /**
         * 设置每次连接重试超时
         *
         * @param retryTimeout
         * @return
         */
        public Builder setRetryTimeout(int retryTimeout) {
            bluetoothGattManager.retryTimeout = retryTimeout;
            return this;
        }

        /**
         * 设置蓝牙状态监听
         *
         * @param onBleStateListener
         * @return
         */
        public Builder setOnBleStateListener(OnBleStateListener onBleStateListener) {
            bluetoothGattManager.onBleStateListener = onBleStateListener;
            return this;
        }

        public BluetoothGattManager build() {
            return bluetoothGattManager;
        }
    }

}
