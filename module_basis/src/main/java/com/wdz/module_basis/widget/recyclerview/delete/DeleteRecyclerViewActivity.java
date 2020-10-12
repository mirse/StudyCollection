package com.wdz.module_basis.widget.recyclerview.delete;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;
import com.wdz.module_basis.widget.recyclerview.universal.ListAdapter;
import com.wdz.module_basis.widget.recyclerview.universal.base.BaseRecyclerViewAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteRecyclerViewActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> mList;
    private DeleteAdapter deleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_basis_activity_delete_recycler_view);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {

        deleteAdapter = new DeleteAdapter(this,mList);
//        ListView listView = new ListView();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(deleteAdapter);
        deleteAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClickNormal(Object object, int position) {
                Log.i(TAG, "onClickNormal: "+position+" "+object);
            }

        });

    }
    @OnClick({R2.id.add_item,R2.id.delete_item})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.add_item) {
            mList.add("");
            deleteAdapter.notifyItemInserted(mList.size());
            //deleteAdapter.addData("");
        } else if (id == R.id.delete_item) {
            mList.remove(mList.size()-1);
            deleteAdapter.notifyItemRemoved(mList.size()-1);
            //deleteAdapter.deleteData("");
        }
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("小于10");
        for (int i=0;i<10;i++){
            mList.add(String.valueOf(i));

        }
        mList.add("大于10");
        for (int i=0;i<10;i++){
            mList.add("1"+i);
        }
        //mList.clear();
    }
}
