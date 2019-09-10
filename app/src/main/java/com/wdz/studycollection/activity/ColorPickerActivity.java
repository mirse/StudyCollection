package com.wdz.studycollection.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.wdz.studycollection.R;
import com.wdz.studycollection.myview.ColorPickerRGB;

public class ColorPickerActivity extends AppCompatActivity {

    private ColorPickerRGB mColorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ColorPickerRGB
        setContentView(R.layout.activity_color_picker);

    }
}
