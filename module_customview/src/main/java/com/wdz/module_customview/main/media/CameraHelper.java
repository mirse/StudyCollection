package com.wdz.module_customview.main.media;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.TriggerEvent;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraHelper implements Camera.PreviewCallback {
    private final String TAG = this.getClass().getSimpleName();
    private Camera camera;
    private Camera.Parameters parameters;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private int cameraInfo = Camera.CameraInfo.CAMERA_FACING_BACK;

    public CameraHelper(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceView.getHolder();
        init();
    }

    public void startPreview(){
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void init(){
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (camera==null){
                    openCamera(cameraInfo);
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void openCamera(int cameraInfo) {
        boolean supportCameraFacing = supportCameraFacing(cameraInfo);
        if (supportCameraFacing){
            camera = Camera.open(cameraInfo);
            initParameters(camera);
            camera.setPreviewCallback(this);
        }
    }

    /**
     * 初始化相机参数
     * @param camera
     */
    private void initParameters(Camera camera) {
        parameters = camera.getParameters();
        parameters.setPreviewFormat(ImageFormat.NV21);
        Camera.Size bestSize = getBestSize(surfaceView.getWidth(), surfaceView.getHeight(), parameters.getSupportedPreviewSizes());
        parameters.setPreviewSize(bestSize.width,bestSize.height);
        if (isSupportFocus(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        camera.setParameters(parameters);


    }

    /**
     * 判断是否支持对焦
     * @param focusMode
     * @return
     */
    private boolean isSupportFocus(String focusMode){
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        for (int i=0;i<supportedFocusModes.size();i++){
            if (supportedFocusModes.get(i).equals(focusMode)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取最优指定尺寸
     * @param targetWidth
     * @param targetHeight
     * @param supportedPreviewSizes
     */
    private Camera.Size getBestSize(int targetWidth, int targetHeight, List<Camera.Size> supportedPreviewSizes) {
        Camera.Size bestSize = null;
        float ratio = (float) targetHeight/targetWidth;
        float minDiff = ratio;
        for (int i=0;i<supportedPreviewSizes.size();i++){
            Camera.Size size = supportedPreviewSizes.get(i);
            int supportRatio = size.width/size.height;
            Log.i(TAG, "getBestSize: size.width:"+size.width+" size.height:"+size.height+" supportRatio:"+supportRatio);
        }
        for (int i=0;i<supportedPreviewSizes.size();i++){
            Camera.Size size = supportedPreviewSizes.get(i);
            if (size.width == targetWidth && size.height == targetHeight){
                bestSize = size;
            }
            float supportRatio = (float) size.width/size.height;
            if (Math.abs(supportRatio-ratio)<minDiff){
                minDiff = Math.abs(supportRatio-ratio);
                bestSize = size;
            }
        }
        Log.i(TAG, "目标尺寸: width:"+targetWidth+" height:"+targetHeight);
        Log.i(TAG, "最优尺寸: width:"+bestSize.width+" height:"+bestSize.height);
        return bestSize;

    }

    private boolean supportCameraFacing(int cameraFacing) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i=0;i<Camera.getNumberOfCameras();i++){
            Camera.getCameraInfo(i,cameraInfo);
            if (cameraInfo.facing == cameraFacing){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}
