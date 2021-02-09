package com.wdz.module_basis.widget.recyclerview.listview;

import androidx.appcompat.app.AppCompatActivity;

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

public class ListViewActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.listview)
    ListView listView;
    @BindView(R2.id.bt_modify)
    Button btnModify;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        final List<MyData> mArrayList = new ArrayList<>();
        for (int i=0;i<20;i++){
            MyData myData = new MyData();
            myData.s = "这是第"+i+"个View";
            myData.isSelect = false;
            mArrayList.add(myData);
        }
        listViewAdapter = new ListViewAdapter(this,R.layout.list_view_item, mArrayList);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: "+position);
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyData myData = mArrayList.get(1);
                myData.isSelect = !myData.isSelect;

                listViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
