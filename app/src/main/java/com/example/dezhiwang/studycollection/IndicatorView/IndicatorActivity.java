package com.example.dezhiwang.studycollection.IndicatorView;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
