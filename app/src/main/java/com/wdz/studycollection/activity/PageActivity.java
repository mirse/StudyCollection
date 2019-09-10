package com.wdz.studycollection.activity;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.wdz.studycollection.R;
import com.wdz.studycollection.turnpage.CoverPageView;
import com.wdz.studycollection.turnpage.PicturePageFactory;


public class PageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        CoverPageView mPageView = findViewById(R.id.cpv);
        int[] pIds=new int[]{R.drawable.football,R.drawable.ic_launcher_background,R.drawable.cartoon};
        mPageView.setPageFactory(new PicturePageFactory(this,pIds));
    }
}
