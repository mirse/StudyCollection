package com.wdz.module_basis.adaptation.storage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_STORAGE)
public class StorageDemoActivity extends PermissionActivity {
    private static final String FILE_PROVIDER_AUTHORITY = "com.wdz.module_basis.provider";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_demo);
        ButterKnife.bind(this);
        initMorePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    @OnClick(R2.id.bt_camera)
    public void onClick(View view){
        if (R.id.bt_camera == view.getId()){
            startCamera();
        }
    }

    private void startCamera() {
        //用来打开相机的Intent
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
        if(takePhotoIntent.resolveActivity(getPackageManager())!=null){
            File imageFile = createImageFile();
            Uri mImageUri = null;
            if (imageFile!=null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    mImageUri = FileProvider.getUriForFile(this,FILE_PROVIDER_AUTHORITY,imageFile);
                }
                else{
                    mImageUri = Uri.fromFile(imageFile);
                }
            }

            //启动相机
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
            startActivityForResult(takePhotoIntent,1);
        }
    }

    private File createImageFile(){
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "JPEG_"+fileName;
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File tempFile = null;
        try {
            tempFile = File.createTempFile(imageName, ".jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri data1 = data.getData();
            Bundle extras = data.getExtras();

        }
    }

    @Override
    protected void alreadyGetPermission() {

    }

    @Override
    protected void onGetPermission() {

    }

    @Override
    protected void onDenyPermission() {

    }
}
