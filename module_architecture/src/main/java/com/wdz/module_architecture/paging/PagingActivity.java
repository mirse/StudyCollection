package com.wdz.module_architecture.paging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_architecture.R;
import com.wdz.module_architecture.R2;
import com.wdz.module_architecture.paging.room.User;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConstant.ACTIVITY_PAGING)
public class PagingActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R2.id.bt_add)
    Button btnAdd;

    private List<User> mUsers = new ArrayList<>();
    private PagingAdapter pagingAdapter;
    private PagingViewModel pagingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging);
        ButterKnife.bind(this);
        initView();
        pagingViewModel = new ViewModelProvider(this).get(PagingViewModel.class);
        pagingViewModel.getUser(this).observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i(TAG, "onChanged: "+users.size());
                mUsers = users;
                pagingAdapter.submitList((PagedList<User>) mUsers);
            }
        });


    }

    private void initView() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagingViewModel.insertUsers(mUsers.size(),getBaseContext());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pagingAdapter = new PagingAdapter(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.userId == newItem.userId;
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.userName.equals(newItem.userName);
            }
        }, mUsers, this);
        recyclerView.setAdapter(pagingAdapter);
    }
}
