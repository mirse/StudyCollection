package com.wdz.studycollection.recyclerview.snaphelper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.recyclerview.universal.BaseAdapter;

import java.util.List;

public class SnapAdapter extends BaseAdapter<String> {
    public SnapAdapter(List datas) {
        super(datas);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_snap;
    }

    @Override
    public void convert(VH vh, String data, int position) {

    }




}
