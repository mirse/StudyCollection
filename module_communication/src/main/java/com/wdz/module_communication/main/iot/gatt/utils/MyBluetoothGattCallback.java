package com.wdz.module_communication.main.iot.gatt.utils;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.wdz.common.util.ByteUtil;

import java.math.BigInteger;

import io.reactivex.ObservableEmitter;

/**
 * @Author dezhi.wang
 * @Date 2021/3/2 17:18
 */
public class MyBluetoothGattCallback extends BluetoothGattCallback {
    private final String TAG = this.getClass().getSimpleName();
    private ObservableEmitter emitter;
    /**
     * 当前gatt连接状态
     */
    private boolean mIsConnected = false;

    private MutableLiveData<Boolean> connectStatusLiveData = new MutableLiveData<>();
    private String address;

    /**
     * 获取当前的连接状态
     * @return
     */
    boolean isConnected() {
        return mIsConnected;
    }


    /**
     * 获取连接的地址
     * @return
     */
    String getConnectAddress() {
        return address;
    }

    /**
     * 获取当前的连接状态的可被观察的数据持有类
     * @return
     */
    MutableLiveData<Boolean> getConnectStatusLiveData(){
        return connectStatusLiveData;
    }


    void setEmitter(ObservableEmitter emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);

        Log.i(TAG, "onConnectionStateChange: status:"+status+" newState:"+newState);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
                address = gatt.getDevice().getAddress();
                mIsConnected = true;
                connectStatusLiveData.postValue(true);

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                gatt.close();
                if (!emitter.isDisposed()) {
                    emitter.onError(new Throwable("Connect Fail"));
                }
                mIsConnected = false;
                connectStatusLiveData.postValue(false);

            }

        } else {
            gatt.close();
            if (!emitter.isDisposed()) {
                emitter.onError(new Throwable("Connect Fail"));
            }
            mIsConnected = false;
            connectStatusLiveData.postValue(false);

        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        Log.i(TAG, "onServicesDiscovered: "+status);
        Log.i(TAG, "onServicesDiscovered: "+gatt.getServices());
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        }
        else {
            if (!emitter.isDisposed()) {
                emitter.onError(new Throwable());
            }
        }

    }

    @Override
    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        super.onMtuChanged(gatt, mtu, status);
        if (status == BluetoothGatt.GATT_SUCCESS){
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        }
        else{
            if (!emitter.isDisposed()) {
                emitter.onError(new Throwable());
            }
        }

    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if (status == BluetoothGatt.GATT_SUCCESS){
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        }
        else{
            if (!emitter.isDisposed()) {
                emitter.onError(new Throwable());
            }
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        Log.i(TAG, "onCharacteristicChanged: "+ ByteUtil.byte2HexString(characteristic.getValue()));
        if (!emitter.isDisposed()) {
            emitter.onNext(characteristic);
            emitter.onComplete();
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        Log.i(TAG, "onCharacteristicWrite: "+status+" emitter.isDisposed():"+emitter.isDisposed());
        if (!emitter.isDisposed()) {
            //Log.i(TAG, "onCharacteristicWrite emitter.onNext");
            emitter.onNext(characteristic);
            emitter.onComplete();
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        Log.i(TAG, "onCharacteristicRead: "+status+" characteristic:"+new BigInteger(1,characteristic.getValue()).toString(16));
        if (!emitter.isDisposed()) {
            emitter.onNext(characteristic);
            emitter.onComplete();
        }
    }
}
