package com.example.dezhiwang.studycollection.Activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.MyView.ColorPickerRGB;
import com.example.dezhiwang.studycollection.MyView.MyColor;
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
