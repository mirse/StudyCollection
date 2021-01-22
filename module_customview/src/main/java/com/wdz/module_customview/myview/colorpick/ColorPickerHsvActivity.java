package com.wdz.module_customview.myview.colorpick;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;

import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConstant.ACTIVITY_HSV_COLOR_PICKER)
public class ColorPickerHsvActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.colorPickerHSV)
    ColorPickerHSV colorPickerHSV;
    @BindView(R2.id.colorBarView)
    ColorBarView colorBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ColorPickerHSV
        setContentView(R.layout.module_customview_activity_color_picker_hsv);
        ButterKnife.bind(this);
        colorPickerHSV.setPointVisible(true);
        colorPickerHSV.setPointPosition(HextoColor("#00ffffff"));
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
        colorBarView.setCurrentColor(Integer.parseInt("FF8989", 16));
        colorBarView.setOnColorChangerListener(new ColorBarView.OnColorChangeListener() {
            @Override
            public void onColorChange(int color) {
                String s = Integer.toHexString(color);
                Log.i(TAG, "onColorChange: "+s);
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
