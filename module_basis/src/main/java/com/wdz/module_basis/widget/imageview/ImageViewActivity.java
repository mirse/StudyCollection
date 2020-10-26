package com.wdz.module_basis.widget.imageview;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConstant.ACTIVITY_IMAGE_VIEW)
public class ImageViewActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.iv)
    TextView iv;

    //?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Toast.makeText(this, "dpi:" + displayMetrics.densityDpi, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate: dpi:" + displayMetrics.densityDpi);

    }
}
