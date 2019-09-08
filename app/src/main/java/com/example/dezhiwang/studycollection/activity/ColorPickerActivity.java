package com.example.dezhiwang.studycollection.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.myview.ColorPickerRGB;
import com.example.dezhiwang.studycollection.R;

public class ColorPickerActivity extends AppCompatActivity {

    private ColorPickerRGB mColorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ColorPickerRGB
        setContentView(R.layout.activity_color_picker);

    }
}
