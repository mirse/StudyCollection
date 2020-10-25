package com.wdz.module_communication.main.info.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class WifiUtils {
    public static WifiInfo getWifiInfo(Context context) {

        WifiInfo wifiInfo = null;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            wifiInfo = wifiManager.getConnectionInfo();
        }
        return wifiInfo;
    }


    /**
     * Android Q（10）已弃用
     * @param context
     * @param ssid  带引号的ssid "\"wifi_name\""
     * @param password  wifi 密码
     * @param fuzzyMatch    是否模糊匹配ssid
     */
    public static void changeWifi(Context context, String ssid, String password, boolean fuzzyMatch) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration wifiConfiguration : list) {
            String wifiSSID = wifiConfiguration.SSID;
            boolean ssidMatch = fuzzyMatch ? wifiSSID.startsWith("\""+ssid+"\"") : wifiSSID.equals("\""+ssid+"\"");
            if (ssidMatch) {
                if (!TextUtils.isEmpty(password)) {
                    wifiConfiguration.preSharedKey = "\"" + password + "\"";
                }
                wifiManager.disconnect();
                wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
    }


    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context 上下文
     * @return true 表示开启
     */
    public static boolean isGpsOpen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }
}
