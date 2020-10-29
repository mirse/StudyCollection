package com.wdz.module_basis.media.camera.camerax;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.common.util.concurrent.ListenableFuture;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_CAMERAX)
public class CameraXActivity extends PermissionActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.bt_take_photo)
    Button btTakePhoto;
    @BindView(R2.id.previewView)
    PreviewView previewView;
    private final int REQUEST_CODE_PERMISSIONS = 10;
    private ExecutorService cameraExecutor;
    private ProcessCameraProvider processCameraProvider;
    private CameraSelector cameraSelector;
    private Preview preview;
    private ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);
        ButterKnife.bind(this);

        initMorePermission(Manifest.permission.CAMERA);

        cameraExecutor = Executors.newSingleThreadExecutor();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void alreadyGetPermission() {
        startCamera();
    }

    @Override
    protected void onGetPermission() {
        startCamera();
    }

    @Override
    protected void onDenyPermission() {

    }

    /**
     * 开始预览
     */
    private void startCamera() {
        //将摄像头的生命周期绑定到生命周期所有者
        final ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        //添加监听器
        cameraProviderListenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    //添加processCameraProvider，用于将相机的生命周期绑定到lifecycleOwner
                    processCameraProvider = cameraProviderListenableFuture.get();
                    //初始化preview对象
                    preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(previewView.createSurfaceProvider());

                    //创建CameraSelector对象
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;


                    //初始化imageCapture
                    imageCapture = new ImageCapture.Builder()
                            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                            .build();

                    //分析图像
                    ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                            .setTargetResolution(new Size(1280, 720))
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build();
                    imageAnalysis.setAnalyzer(cameraExecutor, new ImageAnalysis.Analyzer() {
                        @Override
                        public void analyze(@NonNull ImageProxy image) {
                            int rotationDegrees = image.getImageInfo().getRotationDegrees();
                            Log.i(TAG, "analyze: "+rotationDegrees);
                        }
                    });

                    processCameraProvider.unbindAll();
                   // processCameraProvider.bindToLifecycle(CameraXActivity.this, cameraSelector, preview);
                    processCameraProvider.bindToLifecycle(CameraXActivity.this, cameraSelector,preview,imageAnalysis, imageCapture);


                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
            //主线程运行
        },ContextCompat.getMainExecutor(this));
    }
    @OnClick(R2.id.bt_take_photo)
    public void onClick(View view){
        if (view.getId() == R.id.bt_take_photo){
            takePhoto();
        }

    }

    private void takePhoto() {

        //创建文件保存图片
        final File photoFile = new File("/sdcard/Wdz", new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US
        ).format(System.currentTimeMillis()) + ".jpg");

        //创建一个OutputFileOption,指定有关输出结果的方式，例如：输出保存到刚刚创建的文件夹中
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        //开始拍照
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Uri uri = Uri.fromFile(photoFile);
                Log.i(TAG, "onImageSaved: "+uri.toString());
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
