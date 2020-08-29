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

    private List<LineChartView.Point> datas = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.bind(this);
        for (int i=0;i<9;i++){
            yList.add(i*100);
        }

        for (int i=0;i<10;i++){
            xList.add("day"+i);
        }

        for (int i=0;i<10;i++){
            LineChartView.Point point = new LineChartView.Point();

            if (i==2){
                point.setxValue("day"+i);
                point.setyValue("200");
                point.setErrorPoint(true);
            }
            else if (i==7){
                point.setxValue("day"+i);
                point.setyValue("200");
                point.setErrorPoint(true);
            }
            else{
                point.setxValue("day"+i);
                point.setyValue("400");
                point.setErrorPoint(false);
            }
            datas.add(point);

        }
        lineChartView.setXYDataList(xList,yList);
        lineChartView.setDataList(datas);

    }
}
