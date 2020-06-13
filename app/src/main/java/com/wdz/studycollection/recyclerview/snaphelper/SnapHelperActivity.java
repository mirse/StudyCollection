package com.wdz.studycollection.recyclerview.snaphelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapHelperActivity extends AppCompatActivity {
    private static final String TAG = "SnapHelperActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<Item> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_helper);
        ButterKnife.bind(this);
        initData();
        resetData(mList);
        SnapAdapter snapAdapter = new SnapAdapter(mPageList);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(snapAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        snapAdapter.setOnClickListener(new SnapAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                for (Item item:mList) {
                    item.setSelect(false);
                }
                mList.get(position).setSelect(true);
                resetData(mList);
                snapAdapter.refreshStatus(mPageList);
                Log.i(TAG, "onClick: "+position);
            }
        });
    }

    private List<Page> mPageList = new ArrayList<>();
    private void resetData(List<Item> mList) {
        mPageList.clear();
        int page = mList.size()/8;
        if (mList.size()%8>0){
            page++;
        }
        for (int i=0;i<page;i++){

            int fromIndex = i*8;
            int toIndex = (i+1)*8;
            if (toIndex>mList.size()){
                toIndex = mList.size();
            }
            Page page1 = new Page(new ArrayList<>(mList.subList(fromIndex,toIndex)));
            mPageList.add(page1);
        }
    }

    private void initData() {
        for (int i=0;i<20;i++){
            mList.add(new Item(String.valueOf(i),false));
        }
    }
    public class Page{
        public Page(List<Item> itemList) {
            this.itemList = itemList;
        }

        public List<Item> itemList;

        public List<Item> getItemList() {
            return itemList;
        }

        public void setItemList(List<Item> itemList) {
            this.itemList = itemList;
        }

        @Override
        public String toString() {
            return "Page{" +
                    "itemList=" + itemList +
                    '}';
        }
    }
    public class Item{
        private String name;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Item(String name, boolean isSelect) {
            this.name = name;
            this.isSelect = isSelect;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
