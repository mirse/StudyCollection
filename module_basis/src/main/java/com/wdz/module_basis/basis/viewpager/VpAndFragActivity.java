package com.wdz.module_basis.basis.viewpager;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;

import java.util.ArrayList;
import java.util.List;
@Route(path = ARouterConstant.ACTIVITY_VP_AND_FRAG)
public class VpAndFragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_basis_activity_vp_and_frag);
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
            super(fm);
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
