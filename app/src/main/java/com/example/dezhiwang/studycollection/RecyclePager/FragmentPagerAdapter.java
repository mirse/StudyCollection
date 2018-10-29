package com.example.dezhiwang.studycollection.RecyclePager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ViewGroup;

import com.example.dezhiwang.studycollection.IndicatorView.Fragment1;
import com.example.dezhiwang.studycollection.IndicatorView.Fragment2;
import com.example.dezhiwang.studycollection.IndicatorView.Fragment3;
import com.example.dezhiwang.studycollection.IndicatorView.Fragment4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dezhi.wang on 2018/9/29.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private List<Fragment> listFragment;
    private List<Fragment> listFragment1;
    public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentArrayList) {
        super(fm);
        this.listFragment=fragmentArrayList;
      //  listFragment1=new ArrayList<>();

    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        fragment = listFragment.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
//        if (listFragment.size()<4){
//            return listFragment.size()*2;
//        }else{
            return Integer.MAX_VALUE;
//        }

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        position=position % listFragment.size(); //处理position。让数组下标落在[0,fragmentList.size)中，防止越界
//        Log.i("text",position+"---position");
        return super.instantiateItem(container, position%listFragment.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position%listFragment.size(), object);
    }
}
