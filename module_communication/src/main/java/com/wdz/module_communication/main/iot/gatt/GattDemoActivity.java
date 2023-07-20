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
import com.wdz.common.util.ByteUtil;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;
import com.wdz.module_communication.main.iot.gatt.bean.BlinkSingleRequest;
import com.wdz.module_communication.main.iot.gatt.bean.MyBluetoothDevice;
import com.wdz.module_communication.main.iot.gatt.utils.operate.LightOperation;
import com.wdz.module_communication.main.iot.gatt.utils.scan.BluetoothScanManager;
import com.wdz.module_communication.main.iot.gatt.utils.BluetoothGattManager;
import com.wdz.module_communication.main.iot.gatt.listener.OnBleScanListener;
import com.wdz.module_communication.main.iot.gatt.listener.OnBleStateListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.GATEWAY_CHARACTERISTIC_NOTIFY_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.GATEWAY_CHARACTERISTIC_WRITE_WIFI_INFO_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.GATEWAY_SERVICE_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_CHARACTERISTIC_NOTIFY_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_CHARACTERISTIC_WRITE_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.LIGHT_SERVICE_UUID;
import static com.wdz.module_communication.main.iot.gatt.BluetoothUuid.NOTIFY_DESCRIPTOR;
import static com.wdz.module_communication.main.iot.gatt.utils.scan.BluetoothScanManager.FILTER_MAC;

