package com.wdz.module_customview.myview.imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.base.BaseActivity;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
@Route(path = ARouterConstant.ACTIVITY_ROUND_IMAGE)
public class RoundImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image_view);
    }
}
