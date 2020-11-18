package com.wdz.module_basis.widget.recyclerview.multi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;
import com.wdz.module_basis.widget.recyclerview.universal.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiActivity extends AppCompatActivity {
    @BindView(R2.id.rv_multi)
    RecyclerView rvMulti;
    @BindView(R2.id.bt_add)
    Button btnAdd;
    private List<String> mList = new ArrayList<>();
    private MultiAdapter multiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);
        ButterKnife.bind(this);

        multiAdapter = new MultiAdapter(this, mList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvMulti.setLayoutManager(linearLayoutManager);
        rvMulti.setAdapter(multiAdapter);
        multiAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClickNormal(Object object, int position) {
                boolean itemChecked = multiAdapter.getItemChecked(position);
                multiAdapter.setItemChecked(position,!itemChecked);
                multiAdapter.notifyDataSetChanged();
            }
        });

        for (int i = 0; i < 10; i++) {
            mList.add(String.valueOf(i));
        }

    }
    @OnClick(R2.id.bt_add)
    public void onClick(View view){
        if (R.id.bt_add == view.getId()){
            mList.add("new");
            multiAdapter.notifyDataSetChanged();
        }
    }
}
