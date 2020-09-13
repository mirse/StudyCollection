package com.wdz.module_customview.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;

@Route(path = ARouterConstant.ACTIVITY_RING_COLOR_PICKER)
public class RingColorPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ColorPickerRingRGB
        setContentView(R.layout.module_customview_activity_ring_color_picker);
    }
}
