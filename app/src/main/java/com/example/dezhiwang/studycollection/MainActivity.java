package com.example.dezhiwang.studycollection;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dezhiwang.studycollection.activity.ColorPicker1Activity;
import com.example.dezhiwang.studycollection.activity.ColorPicker2Activity;
import com.example.dezhiwang.studycollection.activity.ColorPickerActivity;
import com.example.dezhiwang.studycollection.activity.DatePickerActivity;
import com.example.dezhiwang.studycollection.activity.DrawableIconActivity;
import com.example.dezhiwang.studycollection.activity.DrawableIconIVActivity;
import com.example.dezhiwang.studycollection.activity.DrawableWrapperActivity;
import com.example.dezhiwang.studycollection.activity.LetterIndexActivity;
import com.example.dezhiwang.studycollection.activity.PageActivity;
import com.example.dezhiwang.studycollection.activity.SurfaceViewActivity;
import com.example.dezhiwang.studycollection.activity.TabViewActivity;
import com.example.dezhiwang.studycollection.anim.AnimDemoActivity;
import com.example.dezhiwang.studycollection.asynctask.AsyncTaskActivity;
import com.example.dezhiwang.studycollection.datasave.room.RoomTestActivity;
import com.example.dezhiwang.studycollection.datasave.SharedPreferenceActivity;
import com.example.dezhiwang.studycollection.fragment.FragmentActivity;
import com.example.dezhiwang.studycollection.fragment.revolve.FixProblemsActivity;
import com.example.dezhiwang.studycollection.fragment.revolve.SavedInstanceStateUsingActivity;
import com.example.dezhiwang.studycollection.fragment.viewpager.VpAndFragActivity;
import com.example.dezhiwang.studycollection.handler.HandlerDemoActivity;
import com.example.dezhiwang.studycollection.indicatorview.IndicatorActivity;
import com.example.dezhiwang.studycollection.myview.LayoutInflaterActivity;
import com.example.dezhiwang.studycollection.mvp.logintest.MvpActivity;
import com.example.dezhiwang.studycollection.mvp.test1.DataActivity;
import com.example.dezhiwang.studycollection.mvp.writeandread.SavaActivity;
import com.example.dezhiwang.studycollection.network.SocketClientActivity;
import com.example.dezhiwang.studycollection.recyclepager.ImageRecyclerActivity;
import com.example.dezhiwang.studycollection.recyclepager.FragmentPagerActivity;
import com.example.dezhiwang.studycollection.recyclerview.grid.GridViewActivity;
import com.example.dezhiwang.studycollection.recyclerview.RecyclerViewActivity;
import com.example.dezhiwang.studycollection.viewbase.CoordinateSystemActivity;
import com.example.dezhiwang.studycollection.viewbase.floating.FloatingViewActivity;
import com.example.dezhiwang.studycollection.viewbase.scroller.ScrollerActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {

    private RecyclerView mRecycleView;
    private MainAdapter mainAdapter;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar!=null){
            supportActionBar.hide();
        }
        initMain();
        Toast.makeText(this,"瀑布流拖拽problem",Toast.LENGTH_SHORT).show();
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
        String[][] array={
                {"1自定义View","argb圆","rgbRing圆","hsv圆","SurfaceView","翻页效果","TabView"},
                {"角标效果","Group实现","View实现","ImageV实现","指示器","fragment循环","viewpage循环"},
                {"字母索引","列表索引","Login-mvp","mvp_test","save_test","选择器","recyclerView"},
                {"视图","LayoutInflater","瀑布流","界面旋转修复","Fragment","与viewpager","界面旋转"},
                {"网络通信","Socket通信","AsyncTask","Handler","","",""},
                {"数据存储","Sp","Room","","","",""},
                {"View合集","坐标系","悬浮窗","Scroller","","",""},
                {"动画合集","属性动画","","","","",""}
        };

        for (String[] mArray:array) {
            list.add(new MainBean(mArray[0],mArray[1],mArray[2],mArray[3],mArray[4],mArray[5],mArray[6]));
        }
        mainAdapter.setList(list);

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG,"position:"+position);
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
                break;
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
                        startActivity(new Intent(this, GridViewActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this, FixProblemsActivity.class));
                        break;
                    case R.id.button4:
                        startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, SocketClientActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, AsyncTaskActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this, HandlerDemoActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 5:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, SharedPreferenceActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, RoomTestActivity.class));
                        break;
                    case R.id.button3:
                        //startActivity(new Intent(this, FixProblemsActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 6:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, CoordinateSystemActivity.class));
                        break;
                    case R.id.button2:
                        startActivity(new Intent(this, FloatingViewActivity.class));
                        break;
                    case R.id.button3:
                        startActivity(new Intent(this, ScrollerActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
                        break;
                    default:
                        break;
                }
                break;
            case 7:
                switch (view.getId()){
                    case R.id.button:
                        startActivity(new Intent(this, AnimDemoActivity.class));
                        break;
                    case R.id.button2:
                        //startActivity(new Intent(this, RoomTestActivity.class));
                        break;
                    case R.id.button3:
                        //startActivity(new Intent(this, ScrollerActivity.class));
                        break;
                    case R.id.button4:
                        //startActivity(new Intent(this, FragmentActivity.class));
                        break;
                    case R.id.button5:
                        //startActivity(new Intent(this, VpAndFragActivity.class));
                        break;
                    case R.id.button6:
                        //startActivity(new Intent(this, SavedInstanceStateUsingActivity.class));
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
