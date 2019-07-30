package com.example.dezhiwang.studycollection.RecyclePager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dezhiwang.studycollection.R;

public class ImageRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recycler);
        ViewPager mViewPager = findViewById(R.id.viewpager);
        int[] images = {R.drawable.cartoon, R.drawable.football};
        ImageRecyclerAdapter imageRecyclerAdapter = new ImageRecyclerAdapter(this, images);
        mViewPager.setAdapter(imageRecyclerAdapter);
      //  mViewPager.setCurrentItem(400);
        if(images.length > 1) {
            mViewPager.setCurrentItem(((Short.MAX_VALUE / 2) / images.length) * images.length, false);
        }
    }
}