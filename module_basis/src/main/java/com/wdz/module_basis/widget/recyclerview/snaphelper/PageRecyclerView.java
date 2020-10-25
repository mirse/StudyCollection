package com.wdz.module_basis.widget.recyclerview.snaphelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.wdz.module_basis.R;


import java.util.ArrayList;
import java.util.List;

/**
 * pageRecyclerView 2*4结构
 * @author wdz
 */
public class PageRecyclerView extends ConstraintLayout {

    private RecyclerView rv;
    private LinearLayout indicator;
    private ImageView[] imageViews;
    private Context context;
    private List<Page> mPageList = new ArrayList<>();
    /**
     * 当前指示器所在位置默认0
     */
    private int pointIndex = 0;
    private LinearLayoutManager layoutManager;
    private PageAdapter pageAdapter;
    /**
     * 行数
     */
    private int verticalCount = 2;
    /**
     * 列数
     */
    private int horizontalCount = 4;

    private int pageTotalCount = verticalCount*horizontalCount;

    private OnClickListener onClickListener;


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

    /**
     * 初始化ui
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.item_my_recyclerview, this);
        rv = view.findViewById(R.id.recyclerView);
        indicator = view.findViewById(R.id.ll_indicator);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager!=null){
                    int firstCompletelyVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if (firstCompletelyVisibleItemPosition == RecyclerView.NO_POSITION){
                        return;
                    }
                    pointIndex = firstCompletelyVisibleItemPosition;
                    for (int i = 0; i < imageViews.length; i++) {
                        //可能出现空清空，当指示器点<2时
                        if (imageViews[firstCompletelyVisibleItemPosition]!=null){
                            imageViews[firstCompletelyVisibleItemPosition].setBackgroundResource(android.R.drawable.presence_online);
                        }

                        if (firstCompletelyVisibleItemPosition != i) {
                            if (imageViews[i]!=null) {
                                imageViews[i].setBackgroundResource(android.R.drawable.presence_invisible);
                            }
                        }
                    }
                }


            }

        });
    }

    /**
     * 设置结构
     * @param verticalCount 行数
     * @param horizontalCount 列数
     */
    public void setGridCount(int verticalCount,int horizontalCount){
        this.verticalCount = verticalCount;
        this.horizontalCount = horizontalCount;
        this.pageTotalCount = verticalCount*horizontalCount;
    }

    /**
     * 设置数据源
     * @param context
     * @param data 数据源
     * @param isCanSelectDevice 是否是从房间选择设备功能
     */
    public void setDataSource(Context context,List<SnapHelperActivity.Item> data, boolean isCanSelectDevice){
        this.context = context;
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        //实现滑动后最后停留在某页正中间
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        //防止出现java.lang.IllegalStateException An instance of OnFlingListener already set.
        rv.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(rv);
        pageAdapter = new PageAdapter(context,resetData(data,true));
        pageAdapter.setGridCount(verticalCount,horizontalCount);
        rv.setAdapter(pageAdapter);
        pageAdapter.setOnClickListener(new PageAdapter.OnClickListener() {
            @Override
            public void onClick(Object o, int position) {
                SnapHelperActivity.Item item = (SnapHelperActivity.Item) o;
                if (onClickListener!=null){
                    onClickListener.onClick(item,position);
                }
            }

        });
    }

    /**
     * 刷新数据源
     * @param list
     */
    public void refreshData(List<SnapHelperActivity.Item> list){
        mPageList = resetData(list, true);
        pageAdapter.notifyDataSetChanged();
    }



    /**
     * 整理数据源 每8个为一个item
     * @param mList
     * @param isInitPoint 是否初始化指示器
     * @return
     */
    private List<Page> resetData(List<SnapHelperActivity.Item> mList,boolean isInitPoint) {
        mPageList.clear();
        int pageCount = mList.size()/pageTotalCount;
        if (mList.size()%pageTotalCount>0){
            pageCount++;
        }
        if (isInitPoint){
            initPoint(pageCount);
        }

        for (int i=0;i<pageCount;i++){

            int fromIndex = i*pageTotalCount;
            int toIndex = (i+1)*pageTotalCount;
            if (toIndex>mList.size()){
                toIndex = mList.size();
            }
            Page page = new Page(new ArrayList<>(mList.subList(fromIndex,toIndex)));
            mPageList.add(page);
        }
        return mPageList;
    }

    /**
     * 初始化底部指示器
     * @param page 当前recyclerView页面数
     */
    private void initPoint(int page) {
        imageViews = new ImageView[page];
        indicator.removeAllViews();
        //页数大于2才显示指示器
        if (page>1) {
            for (int i = 0; i < page; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(dip2px(context, 7), dip2px(context, 7)));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageView.getLayoutParams());
                lp.setMargins(dip2px(context, 3), 0, dip2px(context, 3), 0);
                imageView.setLayoutParams(lp);

                //初始化第一个page页面的图片的原点为选中状态
                if (i == pointIndex) {
                    //表示当前图片
                    imageView.setBackgroundResource(android.R.drawable.presence_online);
                } else {
                    imageView.setBackgroundResource(android.R.drawable.presence_invisible);
                }

                imageViews[i] = imageView;
                indicator.addView(imageView);
            }
        }

    }


    public interface OnClickListener{
        void onClick(SnapHelperActivity.Item item,int position);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
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

    public class Page<T>{
        public Page(List<T> itemList) {
            this.itemList = itemList;
        }

        public List<T> itemList;

        public List<T> getItemList() {
            return itemList;
        }

        public void setItemList(List<T> itemList) {
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
