package com.wdz.studycollection.fragment.viewpager;

import android.os.PersistableBundle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.wdz.studycollection.R;
import com.wdz.studycollection.recyclepager.FragmentPagerAdapter;

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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
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