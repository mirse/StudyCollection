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
    private List<String> errorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.bind(this);
        for (int i=0;i<10;i++){
            yList.add(i);
        }

        for (int i=0;i<10;i++){
            xList.add("day"+i);
        }
        datas.add("day0,4.5");
        datas.add("day1,0");
        datas.add("day2,4.5");
        datas.add("day3,4.5");

        datas.add("day5,4.5");
        datas.add("day6,4.5");

        datas.add("day8,0");
        lineChartView.setXYDataList(xList,yList);
        lineChartView.setDataList(datas);
        errorList.add("day4,4.5");
        errorList.add("day7,6");
        lineChartView.setErrorList(errorList);
    }
}
