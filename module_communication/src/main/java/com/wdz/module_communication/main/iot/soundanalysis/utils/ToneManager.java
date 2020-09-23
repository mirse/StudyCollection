package com.wdz.module_communication.main.iot.soundanalysis.utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import java.util.Arrays;

/**
 * @author dezhi.wang
 */
public class ToneManager {
    private final String TAG = this.getClass().getSimpleName();
    private AudioRecord audioRecord;
    /**
     * 采样频率
     */
    private int sampleRate = 44100;
    /**
     * 采样点数
     */
    private int sampleCount = 20;
    /**
     * 傅里叶变化工具类
     */
    private FFT fft = new FFT();

    private int SPACE = 200;// 间隔取样时间

    private OnVoiceChangeListener onVoiceChangeListener;


    private final Handler mHandler = new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        @Override
        public void run() {
            updateMicStatus();
        }
    };
    private Thread thread;

    public void startRecord() {
        initAudioRecord();
    }

    public void stopRecord() {
        if (audioRecord != null && audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecord.stop();
            audioRecord.release();
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public boolean isRecording(){
        if (audioRecord!=null){
            return audioRecord.getState() == AudioRecord.STATE_INITIALIZED;
        }
        return false;
    }

    /**
     * 初始化
     */
    private void initAudioRecord() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT));
        Log.i(TAG, "initAudioRecord: audioRecord.getState():" + audioRecord.getState());
        if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            sampleRate = 44100;
            sampleCount = 1024;
        }
        audioRecord.startRecording();
       new Thread(new Runnable() {
            @Override
            public void run() {
                if (audioRecord != null) {
                    byte[] bufferRead = new byte[sampleCount];

                    while (audioRecord.read(bufferRead, 0, sampleCount) > 0) {
                        Log.i(TAG, "run: "+ Arrays.toString(bufferRead)+" length:"+bufferRead.length);
                        double currentFrequency = fft.getFrequency(bufferRead, sampleRate, sampleCount);

                        if (onVoiceChangeListener != null) {
                            onVoiceChangeListener.onVoiceChange(currentFrequency);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();

    }

    private void updateMicStatus() {


    }


    public interface OnVoiceChangeListener {
        void onVoiceChange(double tone);
    }

    public void setOnVoiceChangeListener(OnVoiceChangeListener onVoiceChangeListener) {
        this.onVoiceChangeListener = onVoiceChangeListener;

    }
}
