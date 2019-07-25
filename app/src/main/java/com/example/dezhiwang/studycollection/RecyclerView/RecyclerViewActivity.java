package com.example.dezhiwang.studycollection.RecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.R;

import java.util.ArrayList;

import butterknife.BindView;

public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initData();
        initView();
    }

    private void initView() {
        Button mBtnAdd = findViewById(R.id.bt_add);
        Button mBtnDel = findViewById(R.id.bt_delete);
        mRecyclerView = findViewById(R.id.recyclerView);
         mRecyclerView.setLayoutManager(mLayoutManager);
         mRecyclerView.setAdapter(myAdapter);
         mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this,LinearLayoutManager.HORIZONTAL));
         mRecyclerView.setItemAnimator(new DefaultItemAnimator());

         mBtnAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 myAdapter.addItem();
                 mLayoutManager.scrollToPosition(0);
             }
         });
         mBtnDel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 myAdapter.deleteItem();
                 mLayoutManager.scrollToPosition(0);
             }
         });

         myAdapter.setOnClickListener(new MyAdapter.onItemClickListener() {
             @Override
             public void onItemClick(View view, int position) {
                 Toast.makeText(getApplicationContext(),"点击第"+position+"item",Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onItemLongClick(View view, int position) {
                 Toast.makeText(getApplicationContext(),"长按第"+position+"item",Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myAdapter = new MyAdapter(getData());
    }

    private ArrayList<String> getData() {
        ArrayList<String> mDatas = new ArrayList<>();
        String temp = "item";
        for (int i = 0;i<20;i++){
            mDatas.add(temp+i);
        }
        return mDatas;
    }
}
