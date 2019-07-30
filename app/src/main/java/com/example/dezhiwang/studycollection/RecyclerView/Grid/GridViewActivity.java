package com.example.dezhiwang.studycollection.RecyclerView.Grid;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.dezhiwang.studycollection.R;
import com.example.dezhiwang.studycollection.RecyclerView.MyAdapter;
import com.example.dezhiwang.studycollection.RecyclerView.StaggerGrid.MDStaggeredRvAdapter;
import com.example.dezhiwang.studycollection.RecyclerView.StaggerGrid.MDStaggeredRvDividerDecotation;
import com.example.dezhiwang.studycollection.RecyclerView.universal.BaseAdapter;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {

    private GridLayoutManager gridLayoutManager;
    private MyAdapter myAdapter;
    private MDStaggeredRvAdapter mdStaggeredRvAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyclerView mRv;
    private int actionStateNow;
    private ArrayList<String> mDatas;
    private int fromPosition;
    private int endPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        initData();
        initUI();
    }

    private void initData() {
        gridLayoutManager = new GridLayoutManager(this, 4, OrientationHelper.VERTICAL, false);
        myAdapter = new MyAdapter(getData());

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        mdStaggeredRvAdapter = new MDStaggeredRvAdapter(getData());
        mdStaggeredRvAdapter.setOnClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getBaseContext(),"点击第"+position+"个",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private ArrayList<String> getData() {
        mDatas = new ArrayList<>();
        String temp = "item";
        for (int i = 0;i<20;i++){
            mDatas.add(temp+i);
        }
        return mDatas;
    }

    private void initUI() {
        mRv = findViewById(R.id.recyclerView);
        //标准瀑布流
//        mRv.setLayoutManager(gridLayoutManager);
//        mRv.setAdapter(myAdapter);
//        mRv.addItemDecoration(new GridDividerDecoration(this));

        //宽高不一的瀑布流
        mRv.setLayoutManager(staggeredGridLayoutManager);
        mRv.setAdapter(mdStaggeredRvAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);
    }
    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback(){

        //支持长按拖动
        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        //支持滑动
        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
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
            return makeMovementFlags(ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT|ItemTouchHelper.UP|ItemTouchHelper.DOWN,0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {

            mdStaggeredRvAdapter.move(viewHolder.getAdapterPosition(),viewHolder1.getAdapterPosition());

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            //myAdapter.deleteItem(viewHolder.getAdapterPosition());
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            viewHolder.itemView.setBackgroundResource(R.color.grey_d);
            viewHolder.itemView.setAlpha(1.0f);
            super.clearView(recyclerView, viewHolder);
        }
    };
}
