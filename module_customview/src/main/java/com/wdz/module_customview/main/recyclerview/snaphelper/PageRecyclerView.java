package com.wdz.module_customview.main.recyclerview.snaphelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.wdz.module_customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wdz
 */
public class PageRecyclerView extends ConstraintLayout {

    private RecyclerView rv;
    private LinearLayout indicator;
    private ImageView[] imageViews;
    private Context context;
    private LinearLayoutManager layoutManager;
    public PageRecyclerView(Context context) {
        this(context,null);
    }

    public PageRecyclerView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PageRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }
    public void setAdapter(Context context, LinearLayoutManager layoutManager, final List<SnapHelperActivity.Item> datas){
        this.context = context;
        this.layoutManager = layoutManager;
        rv.setLayoutManager(layoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv);
        final SnapAdapter snapAdapter = new SnapAdapter(resetData(datas),context);
        rv.setAdapter(snapAdapter);
        snapAdapter.setOnClickListener(new SnapAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                datas.remove(datas.get(position));
                snapAdapter.refreshStatus(resetData(datas));
            }
        });
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.item_my_recyclerview, this);
        rv = view.findViewById(R.id.recyclerView);
        indicator = view.findViewById(R.id.ll_indicator);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (firstCompletelyVisibleItemPosition == RecyclerView.NO_POSITION){
                    return;
                }
                for (int i = 0; i < imageViews.length; i++) {
                    imageViews[firstCompletelyVisibleItemPosition].setBackgroundResource(android.R.drawable.presence_online);
                    if (firstCompletelyVisibleItemPosition != i) {
                        imageViews[i].setBackgroundResource(android.R.drawable.presence_invisible);
                    }
                }
            }

        });
    }
    private List<Page> mPageList = new ArrayList<>();
    private List<Page> resetData(List<SnapHelperActivity.Item> mList) {
        mPageList.clear();
        int page = mList.size()/8;
        if (mList.size()%8>0){
            page++;
        }
        initPoint(page);
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
    private void initPoint(int page) {
        imageViews = new ImageView[page];
        indicator.removeAllViews();
        for (int i=0;i<page;i++){
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(dip2px(context,7), dip2px(context,7)));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getLayoutParams());
            lp.setMargins(dip2px(context,3),0,dip2px(context,3),0);
            imageView.setLayoutParams(lp);

            //初始化第一个page页面的图片的原点为选中状态
            if (i == 0) {
                //表示当前图片
                imageView.setBackgroundResource(android.R.drawable.presence_online);
            } else {
                imageView.setBackgroundResource(android.R.drawable.presence_invisible);
            }

            imageViews[i] =imageView;
            indicator.addView(imageView);
        }


    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue 要转换的dp值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    public class Page{
        public Page(List<SnapHelperActivity.Item> itemList) {
            this.itemList = itemList;
        }

        public List<SnapHelperActivity.Item> itemList;

        public List<SnapHelperActivity.Item> getItemList() {
            return itemList;
        }

        public void setItemList(List<SnapHelperActivity.Item> itemList) {
            this.itemList = itemList;
        }

        @Override
        public String toString() {
            return "Page{" +
                    "itemList=" + itemList +
                    '}';
        }
    }

}
