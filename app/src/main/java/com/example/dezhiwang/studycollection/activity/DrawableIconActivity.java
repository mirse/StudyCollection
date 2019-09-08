package com.example.dezhiwang.studycollection.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.mydrawable.ViewWithIcon;
import com.example.dezhiwang.studycollection.R;

public class DrawableIconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_icon);
        ViewWithIcon viewWithIcon = findViewById(R.id.viewWithIcon);
        viewWithIcon.setNum(50);
    }
}
