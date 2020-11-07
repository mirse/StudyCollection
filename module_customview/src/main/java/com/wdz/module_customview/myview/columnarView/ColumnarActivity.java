package com.wdz.module_customview.myview.columnarView;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConstant.ACTIVITY_COLUMNAR)
public class ColumnarActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.columnarView)
    ColumnarView columnarView;
    @BindView(R2.id.colorCircleView)
    ColorCircleView colorCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_columnar);
        ButterKnife.bind(this);


        columnarView.setOnMoveListener(new ColumnarView.onMoveListener() {
            @Override
            public void onMove(int percent) {
                Log.i(TAG, "onMove: " + percent);
            }

            @Override
            public void onMoveUp(int percent) {
                Log.i(TAG, "onMoveUp: " + percent);
            }
        });
        columnarView.setPercent(50);
    }
}
