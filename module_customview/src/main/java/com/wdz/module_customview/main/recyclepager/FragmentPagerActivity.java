package com.wdz.module_customview.main.recyclepager;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.main.Fragment1;
import com.wdz.module_customview.main.Fragment2;
import com.wdz.module_customview.main.Fragment3;
import com.wdz.module_customview.main.Fragment4;


import java.util.ArrayList;
import java.util.List;

//无限循环的fragment+viewpager 暂时无法解决4个以下的bug, 单图片的循环可以使用倍数x2的方法解决，
@Route(path = ARouterConstant.ACTIVITY_FRAGMENT_PAGER)
public class FragmentPagerActivity extends AppCompatActivity {

    private List<Fragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_recycler_page);
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
