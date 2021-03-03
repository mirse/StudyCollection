package com.wdz.module_communication.main.iot.gatt.utils;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.wdz.module_communication.main.network.handler.model.Login;

import io.reactivex.ObservableEmitter;

/**
 * @Author dezhi.wang
 * @Date 2021/3/2 17:18
 */
public class MyBluetoothGattCallback<T> extends BluetoothGattCallback {
    private final String TAG = this.getClass().getSimpleName();
    private ObservableEmitter<T> emitter;

    MyBluetoothGattCallback(ObservableEmitter<T> emitter) {
        this.emitter = emitter;
    }

    public void setEmitter(ObservableEmitter<T> emitter) {
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

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                gatt.close();
                if (!emitter.isDisposed()) {
                    emitter.onError(new Throwable("Connect Fail"));
                }
            }

        } else {
            gatt.close();
            if (!emitter.isDisposed()) {
                emitter.onError(new Throwable("Connect Fail"));
            }
        }
    }
}
