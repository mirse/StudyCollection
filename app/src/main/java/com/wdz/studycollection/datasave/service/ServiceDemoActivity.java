package com.wdz.studycollection.datasave.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.wdz.studycollection.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceDemoActivity extends AppCompatActivity {


    private Intent myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);
        ButterKnife.bind(this);
        myService = new Intent(this, MyService.class);

    }

    @OnClick({R.id.bt_start_service,R.id.bt_stop_service,R.id.bt_bind_service1,R.id.bt_bind_service2,R.id.bt_bind_service3,R.id.bt_unbind_service})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_start_service:
                startService(myService);
                break;
            case R.id.bt_stop_service:
                stopService(myService);
                break;
            case R.id.bt_bind_service1:
                bindService(myService,mServiceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.bt_bind_service2:
                bindService(myService,mServiceConnection,BIND_DEBUG_UNBIND);
                break;
            case R.id.bt_bind_service3:
                bindService(myService,mServiceConnection,BIND_NOT_FOREGROUND);
                break;
            case R.id.bt_unbind_service:
                unbindService(mServiceConnection);
                break;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.LocalBinder myBinder = (MyService.LocalBinder)iBinder;
            MyService service = myBinder.getService();
            service.getString();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
