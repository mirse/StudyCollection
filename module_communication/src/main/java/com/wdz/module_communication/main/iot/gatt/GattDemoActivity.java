package com.wdz.module_communication.main.iot.gatt;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;
import com.wdz.module_communication.main.iot.gatt.bean.BlinkSingleRequest;
import com.wdz.module_communication.main.iot.gatt.bean.MyBluetoothDevice;
import com.wdz.module_communication.main.iot.gatt.utils.scan.BluetoothScanManager;
import com.wdz.module_communication.main.iot.gatt.utils.BluetoothGattManager;
import com.wdz.module_communication.main.iot.gatt.listener.OnBleScanListener;
import com.wdz.module_communication.main.iot.gatt.listener.OnBleStateListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_CHARACTERISTIC_NOTIFY_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_CHARACTERISTIC_WRITE_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_SERVICE_UUID;
import static com.wdz.module_communication.main.iot.gatt.utils.scan.BluetoothScanManager.FILTER_MAC;

@Route(path = ARouterConstant.ACTIVITY_GATT)
public class GattDemoActivity extends PermissionActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.bt_scan)
    Button btScan;
    @BindView(R2.id.rv_device)
    RecyclerView rvDevice;
    // TODO: 2019-05-21 打开蓝牙
    private BluetoothAdapter mBluetoothAdapter;
    /**
     * 是否正在扫描
     */
    private boolean isStartScan = false;
    private BluetoothLeScanner bluetoothLeScanner;
    private List<MyBluetoothDevice> myBluetoothDeviceList = new ArrayList<>();
    private ScanDeviceAdapter scanDeviceAdapter;
    private BluetoothGatt bluetoothGatt;
    private BluetoothScanManager bluetoothScanManager;
    private BluetoothGattManager bluetoothGattManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatt_demo);
        ButterKnife.bind(this);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {

        }
        BluetoothManager mBluetoothManager;
        if ((mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE)) != null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
            bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
            if (!mBluetoothAdapter.isEnabled() || mBluetoothAdapter == null) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        } else {

        }
        //checkDeviceConnectStatus();
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            initMorePermission(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        } else {
            initMorePermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }
        bluetoothGattManager = new BluetoothGattManager.Builder(this)
                .setRetryCount(2)
                .setRetryTimeout(7*1000)
                .setOnBleStateListener(new OnBleStateListener() {
                    @Override
                    public void onGattConnected(BluetoothGatt gatt) {
                        Log.i(TAG, "onGattConnected: ");
                        Log.i(TAG, "onConnectionStateChange: "+Thread.currentThread());
                        //bluetoothGattManager.discoverServices();
                        bluetoothGattManager.changeMtu(150);
                    }

                    @Override
                    public void onGattDisconnected() {
                        Log.i(TAG, "onGattDisconnected: ");
                    }

                    @Override
                    public void onMtuChanged(boolean isSuccess,int mtu) {
                        Log.i(TAG, "onMtuChanged: "+isSuccess+" mtu:"+mtu);
                        //bluetoothGattManager.discoverServices();
                    }

                    @Override
                    public void onServicesDiscovered(boolean isServicesDiscovered,BluetoothGatt gatt) {
                        Log.i(TAG, "onServicesDiscovered: "+isServicesDiscovered);
//                        for (BluetoothGattService bluetoothGattService:gatt.getServices()) {
//                            Log.i(TAG, "onServicesDiscovered: "+bluetoothGattService.getUuid());
//                        }
//                        bluetoothGattManager.setCharacteristicNotification(UUID.fromString(LIGHT_SERVICE_UUID), UUID.fromString(LIGHT_CHARACTERISTIC_NOTIFY_UUID),true);
                    }

                    @Override
                    public void onCharacteristicChanged(boolean isWriteDataSuccess, BluetoothGattCharacteristic characteristic) {
                        Log.i(TAG, "onCharacteristicChanged: isWriteDataSuccess:"+isWriteDataSuccess);
                    }

                    @Override
                    public void onCharacteristicNotificationChanged(boolean isSetNotifySuccess) {
                        Log.i(TAG, "onCharacteristicNotificationChanged: "+isSetNotifySuccess);
//                        BlinkSingleRequest blinkSingleRequest = new BlinkSingleRequest();
//                        blinkSingleRequest.setBlink(1);
//                        String s = new Gson().toJson(blinkSingleRequest);
//                        bluetoothGattManager.writeData(s,UUID.fromString(LIGHT_SERVICE_UUID), UUID.fromString(LIGHT_CHARACTERISTIC_WRITE_UUID));

                    }


                })
                .build();

        bluetoothGattManager.getConnectStatusLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.i(TAG, "onChanged: "+aBoolean);
            }
        });


        bluetoothScanManager = new BluetoothScanManager.Builder(this)
                .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                .setScanFilterType(FILTER_MAC)
