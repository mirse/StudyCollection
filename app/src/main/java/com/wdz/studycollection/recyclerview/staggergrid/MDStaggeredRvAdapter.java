package com.wdz.studycollection.recyclerview.staggergrid;



import com.wdz.studycollection.R;
import com.wdz.studycollection.recyclerview.universal.BaseAdapter;

import java.util.ArrayList;

public class MDStaggeredRvAdapter extends BaseAdapter<String> {

    private ArrayList<String> mData;

    public MDStaggeredRvAdapter(ArrayList<String> data) {
        super(data);
        this.mData = data;
    }

    public void move(int fromPosition, int toPosition) {

        String prev = mData.remove(fromPosition);
        mData.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }


    @Override
    public int getLayoutId(int viewType) {
        int layout;
        if (viewType == 1){
            layout = R.layout.view_rv_staggered_item;
        }
        else{
            layout = R.layout.view_rv_staggered_item_two;
        }
        return layout;
    }

    @Override
    public void convert(VH vh, String data, int position) {
        vh.setText(R.id.item_tv,data);
        vh.addOnClickListener(position);
    }



}
