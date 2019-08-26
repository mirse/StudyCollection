package com.example.dezhiwang.studycollection.Activity;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.MyDrawable.ViewWithIcon;
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
