package com.example.dezhiwang.studycollection;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dezhiwang.studycollection.Activity.ColorPicker1Activity;
import com.example.dezhiwang.studycollection.Activity.ColorPicker2Activity;
import com.example.dezhiwang.studycollection.Activity.ColorPickerActivity;
import com.example.dezhiwang.studycollection.MyView.ColorPickerHSV;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {

    private RecyclerView mRecycleView;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.hide();
        }
        initMain();
    }

    private void initMain() {
        mRecycleView = findViewById(R.id.rv);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(this);
        mainAdapter.setOnItemClickListener(this);
        initData();
        mRecycleView.setAdapter(mainAdapter);
    }

    private void initData() {
        List<MainBean> list = new ArrayList<>();
        String[][] array={{"自定义View","argb圆","rgbRing圆","hsv圆","测试上传","",""},{"测试用例","","","","","",""}};
        for (String[] mArray:array) {
            list.add(new MainBean(mArray[0],mArray[1],mArray[2],mArray[3],mArray[4],mArray[5],mArray[6]));
        }
        mainAdapter.setList(list);

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position){
            case 0:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this,ColorPickerActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this,ColorPicker1Activity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this,ColorPicker2Activity.class));
                        break;
                    case R.id.button4:
                        break;
                    case R.id.button5:
                        break;
                    case R.id.button6:
                        break;
                }
                break;
            case 1:
                break;
        }
    }
}
