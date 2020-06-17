package com.wdz.studycollection.recyclerview.delete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteRecyclerViewActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_recycler_view);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        DeleteAdapter deleteAdapter = new DeleteAdapter(this, mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(deleteAdapter);

    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i=0;i<10;i++){
            mList.add(String.valueOf(i));
        }
        //mList.clear();
    }
}
