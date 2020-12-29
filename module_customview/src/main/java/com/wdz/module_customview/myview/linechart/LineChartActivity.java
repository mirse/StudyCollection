package com.wdz.module_customview.myview.linechart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstant.ACTIVITY_LINE_CHART)
public class LineChartActivity extends AppCompatActivity {
    @BindView(R2.id.LineChartView)
    LineChartView lineChartView;
    @BindView(R2.id.bt_refresh)
    Button btnRefresh;
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

        for (int i=0;i<11;i++){
            xList.add("day"+i);
        }

        for (int i=0;i<11;i++){
            LineChartView.Point point = new LineChartView.Point();
            point.setxValue("day"+i);
            point.setyValue("0");
            point.setErrorPoint(true);
            datas.add(point);

        }
        lineChartView.setXYDataList(xList,yList);
        lineChartView.setDataList(datas);




    }
    @OnClick(R2.id.bt_refresh)
    public void onClick(View view){
        if (view.getId() == R.id.bt_refresh){
            yList.clear();
            xList.clear();
            datas.clear();
            for (int i=0;i<9;i++){
                yList.add(i*100);
            }

            for (int i=0;i<30;i++){
                xList.add("day"+i);
            }

            for (int i=0;i<30;i++){
                LineChartView.Point point = new LineChartView.Point();
                point.setxValue("day"+i);
                point.setyValue("100");
                if (i%7==0){
                    point.setErrorPoint(false);
                }
                else{
                    point.setErrorPoint(true);
                }

                datas.add(point);

            }
            lineChartView.setXYDataList(xList,yList);
            lineChartView.setDataList(datas);
        }

    }
}
