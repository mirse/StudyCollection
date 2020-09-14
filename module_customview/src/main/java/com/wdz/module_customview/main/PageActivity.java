package com.wdz.module_customview.main;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.view.turnpage.CoverPageView;
import com.wdz.module_customview.view.turnpage.PicturePageFactory;


@Route(path = ARouterConstant.ACTIVITY_PAGE)
public class PageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_page);
        CoverPageView mPageView = findViewById(R.id.cpv);
        int[] pIds=new int[]{R.drawable.football,R.drawable.ic_launcher_background,R.drawable.cartoon};
        mPageView.setPageFactory(new PicturePageFactory(this,pIds));
    }
}
