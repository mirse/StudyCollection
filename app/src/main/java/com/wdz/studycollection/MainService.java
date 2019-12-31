package com.wdz.studycollection;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {
    private static final String TAG = "MainService";
    private final IBinder mBinder = new LocalBinder();
    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    public MainService() {
    }

    public class LocalBinder extends Binder{
        public MainService getService(){
            return MainService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return mBinder;
    }
}
