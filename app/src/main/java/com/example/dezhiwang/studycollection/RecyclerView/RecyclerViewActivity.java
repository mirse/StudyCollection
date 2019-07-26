package com.example.dezhiwang.studycollection.RecyclerView;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
