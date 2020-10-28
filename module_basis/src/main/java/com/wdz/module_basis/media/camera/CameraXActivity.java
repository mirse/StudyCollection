package com.wdz.module_basis.media.camera;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_CAMERAX)
public class CameraXActivity extends PermissionActivity implements View.OnTouchListener {

    @BindView(R2.id.surfaceView)
    SurfaceView surfaceView;
    private CameraHelper cameraHelper;
    @BindView(R2.id.over_camera_view)
    OverCameraView mOverCameraView;//绘制对焦框控件


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);
        ButterKnife.bind(this);
        initMorePermission(new String[]{Manifest.permission.CAMERA});
//相机预览控件接收Touch事件
        surfaceView.setOnTouchListener(this);
    }

    private Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            mOverCameraView.disDrawTouchFocusRect();//清除对焦框
        }
    };

    @Override
    protected void alreadyGetPermission() {
        cameraHelper = new CameraHelper(surfaceView,this);

    }

    @Override
    protected void onGetPermission() {
        cameraHelper = new CameraHelper(surfaceView,this);

    }

    @Override
    protected void onDenyPermission() {

    }

    @OnClick(R2.id.bt_open_camera)
    public void onClick(View view){
        if (view.getId() == R.id.bt_open_camera) {
            cameraHelper.startPreview();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraHelper.releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraHelper.releaseCamera();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            //获取点击屏幕的位置，作为焦点位置，用于计算对焦区域
            float x=event.getX();
            float y=event.getY();

            //对焦并绘制对焦矩形框
            mOverCameraView.setTouchFoucusRect(cameraHelper.getCamera(),autoFocusCallback, x, y);
        }
        return false;
    }
}
