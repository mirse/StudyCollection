package com.wdz.module_basis.widget.recyclerview.snaphelper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdz.module_basis.R;

import com.wdz.module_basis.widget.recyclerview.universal.BaseAdapter;
import com.wdz.module_basis.widget.recyclerview.universal.base.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends SingleTypeAdapter<PageRecyclerView.Page> {
    private static final String TAG = "SnapAdapter";
    List<PageRecyclerView.Page> datas = new ArrayList<>();
    private OnClickListener onClickListener;
    private Context context;
    private View mView;
    /**
     * 行数
     */
    private int verticalCount = 2;
    /**
     * 列数
     */
    private int horizontalCount = 4;

    private int pageTotalCount = verticalCount*horizontalCount;

    private List<ViewGroup> llList = new ArrayList<>();


    public PageAdapter( Context context,List<PageRecyclerView.Page> datas) {
        super(context,datas);
        this.datas = datas;
        this.context = context;
    }

    /**
     * 设置结构
     * @param verticalCount 行数
     * @param horizontalCount 列数
     */
    public void setGridCount(int verticalCount,int horizontalCount){
        this.verticalCount = verticalCount;
        this.horizontalCount = horizontalCount;
    }

    @Override
    public void bindData(BaseViewHolder holder, PageRecyclerView.Page data, final int position) {
        LinearLayout llRoot = (LinearLayout) holder.getView(R.id.ll_root);
        llList.clear();
        llRoot.removeAllViews();
        for (int i=0;i<verticalCount;i++){
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,1));
            llRoot.addView(linearLayout);
            llList.add(linearLayout);
        }
        for (int i = 0; i < llList.size(); i++) {
            ViewGroup linearLayout = llList.get(i);
            linearLayout.removeAllViews();
            for (int j = 0; j <horizontalCount; j++) {
                FrameLayout frameLayout = new FrameLayout(context);
                frameLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.MATCH_PARENT,1));
                linearLayout.addView(frameLayout);
            }
        }


        for (int i = 0; i <data.itemList.size() ; i++) {
            final Object o = data.itemList.get(i);
            final int i1 = i;
            LinearLayout linearLayout = (LinearLayout) llRoot.getChildAt(i / horizontalCount);
            FrameLayout frameLayout = (FrameLayout) linearLayout.getChildAt(i % horizontalCount);
            frameLayout.removeAllViews();
            mView = View.inflate(context, R.layout.item_snap_in, null);
            frameLayout.addView(mView);
            TextView tv = mView.findViewById(R.id.tv);
            SnapHelperActivity.Item  item = (SnapHelperActivity.Item) data.itemList.get(i);
            Log.i(TAG, "convert: size:"+data.itemList.size()+"toString:"+data.itemList.toString());
            tv.setText(item.getName());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onClick(o,(pageTotalCount*position+i1));
                    }

                }
            });

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_snap;
    }

    @Override
    public int getHeadLayoutId() {
        return 0;
    }

    @Override
    public int getEmptyLayoutId() {
        return 0;
    }

    public interface OnClickListener{
        void onClick(Object o,int position);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }






}
