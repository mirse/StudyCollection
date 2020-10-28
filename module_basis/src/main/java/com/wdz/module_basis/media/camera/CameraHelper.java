package com.wdz.module_basis.media.camera;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraHelper implements Camera.PreviewCallback {
    private final String TAG = this.getClass().getSimpleName();
    private Camera camera;
    private Camera.Parameters parameters;//参数
    private SurfaceView surfaceView;//用于预览的surfaceView
    private SurfaceHolder surfaceHolder;
    private int cameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;//摄像头方向
    private int mDisplayOrientation = 0;//预览的旋转角度
    private Activity mActivity;
    public CameraHelper(SurfaceView surfaceView,Activity activity) {
        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceView.getHolder();
        this.mActivity = activity;
        init();
    }

    public Camera getCamera(){
        return camera;
    }

    public void startPreview(){
        try {
            camera.setPreviewDisplay(surfaceHolder);
            setCameraDisplayOrientation(mActivity);
            //focus();
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
                    openCamera(cameraFacing);
                }
                startPreview();

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
        parameters.setPreviewFormat(ImageFormat.NV21);//预览的图片格式
        Camera.Size bestSize = getBestSize(surfaceView.getWidth(), surfaceView.getHeight(), parameters.getSupportedPreviewSizes());
        //设置预览尺寸
        parameters.setPreviewSize(bestSize.width,bestSize.height);
        //对焦模式
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

    /**
     * 设置预览旋转角度
     * @param activity
     */
    private void setCameraDisplayOrientation(Activity activity){
        Camera.CameraInfo cameraInfo = new  Camera.CameraInfo();
        Camera.getCameraInfo(cameraFacing,cameraInfo);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Log.i(TAG, "setCameraDisplayOrientation: rotation:"+rotation);
        int screenDegree = 0;
        switch (rotation){
            case Surface.ROTATION_0:
                screenDegree = 0;
                break;
            case Surface.ROTATION_90:
                screenDegree = 90;
                break;
            case Surface.ROTATION_180:
                screenDegree = 180;
                break;
            case Surface.ROTATION_270:
                screenDegree = 270;
                break;
            default:
                break;
        }

        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
            mDisplayOrientation = (cameraInfo.orientation + screenDegree)%360;
            mDisplayOrientation = (360 - mDisplayOrientation)%360;
        }
        else{
            mDisplayOrientation = (cameraInfo.orientation - screenDegree + 360)%360;
        }
        camera.setDisplayOrientation(mDisplayOrientation);
        Log.i(TAG, "setCameraDisplayOrientation: mDisplayOrientation:"+mDisplayOrientation);
    }

    public void releaseCamera(){
        if (camera!=null){
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }


}
