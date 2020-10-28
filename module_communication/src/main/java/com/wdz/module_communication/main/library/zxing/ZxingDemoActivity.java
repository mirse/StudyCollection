package com.wdz.module_communication.main.library.zxing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_communication.R;
import com.wdz.module_communication.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_ZXING)
public class ZxingDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_demo);
        ButterKnife.bind(this);
    }
    @OnClick(R2.id.bt_scan)
    public void onClick(View view){
        if (view.getId() == R.id.bt_scan){
            startActivity(new Intent(this,CaptureActivity.class));
        }
    }
}
