package com.example.dezhiwang.studycollection.IndicatorView;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dezhiwang.studycollection.R;

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