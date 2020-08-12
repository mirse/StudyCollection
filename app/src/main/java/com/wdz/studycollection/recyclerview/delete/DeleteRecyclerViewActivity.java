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
    private List<String> mList = new ArrayList<>();
    private DeleteAdapter deleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_recycler_view);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        deleteAdapter = new DeleteAdapter(this, mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(deleteAdapter);

    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i=0;i<5;i++){
            mList.add(String.valueOf(i));
        }
        deleteAdapter.refreshList(mList);
        //mList.clear();
    }
}
