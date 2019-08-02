package com.example.dezhiwang.studycollection.Fragment.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dezhiwang.studycollection.Fragment.Fragment1;
import com.example.dezhiwang.studycollection.Fragment.Fragment2;
import com.example.dezhiwang.studycollection.Fragment.Fragment3;
import com.example.dezhiwang.studycollection.R;
import com.example.dezhiwang.studycollection.RecyclePager.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class VpAndFragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp_and_frag);
        ViewPager mViewPager = findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<>();
        list.add(new f1());
        list.add(new f2());
        list.add(new f3());
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), list));
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm,list);
            this.list = list;
        }
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
        @Override
        public int getCount() {
            return list.size();
        }
    }
}
