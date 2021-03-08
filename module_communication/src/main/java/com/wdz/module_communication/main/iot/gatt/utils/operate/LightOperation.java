package com.wdz.module_communication.main.iot.gatt.utils.operate;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import androidx.lifecycle.LifecycleOwner;

import com.google.gson.Gson;
import com.wdz.module_communication.main.iot.gatt.bean.BlinkSingleRequest;
import com.wdz.module_communication.main.iot.gatt.bean.RemoteBlinkSingleRequest;
import com.wdz.module_communication.main.iot.gatt.listener.OnBleStateListener;
import com.wdz.module_communication.main.iot.gatt.utils.BluetoothGattManager;

import java.util.UUID;

import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_CHARACTERISTIC_NOTIFY_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_CHARACTERISTIC_WRITE_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_SERVICE_UUID;

/**
 * @Author dezhi.wang
 * @Date 2021/3/8 8:57
 */
public class LightOperation {

    private BluetoothGattManager bluetoothGattManager;

    private static volatile LightOperation lightOperation;
    private LightOperation(LifecycleOwner lifecycleOwner) {
        bluetoothGattManager = new BluetoothGattManager.Builder(lifecycleOwner)
                .setRetryCount(2)
                .setRetryTimeout(7*1000)
                .build();
    }
    public static LightOperation getInstance(LifecycleOwner lifecycleOwner){
        if (lightOperation==null){
            synchronized (LightOperation.class){
                if (lightOperation == null){
                    lightOperation = new LightOperation(lifecycleOwner);
                }
            }
        }
        return lightOperation;
    }



    public void blinkDevice(String address, final boolean isBlink){
        if (bluetoothGattManager.isConnected()){
            //判断gatt连接的设置是否当前入参的设备
            if (bluetoothGattManager.getConnectAddress()!=null && bluetoothGattManager.getConnectAddress().equals(address)){
                BlinkSingleRequest blinkSingleRequest = new BlinkSingleRequest();
                blinkSingleRequest.setBlink(isBlink?1:0);
                String s = new Gson().toJson(blinkSingleRequest);
                bluetoothGattManager.writeData(s,UUID.fromString(LIGHT_SERVICE_UUID), UUID.fromString(LIGHT_CHARACTERISTIC_WRITE_UUID));
            }
            else{
                RemoteBlinkSingleRequest remoteBlinkSingleRequest = new RemoteBlinkSingleRequest();
                remoteBlinkSingleRequest.setMsg(isBlink ? RemoteBlinkSingleRequest.BLINK_START : RemoteBlinkSingleRequest.BLINK_STOP);
                remoteBlinkSingleRequest.setMac(address.toLowerCase());
                String s = new Gson().toJson(remoteBlinkSingleRequest, RemoteBlinkSingleRequest.class);
                bluetoothGattManager.writeData(s,UUID.fromString(LIGHT_SERVICE_UUID), UUID.fromString(LIGHT_CHARACTERISTIC_WRITE_UUID));
            }

        }
        else {
            bluetoothGattManager.setOnBleStateListener(new OnBleStateListener() {
                @Override
                public void onGattConnected(BluetoothGatt gatt) {
                    bluetoothGattManager.changeMtu(150);
                }

                @Override
                public void onGattDisconnected() {

                }

                @Override
                public void onMtuChanged(boolean isSuccess, int mtu) {
                    if (isSuccess){
                        bluetoothGattManager.discoverServices();
                    }
                    else{
                        ;
                    }
                }

                @Override
                public void onServicesDiscovered(boolean isServicesDiscovered, BluetoothGatt gatt) {
                    bluetoothGattManager.setCharacteristicNotification(UUID.fromString(LIGHT_SERVICE_UUID), UUID.fromString(LIGHT_CHARACTERISTIC_NOTIFY_UUID),true);
                }

                @Override
                public void onCharacteristicChanged(boolean isWriteDataSuccess, BluetoothGattCharacteristic characteristic) {

                }

                @Override
                public void onCharacteristicNotificationChanged(boolean isSetNotifySuccess) {
                    if (isSetNotifySuccess){
                        BlinkSingleRequest blinkSingleRequest = new BlinkSingleRequest();
                        blinkSingleRequest.setBlink(isBlink?1:0);
                        String s = new Gson().toJson(blinkSingleRequest);
                        bluetoothGattManager.writeData(s,UUID.fromString(LIGHT_SERVICE_UUID), UUID.fromString(LIGHT_CHARACTERISTIC_WRITE_UUID));
                    }
                    else{
                        ;
                    }
                }
            });
            bluetoothGattManager.connect(address);
        }

    }
}
