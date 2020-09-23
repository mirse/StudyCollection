package com.wdz.module_customview.main.media;

import android.Manifest;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
@Route(path = ARouterConstant.ACTIVITY_CAMERAX)
public class CameraXActivity extends PermissionActivity {

    @BindView(R2.id.surfaceView)
    SurfaceView surfaceView;
    private CameraHelper cameraHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_x);
        ButterKnife.bind(this);
        initMorePermission(new String[]{Manifest.permission.CAMERA});

    }

    @Override
    protected void alreadyGetPermission() {
        cameraHelper = new CameraHelper(surfaceView);

    }

    @Override
    protected void onGetPermission() {
        cameraHelper = new CameraHelper(surfaceView);

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
}
