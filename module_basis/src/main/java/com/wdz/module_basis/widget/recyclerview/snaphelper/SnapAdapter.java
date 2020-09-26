package com.wdz.module_basis.widget.recyclerview.snaphelper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wdz.module_basis.R;

import com.wdz.module_basis.widget.recyclerview.universal.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class SnapAdapter extends BaseAdapter<PageRecyclerView.Page> {
    private static final String TAG = "SnapAdapter";
    List<PageRecyclerView.Page> datas = new ArrayList<>();
    private OnClickListener onClickListener;
    private Context context;
    public SnapAdapter(List<PageRecyclerView.Page> datas, Context context) {
        super(datas);
        this.datas = datas;
        this.context = context;
        //Log.i(TAG, "SnapAdapter: "+datas.toString());
    }

    public void refreshStatus(List<PageRecyclerView.Page> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_snap;
    }

    @Override
    public void convert(VH vh, PageRecyclerView.Page data, final int position) {
        List<FrameLayout> views = new ArrayList<>();
        FrameLayout view1 = vh.getView(R.id.stub1);
        FrameLayout view2 = vh.getView(R.id.stub2);
        FrameLayout view3 = vh.getView(R.id.stub3);
        FrameLayout view4 = vh.getView(R.id.stub4);
        FrameLayout view5 = vh.getView(R.id.stub5);
        FrameLayout view6 = vh.getView(R.id.stub6);
        FrameLayout view7 = vh.getView(R.id.stub7);
        FrameLayout view8 = vh.getView(R.id.stub8);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        views.add(view7);
        views.add(view8);
        for (FrameLayout frameLayout:views) {
            frameLayout.removeAllViews();
        }


        for (int i = 0; i <data.itemList.size() ; i++) {
            final int i1 = i;
            FrameLayout view =views.get(i);
            View mView = View.inflate(context, R.layout.item_snap_in, null);
            view.addView(mView);
            TextView tv = mView.findViewById(R.id.tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onClick((8*position+i1));
                    }

                }
            });

//            if (data.itemList.get(i).isSelect()){
//                tv.setSelected(true);
//            }
//            else{
//                tv.setSelected(false);
//            }

            Log.i(TAG, "convert: size:"+data.itemList.size()+"toString:"+data.itemList.toString());
            tv.setText(data.itemList.get(i).getName());
        }


    }
    public interface OnClickListener{
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }






}
