package com.wdz.studycollection.recyclerview.snaphelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapHelperActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_helper);
        ButterKnife.bind(this);
        initData();
        SnapAdapter snapAdapter = new SnapAdapter(mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(snapAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    private void initData() {
        for (int i=0;i<20;i++){
            mList.add(String.valueOf(i));
        }
    }
}
