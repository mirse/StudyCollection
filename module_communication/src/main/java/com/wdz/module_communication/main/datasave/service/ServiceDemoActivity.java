package com.wdz.module_communication.main.datasave.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_SERVICE)
public class ServiceDemoActivity extends AppCompatActivity {


    private Intent myService;
    private Intent myServiceIntent;
    private static final String ACTION_FOO = "com.wdz.studycollection.datasave.service.action.FOO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);
        ButterKnife.bind(this);
        myService = new Intent(this, MyService.class);
        myServiceIntent = new Intent(this, MyIntentService.class);

    }
    //关闭service前需要先解绑service?


    @OnClick({R2.id.bt_start_service,R2.id.bt_stop_service,R2.id.bt_bind_service1,R2.id.bt_bind_service2,R2.id.bt_bind_service3,R2.id.bt_unbind_service,R2.id.bt_start_intent_service, R2.id.bt_stop_intent_service})
    public void onClick(View view){

        int id = view.getId();
        if (id == R.id.bt_start_service) {
            startService(myService);
        } else if (id == R.id.bt_stop_service) {
            stopService(myService);
            //finish();
        } else if (id == R.id.bt_bind_service1) {
            bindService(myService, mServiceConnection, BIND_AUTO_CREATE);
        } else if (id == R.id.bt_bind_service2) {
            bindService(myService, mServiceConnection, BIND_DEBUG_UNBIND);
        } else if (id == R.id.bt_bind_service3) {
            bindService(myService, mServiceConnection, BIND_NOT_FOREGROUND);
        } else if (id == R.id.bt_unbind_service) {
            unbindService(mServiceConnection);

            myServiceIntent.setAction(ACTION_FOO);
            startService(myServiceIntent);
        } else if (id == R.id.bt_start_intent_service) {
            myServiceIntent.setAction(ACTION_FOO);
            startService(myServiceIntent);
        } else if (id == R.id.bt_stop_intent_service) {
            stopService(myServiceIntent);
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

    @Override
    protected void onPause() {
        //unbindService(mServiceConnection);
        super.onPause();
    }
}
