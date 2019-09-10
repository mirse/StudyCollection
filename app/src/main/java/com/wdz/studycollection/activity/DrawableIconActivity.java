package com.wdz.studycollection.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wdz.studycollection.R;
import com.wdz.studycollection.mydrawable.ViewWithIcon;

public class DrawableIconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_icon);
        ViewWithIcon viewWithIcon = findViewById(R.id.viewWithIcon);
        viewWithIcon.setNum(50);
    }
}