@Route(path = ARouterConstant.ACTIVITY_GATT)
public class GattDemoActivity extends PermissionActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.bt_scan)
    Button btScan;
    @BindView(R2.id.rv_device)
    RecyclerView rvDevice;
    @BindView(R2.id.bt_stop)
    Button btStop;
    @BindView(R2.id.bt_continue)
    Button btContinue;
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
    int count = 0;
    long startTime = 0;
    boolean isSendBleData = false;

    private String L7ServiceUuid = "0000A002-0000-1000-8000-00805F9B34FB";
    private String L7NotifyUuid = "0000C305-0000-1000-8000-00805F9B34FB";
    private String L7WriteUuid = "0000C304-0000-1000-8000-00805F9B34FB";
    //L7:SERVICE: 0000A002-0000-1000-8000-00805F9B34FB  NOTIFY:0000C305-0000-1000-8000-00805F9B34FB
    //WRITE:0000C302-0000-1000-8000-00805F9B34FB

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
        if (Build.VERSION.SDK_INT >= 30) {
            //android 12及以上动态申请Manifest.permission.BLUETOOTH,Manifest.permission.BLUETOOTH_ADMIN，== BLUETOOTH.SCAN
            initMorePermission(Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);

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
                        bluetoothGattManager.changeMtu(258);
                    }

                    @Override
                    public void onGattDisconnected() {
                        Log.i(TAG, "onGattDisconnected: ");
                    }

                    @Override
                    public void onMtuChanged(boolean isSuccess,int mtu) {
                        Log.i(TAG, "onMtuChanged: "+isSuccess+" mtu:"+mtu);
                        bluetoothGattManager.discoverServices();
                    }

                    @Override
                    public void onServicesDiscovered(boolean isServicesDiscovered,BluetoothGatt gatt) {
                        Log.i(TAG, "onServicesDiscovered: "+isServicesDiscovered);
//                        for (BluetoothGattService bluetoothGattService:gatt.getServices()) {
//                            Log.i(TAG, "onServicesDiscovered: "+bluetoothGattService.getUuid());
//                        }
                        //bluetoothGattManager.setCharacteristicNotification(UUID.fromString(GATEWAY_SERVICE_UUID), UUID.fromString(GATEWAY_CHARACTERISTIC_NOTIFY_UUID),true);
                        //bluetoothGattManager.writeData(new byte[]{(byte) 0x9D, (byte) 0xB1, (byte) 0xE9,0x17},UUID.fromString("20890000-62E8-4795-9377-B44229C80329"), UUID.fromString("20890002-62E8-4795-9377-B44229C80329"));
                        //bluetoothGattManager.setCharacteristicNotification(UUID.fromString("00001000-0000-1000-8000-00805F9B34FB"), UUID.fromString("00001002-0000-1000-8000-00805F9B34FB"),true);

                        bluetoothGattManager.setCharacteristicNotification(UUID.fromString(L7ServiceUuid), UUID.fromString(L7NotifyUuid),true);
                    }

                    @Override
                    public void onCharacteristicChanged(boolean isWriteDataSuccess, BluetoothGattCharacteristic characteristic) {
                        Log.i(TAG, "onCharacteristicChanged: isWriteDataSuccess:"+isWriteDataSuccess);
//                        if (isSendBleData){
//                            count++;
//                            Log.i(TAG, "onCharacteristicChanged: count:"+count);
//                            if (count>=Integer.MAX_VALUE){
//                                long currentTimeMillis = System.currentTimeMillis();
//                                long l = (currentTimeMillis - startTime) / 1000;
//                                float cent = (float) count*200/1024/l;
//                                Log.i(TAG, "速率："+cent+" 大小："+count*200/1024/1024L);
//                                return;
//                            }
//                            try {
//                                Thread.sleep(5);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            Random random = new Random();
//                            StringBuilder stringBuilder = new StringBuilder();
//                            StringBuilder stringBuilderHex = new StringBuilder();
//                            byte[] datas = new byte[200];
//                            String sTemp;
//                            for (int j = 0; j < datas.length; j++) {
//                                if (j == 0){
//                                    datas[j] = (byte) 0xFF;
//                                }
//                                else if (j == (datas.length -1)){
//                                    datas[j] = (byte) 0xFE;
//                                }
//                                else{
//                                    datas[j] = (byte) random.nextInt(15);
//                                }
//                                stringBuilder.append((char)(datas[j]));
//                                sTemp = Integer.toHexString(0xFF & datas[j]);
//                                if (sTemp.length() < 2) {
//                                    stringBuilderHex.append(0);
//                                }
//                                stringBuilderHex.append(sTemp.toUpperCase());
//                            }
//                            Log.i(TAG, "onCharacteristicChanged: "+stringBuilderHex.toString());
//                            Log.i(TAG, "onCharacteristicChanged-------: "+stringBuilder.toString());
//
//                            //F9100000000100710F
//                            //bluetoothGattManager.writeData(datas,UUID.fromString("00001000-0000-1000-8000-00805F9B34FB"), UUID.fromString("00001001-0000-1000-8000-00805F9B34FB"));
//                            bluetoothGattManager.writeData(datas,UUID.fromString(L7ServiceUuid), UUID.fromString(L7WriteUuid));
//                            //bluetoothGattManager.readData(UUID.fromString("20890000-62E8-4795-9377-B44229C80329"), UUID.fromString("20890002-62E8-4795-9377-B44229C80329"));
//                        }
                        byte[] bytes = new byte[]{(byte) 0xF9,0x10,0x00,0x00,0x00,0x01,0x00,0x71,0x0F};

                        bluetoothGattManager.writeData(bytes,UUID.fromString(L7ServiceUuid), UUID.fromString(L7WriteUuid));
                    }

                    @Override
                    public void onCharacteristicNotificationChanged(boolean isSetNotifySuccess) {
                        Log.i(TAG, "onCharacteristicNotificationChanged: "+isSetNotifySuccess);
//                        BlinkSingleRequest blinkSingleRequest = new BlinkSingleRequest();
//                        blinkSingleRequest.setBlink(1);
//                        String s = new Gson().toJson(blinkSingleRequest);
                        //bluetoothGattManager.writeData("SSID:" + "mSSID",UUID.fromString(GATEWAY_SERVICE_UUID), UUID.fromString(GATEWAY_CHARACTERISTIC_WRITE_WIFI_INFO_UUID));


//                        count = 0;
//                        startTime = System.currentTimeMillis();
//                        Random random = new Random();
//
//                        byte[] datas = new byte[20];
//                        for (int j = 0; j < datas.length; j++) {
//                            if (j == 0){
//                                datas[j] = (byte) 0xFF;
//                            }
//                            else if (j == (datas.length -1)){
//                                datas[j] = (byte) 0xFE;
//                            }
//                            else{
//                                datas[j] = (byte) random.nextInt(10);
//                            }
//
//                        }
//
//
//                        bluetoothGattManager.writeData(datas,UUID.fromString("00001000-0000-1000-8000-00805F9B34FB"), UUID.fromString("00001001-0000-1000-8000-00805F9B34FB"));



                        //c498be0a
                        //bluetoothGattManager.readData(UUID.fromString("20890000-62E8-4795-9377-B44229C80329"), UUID.fromString("20890002-62E8-4795-9377-B44229C80329"));
                    }

                    @Override
                    public void onCharacteristicRead(boolean isReadSuccess, BluetoothGattCharacteristic characteristic) {
                        byte[] value = characteristic.getValue();
                        Log.i(TAG, "onCharacteristicRead isReadSuccess:"+isReadSuccess+" 数据："+ ByteUtil.byte2HexString(value));
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
//                .setScanFilterType(FILTER_MAC)
//                .setScanFilter("EC:1B:BD:78:EA:26") //学校项目
//                .setScanFilter("E8:D0:3C:54:5C:A8")
//                .setScanFilter("68:0A:E2:42:F6:15")
//                .setScanFilter("C9:84:04:46:D6:1D")
//                .setScanFilter("E0:7D:EA:6E:51:89")
//                .setScanFilter("80:6F:B0:0F:BA:F9")
//                .setScanFilter("7C:DF:A1:B3:80:B6")
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

                    Log.i(TAG, "onScanResult: "+myBluetoothDevice.getRssi()+" addr:"+myBluetoothDevice.bluetoothDevice.getAddress()+" name:"+result.getDevice().getName());
                    myBluetoothDeviceList.add(myBluetoothDevice);
                    myBluetoothDeviceList.sort(new Comparator<MyBluetoothDevice>() {
                        @Override
                        public int compare(MyBluetoothDevice o1, MyBluetoothDevice o2) {
                            Integer rssi = o1.getRssi();
                            Integer rssi1 = o2.getRssi();
                            return rssi.compareTo(rssi1);
                        }
                    });
                    scanDeviceAdapter.notifyDataSetChanged();
                }


                Log.i(TAG, "onScanResult: ");
            }

            @Override
            public void onScanFail() {

            }
        }).build();


    }



    public static byte[] intToBytes( int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    public static byte[] intToBytes2(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
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
                //bluetoothGatt = myBluetoothDevice.bluetoothDevice.connectGatt(GattDemoActivity.this, false, bluetoothGattCallback,BluetoothDevice.TRANSPORT_LE);

                bluetoothGattManager.connect(myBluetoothDevice.bluetoothDevice.getAddress());
                //LightOperation.getInstance(GattDemoActivity.this).blinkDevice(myBluetoothDevice.bluetoothDevice.getAddress(),true);

            }
        });
    }
    BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i(TAG, "onConnectionStateChange: status:"+status+" newState:"+newState);
            if (status == GATT_SUCCESS){
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    gatt.requestMtu(258);
                    Log.i(TAG, "requestMtu ");
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
            Log.i(TAG, "onServicesDiscovered ");
            BluetoothGattService bluetoothGattService = gatt.getService(UUID.fromString("00001000-0000-1000-8000-00805F9B34FB"));
            if (bluetoothGattService != null) {
                BluetoothGattCharacteristic characteristic = bluetoothGattService.getCharacteristic(UUID.fromString("00001002-0000-1000-8000-00805F9B34FB"));
                if (characteristic != null) {
                    boolean b = gatt.setCharacteristicNotification(characteristic, true);
                    if (b) {
                        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(NOTIFY_DESCRIPTOR));
                        descriptor.setValue(true ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                        gatt.writeDescriptor(descriptor);
                    }
                }
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);

            count++;
            Log.i(TAG, "onCharacteristicChanged: count:"+count);
            if (count>500){
                long currentTimeMillis = System.currentTimeMillis();
                long l = (currentTimeMillis - startTime) / 1000;
                float cent = (float) 500*100/1024/l;
                Log.i(TAG, "速率："+cent);
                return;
            }
//            try {
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Random random = new Random();
            byte[] datas = new byte[100];
            for (int j = 0; j < datas.length; j++) {
                if (j == 0){
                    datas[j] = (byte) 0xFF;
                }
                else if (j == (datas.length -1)){
                    datas[j] = (byte) 0xFE;
                }
                else{
                    datas[j] = (byte) random.nextInt(10);
                }

            }
            BluetoothGattService bluetoothGattService = bluetoothGatt.getService(UUID.fromString("00001000-0000-1000-8000-00805F9B34FB"));
            if (bluetoothGattService != null) {
                BluetoothGattCharacteristic characteristic1 = bluetoothGattService.getCharacteristic(UUID.fromString("00001001-0000-1000-8000-00805F9B34FB"));
                if (characteristic1 != null) {
                    characteristic1.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                    characteristic1.setValue(datas);
                    boolean status1 = false;
                    while (!status1) {
                        status1 = bluetoothGatt.writeCharacteristic(characteristic);
                        Log.i(TAG, "writeCharacteristic: " + status1);
                    }

                }
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.i(TAG, "onCharacteristicChanged: ");




        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);

            count = 0;
            startTime = System.currentTimeMillis();
            Random random = new Random();

            byte[] datas = new byte[200];
            for (int j = 0; j < datas.length; j++) {
                if (j == 0){
                    datas[j] = (byte) 0xFF;
                }
                else if (j == (datas.length -1)){
                    datas[j] = (byte) 0xFE;
                }
                else{
                    datas[j] = (byte) random.nextInt(10);
                }

            }
            BluetoothGattService bluetoothGattService = bluetoothGatt.getService(UUID.fromString("00001000-0000-1000-8000-00805F9B34FB"));
            if (bluetoothGattService != null) {
                BluetoothGattCharacteristic characteristic1 = bluetoothGattService.getCharacteristic(UUID.fromString("00001001-0000-1000-8000-00805F9B34FB"));
                if (characteristic1 != null) {
                    characteristic1.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                    characteristic1.setValue(datas);
                    boolean status1 = false;
                    while (!status1) {
                        status1 = bluetoothGatt.writeCharacteristic(characteristic1);
                        Log.i(TAG, "writeCharacteristic: " + status1);
                    }

                }
            }

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
            Log.i(TAG, "onMtuChanged ");
            gatt.discoverServices();
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


    @OnClick({R2.id.bt_scan,R2.id.bt_stop,R2.id.bt_continue})
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
        else if (R.id.bt_stop == view.getId()){
            isSendBleData = false;
        }
        else if (R.id.bt_continue == view.getId()){
            isSendBleData = true;
            if (bluetoothGattManager.isConnected()){
                count = 0;
                startTime = System.currentTimeMillis();
                Random random = new Random();

                byte[] datas = new byte[20];
                for (int j = 0; j < datas.length; j++) {
                    if (j == 0){
                        datas[j] = (byte) 0xFF;
                    }
                    else if (j == (datas.length -1)){
                        datas[j] = (byte) 0xFE;
                    }
                    else{
                        datas[j] = (byte) random.nextInt(10);
                    }

                }
                bluetoothGattManager.writeData(datas,UUID.fromString("00001000-0000-1000-8000-00805F9B34FB"), UUID.fromString("00001001-0000-1000-8000-00805F9B34FB"));
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
