package com.wdz.module_customview.myview.combination.icon;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.myview.combination.icon.ViewWithIcon;

@Route(path = ARouterConstant.ACTIVITY_DRAWABLE_ICON)
public class DrawableIconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_drawable_icon);
        ViewWithIcon viewWithIcon = findViewById(R.id.viewWithIcon);
        viewWithIcon.setNum(50);
    }
}
