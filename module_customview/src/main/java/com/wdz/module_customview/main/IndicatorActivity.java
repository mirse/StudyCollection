package com.wdz.module_customview.main;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.view.MyIndicatorView;

@Route(path = ARouterConstant.ACTIVITY_INDICATOR)
public class IndicatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_indicator);
        ViewPager mViewPager = findViewById(R.id.viewpager);
        MyIndicatorView mIndicator = findViewById(R.id.indicator);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        mIndicator.setUpWithViewPager(mViewPager);

    }
}
