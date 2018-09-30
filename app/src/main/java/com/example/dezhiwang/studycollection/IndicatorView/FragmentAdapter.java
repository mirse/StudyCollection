package com.example.dezhiwang.studycollection.IndicatorView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dezhi.wang on 2018/9/29.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        fragment1=new Fragment1();
        fragment2=new Fragment2();
        fragment3=new Fragment3();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=fragment1;
                break;
            case 1:
                fragment=fragment2;
                break;
            case 2:
                fragment=fragment3;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
