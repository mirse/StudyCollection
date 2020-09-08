package com.wdz.studycollection.activity;


import android.graphics.Color;
import android.os.Bundle;

import com.wdz.studycollection.R;
import com.wdz.studycollection.myview.ColorPickerHSV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ColorPicker2Activity extends AppCompatActivity {
    @BindView(R.id.colorPickerHSV)
    ColorPickerHSV colorPickerHSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ColorPickerHSV
        setContentView(R.layout.activity_color_picker2);
        ButterKnife.bind(this);
        colorPickerHSV.setPointVisible(true);
        colorPickerHSV.setPointPosition(HextoColor("#000000ff"));
        colorPickerHSV.setOnMoveListener(new ColorPickerHSV.onMoveListener() {
            @Override
            public void onMoveStart() {

            }

            @Override
            public void onMove(int r, int g, int b) {

            }

            @Override
            public void onMoveUp(int r, int g, int b) {

            }
        });
    }

    /**
     * 将十六进制 颜色代码 转换为 int
     *
     * @return
     */
    public static int HextoColor(String color) {

        // #ff00CCFF
        String reg = "#[a-f0-9A-F]{8}";
        if (!Pattern.matches(reg, color)) {
            color = "#00ffffff";
        }

        return Color.parseColor(color);
    }
}
