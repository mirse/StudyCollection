package com.wdz.module_customview.materialdesign;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_DRAWERLAYOUT)
public class DrawerLayoutActivity extends AppCompatActivity {

    @BindView(R2.id.drawer_layout)
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.btn_open_left,R2.id.btn_open_right,R2.id.btn_close_right})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btn_open_left) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else if (id == R.id.btn_open_right) {
            mDrawerLayout.openDrawer(GravityCompat.END);
        } else if (id == R.id.btn_close_right) {
            mDrawerLayout.closeDrawers();
        }
    }
}
