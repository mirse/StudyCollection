package com.wdz.studycollection.indicatorview;


import android.os.Bundle;

import com.wdz.studycollection.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class IndicatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        ViewPager mViewPager = findViewById(R.id.viewpager);
        MyIndicatorView mIndicator = findViewById(R.id.indicator);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(fragmentAdapter);
        mIndicator.setUpWithViewPager(mViewPager);

    }
}