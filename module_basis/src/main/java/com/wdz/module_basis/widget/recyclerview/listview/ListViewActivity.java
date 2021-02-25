package com.wdz.module_basis.widget.recyclerview.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;
import com.wdz.module_basis.widget.recyclerview.bean.MyData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * listview item中包含seekBar会出现复用显示不出来的问题，解决方法 seekBar同级添加其余一种系统view或者使用recyclerview
 */
public class ListViewActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.listview)
    ListView listView;
    @BindView(R2.id.bt_modify)
    Button btnModify;
    private ListViewAdapter listViewAdapter;

//    @BindView(R2.id.control_part)
//    ConstraintLayout control_part;
//    @BindView(R2.id.control_part1)
//    ConstraintLayout control_part1;

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        final List<MyData> mArrayList = new ArrayList<>();
        for (int i=0;i<2;i++){
            MyData myData = new MyData();
            myData.s = "这是第"+i+"个View";
            myData.isSelect = false;
            mArrayList.add(myData);
        }
        listViewAdapter = new ListViewAdapter(this, mArrayList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        listView.setLayoutManager(linearLayoutManager);
        listView.setAdapter(listViewAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i(TAG, "onItemClick: "+position);
//            }
//        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData myData = mArrayList.get(1);
                myData.isSelect = !myData.isSelect;

                listViewAdapter.notifyDataSetChanged();
            }
        });
    }
//    @OnClick({R2.id.item_expand_icon,R2.id.item_expand_icon1})
//    public void onClick(View view){
//        if (view.getId() == R.id.item_expand_icon){
//            control_part.setVisibility(View.VISIBLE);
//        }
//        else if (view.getId() == R.id.item_expand_icon1){
//            control_part1.setVisibility(View.VISIBLE);
//        }
//    }
}
