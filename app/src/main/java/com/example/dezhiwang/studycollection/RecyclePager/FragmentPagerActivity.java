package com.example.dezhiwang.studycollection.RecyclePager;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dezhiwang.studycollection.IndicatorView.Fragment1;
import com.example.dezhiwang.studycollection.IndicatorView.Fragment2;
import com.example.dezhiwang.studycollection.IndicatorView.Fragment3;
import com.example.dezhiwang.studycollection.IndicatorView.Fragment4;
import com.example.dezhiwang.studycollection.IndicatorView.FragmentAdapter;
import com.example.dezhiwang.studycollection.IndicatorView.MyIndicatorView;
import com.example.dezhiwang.studycollection.R;

import java.util.ArrayList;
import java.util.List;
//无限循环的fragment+viewpager 暂时无法解决4个以下的bug, 单图片的循环可以使用倍数x2的方法解决，
public class FragmentPagerActivity extends AppCompatActivity {

    private List<Fragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_page);
        ViewPager mViewPager = findViewById(R.id.viewpager);
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new Fragment1());
        fragmentArrayList.add(new Fragment2());
        fragmentArrayList.add(new Fragment3());
        fragmentArrayList.add(new Fragment4());
        FragmentPagerAdapter fragmentAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),fragmentArrayList);

        mViewPager.setAdapter(fragmentAdapter);
        //mViewPager.setOffscreenPageLimit(2);//当实际fragment个数小于limit*2+2的时候，会出现fragment缓存不够用的情况
      //  mViewPager.setCurrentItem(4);

    }
}
