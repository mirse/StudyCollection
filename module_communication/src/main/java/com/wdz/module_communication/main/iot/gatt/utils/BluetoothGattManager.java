package com.wdz.module_communication.main.iot.gatt.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.wdz.common.util.StringUtils;
import com.wdz.module_communication.main.iot.gatt.listener.OnBleStateListener;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.NOTIFY_DESCRIPTOR;

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
    private MyBluetoothGattCallback bluetoothGattCallback = new MyBluetoothGattCallback();

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
     * 获取当前的连接状态
     * @return
     */
    public boolean isConnected() {
        return bluetoothGattCallback.isConnected();
    }

    /**
     * 获取当前的连接状态的可被观察的数据持有类
     * @return
     */
    public MutableLiveData<Boolean> getConnectStatusLiveData(){
        return bluetoothGattCallback.getConnectStatusLiveData();
    }


    /**
     * gatt连接操作
     *
     * @param address 设备mac地址
     */
    public void connect(String address) {
        if (checkIsSupportBle()) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            //判断mac地址是否符合规范
            if (StringUtils.isMacPattern(address)) {
                final BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
                gattConnectOperateObservable(bluetoothDevice)
                        //指定Observable自身在哪个调度器上执行
                        .subscribeOn(Schedulers.io())
                        //指定一个观察者在哪个调度器上观察这个Observable
                        .observeOn(AndroidSchedulers.mainThread())
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
                                if (bluetoothGatt != null) {
                                    bluetoothGatt.disconnect();
                                    bluetoothGatt.close();
                                    bluetoothGatt = null;
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
            }
            else {
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
        gattDisconnectOperateObservable()
                .timeout(retryTimeout, TimeUnit.MILLISECONDS)
                //每个observable对象的操作符在哪个线程上运行
                .subscribeOn(Schedulers.io())
                //每个Subscriber(Observer)对象的操作符在哪个线程上运行
                .observeOn(AndroidSchedulers.mainThread())
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
                        Log.i(TAG, "gattDisconnectOperateObservable onError: ");
                        if (bluetoothGatt != null) {
                            bluetoothGatt.close();
                            bluetoothGatt = null;
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
     * 找服务操作
     */
    public void discoverServices() {
        discoverServiceOperateObservable()
                //每个observable对象的操作符在哪个线程上运行
                .subscribeOn(Schedulers.io())
                //每个Subscriber(Observer)对象的操作符在哪个线程上运行
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(7 * 1000, TimeUnit.MILLISECONDS)
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
                        if (onBleStateListener != null && bluetoothGatt != null) {
                            onBleStateListener.onServicesDiscovered(false, bluetoothGatt);
                        } else {
                            if (onBleStateListener != null) {
                                onBleStateListener.onGattDisconnected();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (onBleStateListener != null && bluetoothGatt != null) {
                            onBleStateListener.onServicesDiscovered(true, bluetoothGatt);
                        } else {
                            if (onBleStateListener != null) {
                                onBleStateListener.onGattDisconnected();
                            }
                        }
                    }
                });
    }

    /**
     * 找服务操作
     */
    public void changeMtu(final int mtu) {
        //Android5.1才支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeMtuOperateObservable(mtu)
                    //每个observable对象的操作符在哪个线程上运行
                    .subscribeOn(Schedulers.io())
                    //每个Subscriber(Observer)对象的操作符在哪个线程上运行
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(7 * 1000, TimeUnit.MILLISECONDS)
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
                            if (onBleStateListener != null && bluetoothGatt != null) {
                                onBleStateListener.onMtuChanged(false, mtu);
                            } else {
                                if (onBleStateListener != null) {
                                    onBleStateListener.onGattDisconnected();
                                }
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (onBleStateListener != null && bluetoothGatt != null) {
                                onBleStateListener.onMtuChanged(true, mtu);
                            } else {
                                if (onBleStateListener != null) {
                                    onBleStateListener.onGattDisconnected();
                                }
                            }
                        }
                    });
        }

    }

    /**
     * 设置特征变化通知
     */
    public void setCharacteristicNotification(UUID serviceUuid, UUID characteristicUuid,boolean enabled) {
        setCharacteristicNotificationObservable(serviceUuid, characteristicUuid,enabled)
                //每个observable对象的操作符在哪个线程上运行
                .subscribeOn(Schedulers.io())
                //每个Subscriber(Observer)对象的操作符在哪个线程上运行
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(7 * 1000, TimeUnit.MILLISECONDS)
                .as(bindAutoDispose(lifecycleOwner))
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //EC:1B:BD:**:**:**
                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onBleStateListener != null) {
                            onBleStateListener.onCharacteristicNotificationChanged(false);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (onBleStateListener != null) {
                            onBleStateListener.onCharacteristicNotificationChanged(true);
                        }

                    }
                });
    }

    /**
     * 写数据
     */
    public void writeData(String content, UUID serviceUuid, UUID characteristicUuid) {
        writeDataObservable(content, serviceUuid, characteristicUuid)
                //每个observable对象的操作符在哪个线程上运行
                .subscribeOn(Schedulers.io())
                //每个Subscriber(Observer)对象的操作符在哪个线程上运行
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(7 * 1000, TimeUnit.MILLISECONDS)
                .as(bindAutoDispose(lifecycleOwner))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (o instanceof BluetoothGattCharacteristic && onBleStateListener != null) {
                            BluetoothGattCharacteristic bluetoothGattCharacteristic = (BluetoothGattCharacteristic) o;
                            onBleStateListener.onCharacteristicChanged(true, bluetoothGattCharacteristic);
                        } else if (onBleStateListener != null) {
                            onBleStateListener.onCharacteristicChanged(false, null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (onBleStateListener != null) {
                            onBleStateListener.onCharacteristicChanged(false, null);
                        }
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
                if (bluetoothGatt != null) {
                    bluetoothGatt.disconnect();
                    bluetoothGatt.close();
                }
                bluetoothGattCallback.setEmitter(emitter);
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
                } else {
                    if (onBleStateListener != null) {
                        onBleStateListener.onGattDisconnected();
                    }
                }
            }
        });
    }

    /**
     * 创建discover Service被观察者
     *
     * @return
     */
    private Observable<Object> discoverServiceOperateObservable() {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (bluetoothGatt != null) {
                    bluetoothGattCallback.setEmitter(emitter);
                    bluetoothGatt.discoverServices();
                } else {
                    if (onBleStateListener != null) {
                        onBleStateListener.onGattDisconnected();
                    }
                }
            }
        });
    }

    /**
     * 创建change mtu被观察者
     *
     * @param mtu
     * @return
     */
    private Observable<Object> changeMtuOperateObservable(final int mtu) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (bluetoothGatt != null) {
                    bluetoothGattCallback.setEmitter(emitter);
                    //bluetoothGatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);//修改蓝牙的连接参数，影响传输数据的速度
                    bluetoothGatt.requestMtu(mtu);
                } else {
                    if (onBleStateListener != null) {
                        onBleStateListener.onGattDisconnected();
                    }
                }
            }
        });
    }


    /**
     * 创建setCharacteristicNotification被观察者
     *
     * @param serviceUuid
     * @param characteristicUuid
     * @return
     */
    private Observable<Object> setCharacteristicNotificationObservable(final UUID serviceUuid, final UUID characteristicUuid,final boolean enabled) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (bluetoothGatt != null) {
                    bluetoothGattCallback.setEmitter(emitter);
                    BluetoothGattService bluetoothGattService = bluetoothGatt.getService(serviceUuid);
                    if (bluetoothGattService != null) {
                        BluetoothGattCharacteristic characteristic = bluetoothGattService.getCharacteristic(characteristicUuid);
                        if (characteristic != null) {
                            boolean b = bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
                            if (b) {
                                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(NOTIFY_DESCRIPTOR));
                                descriptor.setValue(enabled?BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE:BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                                bluetoothGatt.writeDescriptor(descriptor);
                            } else {
                                if (onBleStateListener != null) {
                                    onBleStateListener.onCharacteristicNotificationChanged(false);
                                }
                            }
                        } else {
                            if (onBleStateListener != null) {
                                onBleStateListener.onCharacteristicNotificationChanged(false);
                            }
                        }
                    } else {
                        if (onBleStateListener != null) {
                            onBleStateListener.onCharacteristicNotificationChanged(false);
                        }
                    }

                } else {
                    if (onBleStateListener != null) {
                        onBleStateListener.onGattDisconnected();
                    }
                }
            }
        });
    }

    /**
     * 创建writeData被观察者
     *
     * @param content
     * @param serviceUuid
     * @param characteristicUuid
     * @return
     */
    private Observable<Object> writeDataObservable(final String content, final UUID serviceUuid, final UUID characteristicUuid) {
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if (bluetoothGatt != null) {
                    bluetoothGattCallback.setEmitter(emitter);
                    BluetoothGattService bluetoothGattService = bluetoothGatt.getService(serviceUuid);
                    if (bluetoothGattService != null) {
                        BluetoothGattCharacteristic characteristic = bluetoothGattService.getCharacteristic(characteristicUuid);
                        if (characteristic != null) {
                            characteristic.setValue(content.getBytes());
                            boolean status = false;
                            while (!status) {
                                status = bluetoothGatt.writeCharacteristic(characteristic);
                            }

                        } else {
                            if (onBleStateListener != null) {
                                onBleStateListener.onCharacteristicChanged(false, null);
                            }
                        }
                    } else {
                        if (onBleStateListener != null) {
                            onBleStateListener.onCharacteristicChanged(false, null);
                        }
                    }

                } else {
                    if (onBleStateListener != null) {
                        onBleStateListener.onGattDisconnected();
                    }
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
