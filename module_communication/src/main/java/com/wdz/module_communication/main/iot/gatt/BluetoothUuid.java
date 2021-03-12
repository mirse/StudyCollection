package com.wdz.module_communication.main.iot.gatt;

import java.util.UUID;

/**
 * @Author dezhi.wang
 * @Date 2021/3/4 15:33
 */
public interface BluetoothUuid {
    /**
     * Notify的Descriptor的UUID
     */
    String NOTIFY_DESCRIPTOR = "00002902-0000-1000-8000-00805f9b34fb";


    //学校项目相关
    /**
     *
     */
    String LIGHT_SERVICE_UUID = "bc8f101e-558c-459f-a413-1fa8950198f8";
    /**
     * 写特征UUID
     */
    String LIGHT_CHARACTERISTIC_WRITE_UUID = "3db871af-10bb-40ea-a82d-8c3b1b36e44b";
    /**
     * 通知特征UUID
     */
    String LIGHT_CHARACTERISTIC_NOTIFY_UUID = "633bffba-59b4-4feb-bb4f-f9991217a72f";

    String GATEWAY_SERVICE_UUID = "a9ac5307-38cb-4899-8db1-c56616f3860a";
    String GATEWAY_CHARACTERISTIC_WRITE_WIFI_INFO_UUID = "eaa4d350-6fd7-4168-a3fa-dc70f1d0f6e1";
    String GATEWAY_CHARACTERISTIC_NOTIFY_UUID = "d3575cba-538c-4442-bc07-98eeef496bff";
}
