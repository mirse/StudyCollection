package com.wdz.module_customview.main.recyclepager;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;

@Route(path = ARouterConstant.ACTIVITY_IMAGE_RECYCLER)
public class ImageRecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_image_recycler);
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
