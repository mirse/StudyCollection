package com.wdz.studycollection.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.studycollection.R;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewPager2)
    ViewPager2 viewPager2;
    @BindView(R.id.ll_tab_1)
    LinearLayout llTab1;
    @BindView(R.id.ll_tab_2)
    LinearLayout llTab2;
    @BindView(R.id.ll_tab_3)
    LinearLayout llTab3;
    @BindView(R.id.ll_tab_4)
    LinearLayout llTab4;

    private List<Fragment> fragmentArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        fragmentArrayList.clear();
        fragmentArrayList.add((Fragment) ARouter.getInstance().build(ARouterConstant.FRAGMENT_CUSTOM_VIEW).navigation());
        fragmentArrayList.add((Fragment) ARouter.getInstance().build(ARouterConstant.FRAGMENT_CUMMUNICATION).navigation());
        fragmentArrayList.add((Fragment) ARouter.getInstance().build(ARouterConstant.FRAGMENT_ARCHITECTURE).navigation());
        fragmentArrayList.add((Fragment) ARouter.getInstance().build(ARouterConstant.FRAGMENT_BASIS).navigation());
        FragmentStateAdapter fragmentAdapter = new FragmentAdapter(this,fragmentArrayList);
        viewPager2.setAdapter(fragmentAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selectBottomTab(position);
            }
        });
        viewPager2.setCurrentItem(0);

    }
    @OnClick({R.id.ll_tab_1,R.id.ll_tab_2,R.id.ll_tab_3,R.id.ll_tab_4})
    public void onClick(View view){
        llTab1.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab2.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab3.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab4.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        if (R.id.ll_tab_1 == view.getId()){
            viewPager2.setCurrentItem(0,false);
            llTab1.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
        }
        else if (R.id.ll_tab_2 == view.getId()){
            viewPager2.setCurrentItem(1,false);
            llTab2.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
        }
        else if (R.id.ll_tab_3 == view.getId()){
            viewPager2.setCurrentItem(2,false);
            llTab3.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
        }
        else if (R.id.ll_tab_4 == view.getId()){
            viewPager2.setCurrentItem(3,false);
            llTab4.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
        }
    }

    private void selectBottomTab(int position) {

        llTab1.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab2.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab3.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        llTab4.setBackgroundColor(getBaseContext().getColor(R.color.gray_holo_light));
        switch (position){
            case 0:
                llTab1.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
                break;
            case 1:
                llTab2.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
                break;
            case 2:
                llTab3.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
                break;
            case 3:
                llTab4.setBackgroundColor(getBaseContext().getColor(R.color.blue_color));
                break;
            default:
                break;
        }
    }
}
