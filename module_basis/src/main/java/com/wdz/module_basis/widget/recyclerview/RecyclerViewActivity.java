package com.wdz.module_basis.widget.recyclerview;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_basis.R;

import com.wdz.module_basis.R2;
import com.wdz.module_basis.widget.recyclerview.delete.DeleteRecyclerViewActivity;
import com.wdz.module_basis.widget.recyclerview.snaphelper.SnapHelperActivity;
import com.wdz.module_basis.widget.recyclerview.updownrecyclerview.UpDownRecyclerViewActivity;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConstant.ACTIVITY_RECYCLER_VIEW)
public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.swipeRefreshLayout)
    PullRefreshView swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.module_basis_activity_recycler_view);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        Button mBtnAdd = findViewById(R.id.bt_add);
        Button mBtnDel = findViewById(R.id.bt_delete);
        Button mBtnRecyclerView = findViewById(R.id.bt_recyclerview);
        Button mBtnBanner = findViewById(R.id.banner_recycler);
        Button mBtnDelete = findViewById(R.id.delete_recycler);
        //mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView = swipeRefreshLayout.getRecyclerView();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this,LinearLayoutManager.HORIZONTAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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
                 myAdapter.deleteItem(0);
                 mLayoutManager.scrollToPosition(0);
             }
         });
        mBtnRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecyclerViewActivity.this, UpDownRecyclerViewActivity.class));
            }
        });
        mBtnBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecyclerViewActivity.this, SnapHelperActivity.class));
            }
        });
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecyclerViewActivity.this, DeleteRecyclerViewActivity.class));
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

//         swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//             @Override
//             public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                 swipeRefreshLayout.postDelayed(new Runnable() {
//                     @Override
//                     public void run() {
//                         swipeRefreshLayout.finishRefresh();
//                     }
//                 }, 500);
//             }
//         });
        swipeRefreshLayout.setOnRefreshListener(new PullRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /** 模拟网络请求 */
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.refreshComplete();
                    }
                }, 1000);
            }
        });


//         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//             @Override
//             public void onRefresh() {
//                 new Handler().postDelayed(new Runnable() {
//                     @Override
//                     public void run() {
//                         swipeRefreshLayout.setRefreshing(false);
//                     }
//                 },2000);
//             }
//         });
    }



    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback(){

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
            if(actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundResource(R.color.red_deep);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            float x = Math.abs(dX) + 0.5f;
            float width = viewHolder.itemView.getWidth();
            float alpha = 1f - x / width;
            viewHolder.itemView.setAlpha(alpha);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                    isCurrentlyActive);
        }

        @Override
        public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0,ItemTouchHelper.START|ItemTouchHelper.END);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            myAdapter.deleteItem(viewHolder.getAdapterPosition());
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            viewHolder.itemView.setBackgroundResource(R.color.colorAccent);
            viewHolder.itemView.setAlpha(1.0f);
            super.clearView(recyclerView, viewHolder);
        }
    };

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
