package com.example.dezhiwang.studycollection.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.example.dezhiwang.studycollection.R;
import com.example.dezhiwang.studycollection.TurnPage.CoverPageView;
import com.example.dezhiwang.studycollection.TurnPage.PicturePageFactory;


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
