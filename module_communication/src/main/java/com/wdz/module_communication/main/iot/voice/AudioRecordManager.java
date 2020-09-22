package com.wdz.module_communication.main.iot.voice;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioRecordManager {

    private int sampleRate = 44100;

    public void startRecord(){
        int audioSize = AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, audioSize);
        audioRecord.startRecording();
//        byte[] bufferRead = new byte[READ_BUFFERSIZE];
//        while (audioRecord.read(bufferRead, 0, READ_BUFFERSIZE) > 0) {
//            currentFrequency = processSampleData(bufferRead, sampleRate);
//        }
    }
}
