package com.wdz.module_basis.media.voice.utils;

import android.util.Log;

/**
 * 声音计算工具类
 * Created by shine on 18-10-15.
 */

public class VoiceUtil {
    private static final String TAG = "VoiceUtil";
    /**
     * 如果你录音的编码是16为pcm，而录音数据数据是byte，需要将两个byte转为一个short进行处理，建议用小端的方式。
     * 获取声音的分贝
     *  max = 45
     * @param bufferRead
     * @param lenght
     * @return
     */
    public static double getVolume(byte[] bufferRead, int lenght) {
//        int volume = 0;
//
//        for (int i = 0; i < bufferRead.length; i++) {
//            volume += bufferRead[i] * bufferRead[i];
//        }
//        Log.i(TAG, "getVolume volume: "+volume + " lenght:"+lenght);
//
//        double mean = volume / (float) lenght;
//        Log.i(TAG, "getVolume: mean:"+mean+" 10 * Math.log10(mean):--------"+10 * Math.log10(mean));
//        return 10 * Math.log10(mean);//10 * Math.log10(mean);
        double sumVolume = 0.0;

        double avgVolume = 0.0;

        double volume = 0.0;

        for(int i = 0; i < bufferRead.length; i+=2){
            int v1 = bufferRead[i] & 0xFF;

            int v2 = bufferRead[i + 1] & 0xFF;

            int temp = v1 + (v2 << 8);// 小端

            if (temp >= 0x8000) {
                temp = 0xffff - temp;

            }

            sumVolume += Math.abs(temp);

        }

        avgVolume = sumVolume / bufferRead.length / 2;

        volume = Math.log10(1 + avgVolume) * 10;

        Log.i(TAG, "getVolume: "+volume);
        return volume;
    }
}
