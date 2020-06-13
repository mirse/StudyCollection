package com.wdz.studycollection.recyclerview.snaphelper;

import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.recyclerview.universal.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class SnapAdapter extends BaseAdapter<SnapHelperActivity.Page> {
    private static final String TAG = "SnapAdapter";
    List<SnapHelperActivity.Page> datas = new ArrayList<>();
    private OnClickListener onClickListener;
    public SnapAdapter(List<SnapHelperActivity.Page> datas) {
        super(datas);
        this.datas = datas;
        //Log.i(TAG, "SnapAdapter: "+datas.toString());
    }

    public void refreshStatus(List<SnapHelperActivity.Page> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_snap;
    }

    @Override
    public void convert(VH vh, SnapHelperActivity.Page data, int position) {
        List<View> views = new ArrayList<>();
        views.add(vh.getView(R.id.stub1));
        views.add(vh.getView(R.id.stub2));
        views.add(vh.getView(R.id.stub3));
        views.add(vh.getView(R.id.stub4));
        views.add(vh.getView(R.id.stub5));
        views.add(vh.getView(R.id.stub6));
        views.add(vh.getView(R.id.stub7));
        views.add(vh.getView(R.id.stub8));


        for (int i = 0; i <data.itemList.size() ; i++) {
            int i1 = i;
            ViewStub view = (ViewStub) views.get(i);
            if (view!=null){

            }
            View view1 = view.inflate();
            TextView tv = view1.findViewById(R.id.tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onClick((8*position+i1));
                    }

                }
            });
            Log.i(TAG, "convert: "+data.itemList.toString());
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
