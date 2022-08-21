package com.wdz.module_communication.main.iot.gatt.listener;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.le.ScanResult;

/**
 * profile <- service(服务)电量信息服务 <- characteristic(特征值) 获取写入想要的东西      <- descriptor(描述符) 对characteristic进行描述
 *         <- service                                                                 <- descriptor
 *                                                                                    <- value
 *
 * setCharacteristicNotification //设备上的特定特征发生变化时收到通知
 * writeDescriptor //写入描述符  向Characteristic的Descriptor属性写入通知开关，使蓝牙设备主动向手机发送数据
 *
 *
 *
 * 蓝牙连接监听类
 * @Author dezhi.wang
 * @Date 2021/2/25 11:31
 */
public interface OnBleStateListener {
    /**
     * gatt连接成功
     * @param gatt
     */
    void onGattConnected(BluetoothGatt gatt);

    /**
     * gatt连接失败
     */
    void onGattDisconnected();

    /**
     * mtu更改
     * @param isSuccess
     * @param mtu
     */
    void onMtuChanged(boolean isSuccess,int mtu);

    /**
     * 查找服务结果回调
     * @param isServicesDiscovered 是否已开启远程服务发现操作
     * @param gatt
     */
    void onServicesDiscovered(boolean isServicesDiscovered,BluetoothGatt gatt);

    /**
     * 写数据回调
     * @param isWriteDataSuccess
     * @param characteristic
     */
    void onCharacteristicChanged(boolean isWriteDataSuccess,BluetoothGattCharacteristic characteristic);

    /**
     * 设置特征值通知回调
     * @param isSetNotifySuccess
     */
    void onCharacteristicNotificationChanged(boolean isSetNotifySuccess);

    /**
     * 读特征值回调
     */
    void onCharacteristicRead(boolean isReadSuccess,BluetoothGattCharacteristic characteristic);

}
