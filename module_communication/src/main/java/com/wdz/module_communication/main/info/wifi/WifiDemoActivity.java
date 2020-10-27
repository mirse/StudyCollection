package com.wdz.module_communication.main.info.wifi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.common.util.Log;
import com.wdz.module_basis.widget.recyclerview.universal.base.BaseRecyclerViewAdapter;
import com.wdz.module_basis.widget.recyclerview.universal.base.SingleTypeAdapter;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_WIFI)
public class WifiDemoActivity extends PermissionActivity {
    private static final String TAG = "WifiDemoActivity";
    private static final int REQUEST_GPS = 1;
    private WifiManager wifiManager;
    @BindView(R2.id.rv_wifi)
    RecyclerView rvWifi;
    private List<ScanResult> mList = new ArrayList<>();
    HashMap<String, Integer> wifiMap = new HashMap<>();
    private WifiAdapter wifiAdapter;

    /**
     * 获取wifi信息
     */
    private final int ACTION_GET_WIFI_INFO = 0;
    /**
     * 扫描wifi
     */
    private final int ACTION_SCAN_WIFI = 1;
    /**
     * 获取扫描wifi结果
     */
    private final int ACTION_GET_SCAN_WIFI = 2;
    private int action = ACTION_GET_WIFI_INFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_demo);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        wifiAdapter = new WifiAdapter(this,mList);
        rvWifi.setLayoutManager(linearLayoutManager);
        rvWifi.setAdapter(wifiAdapter);

        wifiAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClickNormal(Object object, int position) {
                ScanResult scanResult = (ScanResult) object;
                WifiUtils.changeWifi(WifiDemoActivity.this,scanResult.SSID,"7418529631",false);
            }
        });
    }

    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(
                    WifiManager.EXTRA_RESULTS_UPDATED, false);
            //扫描成功或失败的状态
            if (success) {
                scanSuccess();
            } else {
                // scan failure handling
                scanFailure();
            }
        }
    };

    /*
    *   节流
        使用 WifiManager.startScan() 扫描的频率适用以下限制。
        Android 8.0 和 Android 8.1：
        每个后台应用可以在 30 分钟内扫描一次。
        Android 9：
        每个前台应用可以在 2 分钟内扫描四次。这样便可在短时间内进行多次扫描。
        所有后台应用总共可以在 30 分钟内扫描一次。
        Android 10 及更高版本：
        适用 Android 9 的节流限制。新增一个开发者选项，用户可以关闭节流功能以便进行本地测试（位于开发者选项 > 网络 > WLAN 扫描调节下）。
    *
    * */


    @OnClick({R2.id.bt_wifi_info,R2.id.bt_wifi_list})
    public void onClick(View view){
        if (view.getId() == R.id.bt_wifi_info){
            action = ACTION_GET_WIFI_INFO;
            checkWifiPermission();
        }
        else if (view.getId() == R.id.bt_wifi_list){
            action = ACTION_SCAN_WIFI;
            wifiManager = (WifiManager)
                    getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            registerReceiver(wifiScanReceiver, intentFilter);

            checkScanPermission();



        }
    }

    private void scanSuccess(){
        action = ACTION_GET_SCAN_WIFI;
        initMorePermission(Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    private void scanFailure() {

        List<ScanResult> results = wifiManager.getScanResults();
        Log.i(TAG,"scanFailure:"+results.toString());
    }


    /**
     * 需要真机验证下 从哪个版本开始需要开启gps
     * Android8.0及以下  ACCESS_WIFI_STATE √
     * Android 9.0 需要权限1、ACCESS_FINE_LOCATION 或 ACCESS_COARSE_LOCATION 2、ACCESS_WIFI_STATE  2、需要启动位置服务
     * Android 10 新增ACCESS_BACKGROUND_LOCATION权限
     */
    private void checkWifiPermission() {
        //大于Android10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            initMorePermission(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            initMorePermission(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
        }
        else{
            Log.i(TAG,WifiUtils.getWifiInfo(this).toString());
            //initMorePermission(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }


    /**
     * Android 8,8.1: ACCESS_FINE_LOCATION 或 ACCESS_COARSE_LOCATION 或 CHANGE_WIFI_STATE
     * Android 9: 1、ACCESS_FINE_LOCATION 或 ACCESS_COARSE_LOCATION 2、 CHANGE_WIFI_STATE 3、需要启动位置服务
     * Android 10:1、目标平台>=29 ACCESS_FINE_LOCATION <29:ACCESS_FINE_LOCATION 或 ACCESS_COARSE_LOCATION
     *            2、目标平台>=29 CHANGE_WIFI_STATE  <29: ACCESS_WIFI_STATE
     *            3、需要启动位置服务
     */
    public void checkScanPermission(){
        //大于Android9
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            initMorePermission(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
        }
        else{
            boolean success = wifiManager.startScan();
            //可能失败
            if (!success) {
                scanFailure();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GPS){
            if (action == ACTION_GET_WIFI_INFO){
                checkWifiPermission();
            }
            else{
                checkScanPermission();
            }

        }

    }

    @Override
    protected void alreadyGetPermission() {
        if (action == ACTION_GET_WIFI_INFO){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                //gps已开启
                if (WifiUtils.isGpsOpen(this)){
                    Log.i(TAG,WifiUtils.getWifiInfo(this).toString());
                }
                else{
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, REQUEST_GPS);
                }
            }
            else{
                Log.i(TAG,WifiUtils.getWifiInfo(this).toString());
            }
        }
        else if (action == ACTION_SCAN_WIFI){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                if (WifiUtils.isGpsOpen(this)){
                    boolean success = wifiManager.startScan();
                    //可能失败
                    if (!success) {
                        scanFailure();
                    }
                }
                else{
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, REQUEST_GPS);
                }
            }
            else{
                boolean success = wifiManager.startScan();
                //可能失败
                if (!success) {
                    scanFailure();
                }
            }

        }
        else if (action == ACTION_GET_SCAN_WIFI){
            List<ScanResult> results = wifiManager.getScanResults();
            for (int i = 0; i < results.size(); i++) {
                Log.i(TAG,"scanSuccess:"+results.get(i).SSID);
                if (!results.get(i).SSID.isEmpty()){
                    String key = results.get(i).SSID +""+results.get(i).capabilities;
                    if (!wifiMap.containsKey(key)){
                        wifiMap.put(key,i);
                        mList.add(results.get(i));
                    }
                }
            }
            wifiAdapter.notifyDataSetChanged();
        }


    }

    @Override
    protected void onGetPermission() {
        if (action == ACTION_GET_WIFI_INFO){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                //gps已开启
                if (WifiUtils.isGpsOpen(this)){
                    Log.i(TAG,WifiUtils.getWifiInfo(this).toString());
                }
                else{
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, REQUEST_GPS);
                }
            }
            else{
                Log.i(TAG,WifiUtils.getWifiInfo(this).toString());
            }
        }
        else if (action == ACTION_SCAN_WIFI){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                if (WifiUtils.isGpsOpen(this)){
                    boolean success = wifiManager.startScan();
                    //可能失败
                    if (!success) {
                        scanFailure();
                    }
                }
                else{
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, REQUEST_GPS);
                }
            }
            else {
                boolean success = wifiManager.startScan();
                //可能失败
                if (!success) {
                    scanFailure();
                }
            }
        }
        else if (action == ACTION_GET_SCAN_WIFI){
            List<ScanResult> results = wifiManager.getScanResults();
            for (int i = 0; i < results.size(); i++) {
                Log.i(TAG,"scanSuccess:"+results.get(i).SSID);
                if (!results.get(i).SSID.isEmpty()){
                    String key = results.get(i).SSID +""+results.get(i).capabilities;
                    if (!wifiMap.containsKey(key)){
                        wifiMap.put(key,i);
                        mList.add(results.get(i));
                    }
                }
            }
            wifiAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDenyPermission() {

    }



    public class WifiAdapter extends SingleTypeAdapter<ScanResult> {


        public WifiAdapter(Context mContext, List list) {
            super(mContext, list);
        }

        @Override
        public int getLayoutId() {
            return R.layout.recycler_item_wifi;
        }

        @Override
        public int getHeadLayoutId() {
            return 0;
        }

        @Override
        public int getEmptyLayoutId() {
            return 0;
        }
        @Override
        public void bindData(BaseViewHolder holder, ScanResult data, int position) {
            TextView wifiName = (TextView) holder.getView(R.id.wifi_name);
            wifiName.setText(data.SSID);
        }
    }
}
