package com.wdz.studycollection.recyclerview.delete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteRecyclerViewActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> mList;
    private DeleteAdapter deleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_recycler_view);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        deleteAdapter = new DeleteAdapter(this, mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(deleteAdapter);


    }
    @OnClick({R.id.add_item,R.id.delete_item})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.add_item:
                deleteAdapter.addData("");
                break;
            case R.id.delete_item:
                deleteAdapter.deleteData("");
                break;
            default:
                break;
        }
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i=0;i<1;i++){
            mList.add(String.valueOf(i));
        }
        //mList.clear();
    }
}