package com.wdz.studycollection.myview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineChartActivity extends AppCompatActivity {
    @BindView(R.id.LineChartView)
    LineChartView lineChartView;
    private List<Integer> yList = new ArrayList<>();
    private List<String> xList = new ArrayList<>();

    private List<String> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.bind(this);
        for (int i=0;i<10;i++){
            yList.add(i);
        }

        for (int i=0;i<10;i++){
            xList.add(String.valueOf(i));
        }
        datas.add("1,2");
        datas.add("2,8.3");
        lineChartView.setXYDataList(xList,yList);
        lineChartView.setDataList(datas);
    }
}
