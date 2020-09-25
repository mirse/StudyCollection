package com.wdz.module_customview.myview.linechart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = ARouterConstant.ACTIVITY_LINE_CHART)
public class LineChartActivity extends AppCompatActivity {
    @BindView(R2.id.LineChartView)
    LineChartView lineChartView;
    private List<Integer> yList = new ArrayList<>();
    private List<String> xList = new ArrayList<>();

    private List<LineChartView.Point> datas = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_customview_activity_line_chart);
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
