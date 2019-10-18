package com.wdz.studycollection.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wdz.studycollection.R;
import com.wdz.studycollection.myview.ColorPickerRGB;
import com.wdz.studycollection.myview.MyColor;

public class ColorPickerActivity extends AppCompatActivity {

    private ColorPickerRGB mColorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ColorPickerRGB
        setContentView(R.layout.activity_color_picker);
        ColorPickerRGB colorPickerRGB = findViewById(R.id.ColorPickerRGB);
        colorPickerRGB.setonColorChange(new ColorPickerRGB.OnColorChangeListener() {
            @Override
            public void onRGBChange(MyColor myColor) {
                Log.i("text","A:"+myColor.getA()+" R:"+myColor.getR()+" G:"+myColor.getG()+"  B:"+myColor.getB());
            }
        });

    }
}
