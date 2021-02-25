package com.wdz.module_communication.main.iot.gatt.utils;

import android.os.Handler;
import android.util.Log;

/**
 * @Author dezhi.wang
 * @Date 2021/2/25 15:41
 */
public class TimeoutUtil {
    private static Handler handler = new Handler();

    public static void setTimeout(long timeout, final OnTimeoutListener onTimeoutListener){
        Log.i("TimeoutUtil", "setTimeout: "+handler.hashCode());
        if (onTimeoutListener!=null){
            onTimeoutListener.onTimeoutStart();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (onTimeoutListener!=null){
                    onTimeoutListener.onTimeoutFinish();
                }
            }
        },timeout);
    }

    public interface OnTimeoutListener{
        /**
         * 超时开始
         */
        void onTimeoutStart();

        /**
         * 超时结束
         */
        void onTimeoutFinish();
    }
}
