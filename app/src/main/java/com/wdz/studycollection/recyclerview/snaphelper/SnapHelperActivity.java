package com.wdz.studycollection.recyclerview.snaphelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ImageView;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapHelperActivity extends AppCompatActivity {
    private static final String TAG = "SnapHelperActivity";
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.ll_indicator)
//    LinearLayout llIndicator;
    @BindView(R.id.myView)
PageRecyclerView myRecyclerView;
    private List<Item> mList = new ArrayList<>();
    private ImageView[] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_helper);
        ButterKnife.bind(this);
        initData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myRecyclerView.setAdapter(this,linearLayoutManager,mList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(snapAdapter);
//        PagerSnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                Log.i(TAG, "onScrolled: "+firstCompletelyVisibleItemPosition);
//                if (firstCompletelyVisibleItemPosition == RecyclerView.NO_POSITION){
//                    return;
//                }
//                for (int i = 0; i < imageViews.length; i++) {
//                    imageViews[firstCompletelyVisibleItemPosition].setBackgroundResource(android.R.drawable.presence_online);
//                    if (firstCompletelyVisibleItemPosition != i) {
//                        imageViews[i].setBackgroundResource(android.R.drawable.presence_invisible);
//                    }
//                }
//            }
//
//        });
//
//        snapAdapter.setOnClickListener(new SnapAdapter.OnClickListener() {
//            @Override
//            public void onClick(int position) {
//
//                mList.remove(mList.get(position));
//                snapAdapter.refreshStatus(resetData(mList));
////                for (Item item:mList) {
////                    item.setSelect(false);
////                }
////                mList.get(position).setSelect(true);
////                snapAdapter.refreshStatus(resetData(mList));
//                Log.i(TAG, "onClick: "+position);
//            }
//        });

    }

    private List<Page> mPageList = new ArrayList<>();
    private List<Page> resetData(List<Item> mList) {
        mPageList.clear();
        int page = mList.size()/8;
        if (mList.size()%8>0){
            page++;
        }
        //initPoint(page);
        for (int i=0;i<page;i++){

            int fromIndex = i*8;
            int toIndex = (i+1)*8;
            if (toIndex>mList.size()){
                toIndex = mList.size();
            }
            Page page1 = new Page(new ArrayList<>(mList.subList(fromIndex,toIndex)));
            mPageList.add(page1);
        }
        return mPageList;
    }

//    private void initPoint(int page) {
//        imageViews = new ImageView[page];
//        llIndicator.removeAllViews();
//        for (int i=0;i<page;i++){
//            ImageView imageView = new ImageView(this);
//            imageView.setBackgroundResource(android.R.drawable.presence_invisible);
//            imageViews[i] =imageView;
//            llIndicator.addView(imageView);
//        }
//
//
//    }

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
                    ", isSelect=" + isSelect +
                    '}';
        }
    }
}
