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
import com.example.dezhiwang.studycollection.Activity.DatePickerActivity;
import com.example.dezhiwang.studycollection.Activity.DrawableIconActivity;
import com.example.dezhiwang.studycollection.Activity.DrawableIconIVActivity;
import com.example.dezhiwang.studycollection.Activity.DrawableWrapperActivity;
import com.example.dezhiwang.studycollection.Activity.LetterIndexActivity;
import com.example.dezhiwang.studycollection.Activity.PageActivity;
import com.example.dezhiwang.studycollection.Activity.SurfaceViewActivity;
import com.example.dezhiwang.studycollection.Activity.TabViewActivity;
import com.example.dezhiwang.studycollection.IndicatorView.IndicatorActivity;
import com.example.dezhiwang.studycollection.Line4.LayoutInflater.LayoutInflaterActivity;
import com.example.dezhiwang.studycollection.Mvp.LoginTest.MvpActivity;
import com.example.dezhiwang.studycollection.Mvp.Test1.DataActivity;
import com.example.dezhiwang.studycollection.Mvp.WriteAndRead.SavaActivity;
import com.example.dezhiwang.studycollection.RecyclePager.ImageRecyclerActivity;
import com.example.dezhiwang.studycollection.RecyclePager.FragmentPagerActivity;
import com.example.dezhiwang.studycollection.RecyclerView.RecyclerViewActivity;

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
        String[][] array={{"自定义View","argb圆","rgbRing圆","hsv圆","SurfaceView","翻页效果","TabView"},{"角标效果","Group实现","View实现","ImageV实现","指示器","fragment循环","viewpage循环"},{"字母索引","列表索引","Login-mvp","mvp_test","save_test","选择器","recyclerView"},{"视图","LayoutInflater","","","","",""}};
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
                        startActivity(new Intent(this, SurfaceViewActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, PageActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, TabViewActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this,DrawableWrapperActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this,DrawableIconActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this,DrawableIconIVActivity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, IndicatorActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, FragmentPagerActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, ImageRecyclerActivity.class));
                        break;
                    default:
                        break;
                }
            case 2:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this,LetterIndexActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this,MvpActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this,DataActivity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, SavaActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, DatePickerActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, RecyclerViewActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 3:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, LayoutInflaterActivity.class));
                        break;
                    case R.id.button2:
                  //      startActivity(new Intent(this,MvpActivity.class));
                        break;
                    case R.id.button3:
                  //      startActivity(new Intent(this,DataActivity.class));
                        break;
                    case R.id.button4:
                  //      startActivity(new Intent(this, SavaActivity.class));
                        break;
                    case R.id.button5:
                  //      startActivity(new Intent(this, DatePickerActivity.class));
                        break;
                    case R.id.button6:
                  //      startActivity(new Intent(this, RecyclerViewActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;

        }
    }
}
