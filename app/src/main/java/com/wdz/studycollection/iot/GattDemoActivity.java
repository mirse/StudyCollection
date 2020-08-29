package com.wdz.studycollection.iot;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wdz.studycollection.R;
import com.wdz.studycollection.base.PermissionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GattDemoActivity extends PermissionActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R.id.bt_scan)
    Button btScan;
    // TODO: 2019-05-21 打开蓝牙
    private BluetoothAdapter mBluetoothAdapter;
    /**
     * 是否正在扫描
     */
    private boolean isStartScan = false;
    private BluetoothLeScanner bluetoothLeScanner;

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
//            finish();
        }
        initMorePermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    @OnClick(R.id.bt_scan)
    public void onClick(View view) {
        if (R.id.bt_scan == view.getId()) {
            if (isStartScan){
                isStartScan = false;
                //stopScan();
                stopScanNew();
                btScan.setText("scan");
            }
            else{
                isStartScan = true;
                //scanDevice();
                scanDeviceNew();
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
            Log.i(TAG, "onScanResult: "+result);
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
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
