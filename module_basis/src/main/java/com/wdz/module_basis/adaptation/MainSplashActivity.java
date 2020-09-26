package com.wdz.module_basis.adaptation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;


import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_MAIN_SPLASH)
public class MainSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);
        ButterKnife.bind(this);
    }
    @OnClick({R2.id.bt_image,R2.id.bt_actionbar})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.bt_image) {
            startActivity(new Intent(this, SplashImageActivity.class));
        } else if (id == R.id.bt_actionbar) {
            startActivity(new Intent(this, SplashBarActivity.class));
        }
    }
}
