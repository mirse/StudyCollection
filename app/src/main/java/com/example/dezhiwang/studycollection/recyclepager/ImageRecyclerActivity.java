package com.example.dezhiwang.studycollection.recyclepager;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
