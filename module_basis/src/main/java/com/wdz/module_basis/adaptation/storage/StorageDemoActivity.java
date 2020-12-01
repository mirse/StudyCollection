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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.PermissionActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_STORAGE)
public class StorageDemoActivity extends PermissionActivity {
    private final String TAG = this.getClass().getSimpleName();

    @BindView(R2.id.iv_photo)
    ImageView ivPhoto;

    private static final String FILE_PROVIDER_AUTHORITY = "com.wdz.module_basis.provider";
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_demo);
        ButterKnife.bind(this);
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        Log.i(TAG, "onCreate: "+file.getAbsolutePath());
        initMorePermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE);
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

            mImageUri = null;
            if (imageFile!=null){
                Log.i(TAG, "startCamera: imageFile"+imageFile.getAbsolutePath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    takePhotoIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//表示对目标应用临时授权该Uri所代表的文件
                    mImageUri = FileProvider.getUriForFile(this,FILE_PROVIDER_AUTHORITY,imageFile);
                    ///storage/emulated/0/Android/data/com.wdz.studycollection/files/Pictures/JPEG_20201201_094013377987067.jpg

         //content://com.wdz.module_basis.provider/my_images/Android/data/com.wdz.studycollection/files/Pictures/JPEG_20201201_094109124569061.jpg
         //content://com.wdz.module_basis.provider/my_images/Pictures/JPEG_20201201_094013377987067.jpg
                }
                else{
                    mImageUri = Uri.fromFile(imageFile);
                }
            }

            //启动相机
            //如果指定了存储路径的uri,则在返回的data就为null.
            //如果不指定图片的uri,则可能会返回data(照相机有自己默认的存储路径：data.getParcelableExtra("data")）
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
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
            ivPhoto.setImageURI(mImageUri);
            Log.i(TAG, "onActivityResult:data: "+data);
            if (data!=null){
                Uri data1 = data.getData();
                Bundle extras = data.getExtras();

            }

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
