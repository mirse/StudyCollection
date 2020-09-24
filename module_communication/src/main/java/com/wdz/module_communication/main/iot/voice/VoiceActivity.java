package com.wdz.module_communication.main.iot.voice;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;
import com.wdz.module_communication.main.iot.voice.utils.ToneManager;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_VOICE)
public class VoiceActivity extends PermissionActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.bt_voice)
    Button btnVoice;
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

    }
    @OnClick(R2.id.bt_voice)
    public void onClick(View view){
        if (R.id.bt_voice == view.getId()){

            isRecording = !isRecording;
            if (isRecording){
                //开始录制音频
//                btnVoice.setText("stop");
//                aa.startRecord();
//                visualizer.setEnabled(true);
                toneManager.startRecord();
            }
            else{
                //停止录制音频
//                btnVoice.setText("start");
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


        //aa = new RecordManager(new File("/sdcard/Wdz/aa"));
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

//        aa = new RecordManager(new File("/sdcard/Wdz/aa"));
    }

    @Override
    protected void onDenyPermission() {

    }
}

