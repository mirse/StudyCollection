package com.wdz.studycollection.recyclerview.delete;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.recyclerview.universal.BaseRecyclerViewAdapter;

import java.util.List;

public class DeleteAdapter extends BaseRecyclerViewAdapter {
    public DeleteAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getHeaderLayoutId() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycler_item_delete;
    }

    @Override
    public int getEmptyLayoutId() {
        return R.layout.recycler_item_empty;
    }

    @Override
    public void bindData(BaseViewHolder holder, MyItem myItem, int position) {

    }

    @Override
    public void bindHeaderData(BaseViewHolder holder) {

    }

    @Override
    public boolean hasEmptyView() {
        return false;
    }

    @Override
    public boolean isHeaderOnlyLine() {
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
