package com.wdz.module_basis.media.voice;

import android.Manifest;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;
import com.wdz.module_basis.media.voice.utils.ToneManager;
import com.wdz.module_basis.media.voice.view.VisualizerView;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_VOICE)
public class VoiceActivity extends PermissionActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.bt_voice)
    Button btnVoice;
    @BindView(R2.id.visualizerView)
    VisualizerView visualizerView;
    private Visualizer visualizer;
    /**
     * 是否正在录制
     */
    private boolean isRecording = false;
    private RecordManager aa;
    private ToneManager toneManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.bind(this);
        initMorePermission(new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE});
        toneManager = new ToneManager();

        toneManager.setOnVoiceChangeListener(new ToneManager.OnVoiceChangeListener() {
            @Override
            public void onVoiceChange(double[] tone) {
                Log.i(TAG, "onVoiceChange: "+ Arrays.toString(tone));

                Log.i(TAG, "onVoiceChange: "+tone.length);
                List<Double> toneList = new ArrayList<>();
                for (int i = 0; i < tone.length; i++) {
                    if (tone[i]>0){
                        toneList.add(tone[i]);
                    }
                }
                final List<Integer> toneCountList = new ArrayList<>();
                for (int i = 0; i < toneList.size(); i+=4) {
                    if ((i+4) < toneList.size()){
                        List<Double> doubles = toneList.subList(i, i + 4);
                        int count = 0;
                        for (int j = 0; j < doubles.size(); j++) {
                           count += doubles.get(j);
                        }
                        count = count/3000;
                        toneCountList.add(count);
                    }

                }

                Log.i(TAG, "onVoiceChange: "+toneCountList.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        visualizerView.updateVisualizer(toneCountList);
                    }
                });




//                Log.i(TAG, "onVoiceChange: "+tone.length);
//                List<Double> toneList = new ArrayList<>();
//                for (int i = 0; i < tone.length; i++) {
//                    if (tone[i]>0){
//                        toneList.add(tone[i]);
//                    }
//                }
//                final List<Integer> toneCountList = new ArrayList<>();
//                for (int i = 0; i < toneList.size(); i+=4) {
//                    if ((i+4) < toneList.size()){
//                        List<Double> doubles = toneList.subList(i, i + 4);
//                        int count = 0;
//                        for (int j = 0; j < doubles.size(); j++) {
//                            if (doubles.get(j)>2000){
//                                count++;
//                            }
//                        }
//                        toneCountList.add(count);
//                    }
//
//                }
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        visualizerView.updateVisualizer(toneCountList);
//                    }
//                });
//
//
//
//
//                Log.i(TAG, "onVoiceChange: "+toneCountList.toString());
            }
        });

    }
    @OnClick(R2.id.bt_voice)
    public void onClick(View view){
        if (R.id.bt_voice == view.getId()){

            isRecording = !isRecording;
            if (isRecording){
                //开始录制音频
                btnVoice.setText("stop");
//                aa.startRecord();


//                visualizer.setEnabled(true);

                toneManager.startRecord();
            }
            else{
                //停止录制音频
                btnVoice.setText("start");
//                aa.stopRecord();


//                visualizer.setEnabled(false);


                toneManager.stopRecord();
            }


        }
    }

    @Override
    protected void alreadyGetPermission() {
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.eason);
//        try {
//            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
//        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
//        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
//            @Override
//            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
//                Log.i(TAG, "onWaveFormDataCapture: "+ Arrays.toString(waveform));
//            }
//
//            @Override
//            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
//
//                float[] magnitudes = new float[fft.length / 2];
//                int max = 0;
//                for (int i = 0; i < magnitudes.length; i++) {
//                    magnitudes[i] = (float) Math.hypot(fft[2 * i], fft[2 * i + 1]);
//                    if (magnitudes[max] < magnitudes[i]) {
//                        max = i;
//                    }
//
//                }
//
//                int currentFrequency = max * samplingRate / fft.length;
//                Log.i("xiaozhu", "currentFrequency=" + currentFrequency);
//                //Log.i(TAG, "onFftDataCapture: "+ Arrays.toString(fft)+" size:"+fft.length);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        },Visualizer.getMaxCaptureRate()/2,true,true);
        File file = new File("/sdcard/aa");
        if (!file.exists()){
            try {
                file.createNewFile();
                aa = new RecordManager(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            aa = new RecordManager(file);
        }


    }

    @Override
    protected void onGetPermission() {

//        MediaPlayer mediaPlayer = new MediaPlayer();
//        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
//        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
//        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
//            @Override
//            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
//                Log.i(TAG, "onWaveFormDataCapture: ");
//            }
//
//            @Override
//            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
//                Log.i(TAG, "onFftDataCapture: ");
//            }
//        },Visualizer.getMaxCaptureRate()/2,true,true);

        aa = new RecordManager(new File("/sdcard/Wdz/aa"));
    }

    @Override
    protected void onDenyPermission() {

    }
}

