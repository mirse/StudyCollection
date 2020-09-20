package com.wdz.module_communication.main.internet.glide;

import android.util.Log;

public class Outter {
    private static final String TAG = "Outter";

    private final GlideActivity glideActivity;

    public Outter(GlideActivity activity) {
        glideActivity = activity;
    }

    public synchronized void onMsgGet(String msg) {
        Log.i(TAG,msg);
    }
}