//                .setScanFilter("EC:1B:BD:78:EA:26") //学校项目
                .setScanFilter("E8:D0:3C:54:5C:A8")
                .setScanTimeOut(10*1000)
                .setOnBluetoothScanListener(new OnBleScanListener() {
            @Override
            public void onStartScan() {

            }

            @Override
            public void onStopScan() {
                Log.i(TAG, "onStopScan: ");
            }

            @Override
            public void onScanTimeOut() {
                Log.i(TAG, "onScanTimeOut: ");
            }

            @Override
            public void onScanResult(ScanResult result) {

                List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
                for (int i = 0; i < myBluetoothDeviceList.size(); i++) {
                    bluetoothDeviceList.add(myBluetoothDeviceList.get(i).bluetoothDevice);
                }
                if (!bluetoothDeviceList.contains(result.getDevice())){
                    MyBluetoothDevice myBluetoothDevice = new MyBluetoothDevice();
                    myBluetoothDevice.setBluetoothDevice(result.getDevice());
                    myBluetoothDevice.setRssi(result.getRssi());
                    myBluetoothDeviceList.add(myBluetoothDevice);
//                myBluetoothDeviceList.sort(new Comparator<MyBluetoothDevice>() {
//                    @Override
//                    public int compare(MyBluetoothDevice o1, MyBluetoothDevice o2) {
//                        Integer rssi = o1.getRssi();
//                        Integer rssi1 = o2.getRssi();
//                        return rssi.compareTo(rssi1);
//                    }
//                });
                    scanDeviceAdapter.notifyDataSetChanged();
                }


                Log.i(TAG, "onScanResult: ");
            }

            @Override
            public void onScanFail() {

            }
        }).build();


    }

    /**
     * 判断设备的连接状态
     */
    private void checkDeviceConnectStatus() {
        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device:bondedDevices) {
            Log.i(TAG, "onCreate: "+device.toString());
            Method isConnectedMethod = null;
            try {
                isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
                boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
                Log.i(TAG, "onCreate: "+isConnected);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        scanDeviceAdapter = new ScanDeviceAdapter(this, myBluetoothDeviceList);
        rvDevice.setLayoutManager(linearLayoutManager);
        rvDevice.setAdapter(scanDeviceAdapter);
        scanDeviceAdapter.setOnClickListener(new ScanDeviceAdapter.OnClickListener() {
            @Override
            public void onClickDisConnect(@NotNull MyBluetoothDevice myBluetoothDevice) {
//                bluetoothGatt.disconnect();
                bluetoothGattManager.disconnect();
            }

            @Override
            public void onClickConnect(@NotNull MyBluetoothDevice myBluetoothDevice) {
//                //autoConnect:指示是否在可用时自动连接到 BLE 设备
//                if (bluetoothGatt!=null){
//                    bluetoothGatt.close();
//                }
//                //> 6.0
//                bluetoothGatt = myBluetoothDevice.bluetoothDevice.connectGatt(GattDemoActivity.this, false, bluetoothGattCallback,BluetoothDevice.TRANSPORT_LE);

                bluetoothGattManager.connect(myBluetoothDevice.bluetoothDevice.getAddress());

            }
        });
    }
    BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i(TAG, "onConnectionStateChange: status:"+status+" newState:"+newState);
            if (status == GATT_SUCCESS){
                if (newState == BluetoothProfile.STATE_CONNECTED) {

                }
                else if (newState == BluetoothProfile.STATE_DISCONNECTED){
                    refreshGatt(gatt);
                    gatt.close();
                }
            }
            else{
                refreshGatt(gatt);
                gatt.close();
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }


    };

    private void refreshGatt(BluetoothGatt gatt) {
        try {
            Method localMethod = gatt.getClass().getMethod("refresh", new Class[0]);
            boolean bool = ((Boolean) localMethod.invoke(gatt, new Object[0])).booleanValue();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }


    @OnClick(R2.id.bt_scan)
    public void onClick(View view) {
        if (R.id.bt_scan == view.getId()) {
            if (isStartScan){
                isStartScan = false;
                //stopScan();
                //stopScanNew();
                bluetoothScanManager.stopScan();
                btScan.setText("scan");
            }
            else{
                isStartScan = true;
                //scanDevice();
                //scanDeviceNew();
                bluetoothScanManager.startScan();
                btScan.setText("stop");
            }

        }
    }

    private void scanDevice() {
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }
    private void stopScan() {
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "onLeScan: " + device);
                        }
                    });
                }
            };

//  -------------------------------------------------------
    private void scanDeviceNew() {
        bluetoothLeScanner.startScan(callback);

    }
    private void stopScanNew() {
        bluetoothLeScanner.stopScan(callback);

    }

    private ScanCallback callback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();
            for (int i = 0; i < myBluetoothDeviceList.size(); i++) {
                bluetoothDeviceList.add(myBluetoothDeviceList.get(i).bluetoothDevice);
            }
            if (!bluetoothDeviceList.contains(result.getDevice())){
                MyBluetoothDevice myBluetoothDevice = new MyBluetoothDevice();
                myBluetoothDevice.setBluetoothDevice(result.getDevice());
                myBluetoothDevice.setRssi(result.getRssi());
                myBluetoothDeviceList.add(myBluetoothDevice);
//                myBluetoothDeviceList.sort(new Comparator<MyBluetoothDevice>() {
//                    @Override
//                    public int compare(MyBluetoothDevice o1, MyBluetoothDevice o2) {
//                        Integer rssi = o1.getRssi();
//                        Integer rssi1 = o2.getRssi();
//                        return rssi.compareTo(rssi1);
//                    }
//                });
                scanDeviceAdapter.notifyDataSetChanged();
            }


            Log.i(TAG, "onScanResult: ");
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        //mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    @Override
    protected void alreadyGetPermission() {

    }

    @Override
    protected void onGetPermission() {

    }

    @Override
    protected void onDenyPermission() {

    }
}
