package com.wdz.module_communication.main.iot.gatt.bean

import android.bluetooth.BluetoothDevice

/**

 * @Author dezhi.wang

 * @Date 2020/11/20 16:31

 */
class MyBluetoothDevice {
    lateinit var bluetoothDevice:BluetoothDevice
    var rssi:Int = 0;
}