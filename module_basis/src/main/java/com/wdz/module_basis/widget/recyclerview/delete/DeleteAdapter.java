package com.wdz.module_basis.widget.recyclerview.delete;

import android.content.Context;

import com.wdz.module_basis.R;

import com.wdz.module_basis.widget.recyclerview.universal.BaseRecyclerViewAdapter;


import java.util.List;

public class DeleteAdapter extends BaseRecyclerViewAdapter<String> {
    public DeleteAdapter(Context context, List<String> list) {
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


}
