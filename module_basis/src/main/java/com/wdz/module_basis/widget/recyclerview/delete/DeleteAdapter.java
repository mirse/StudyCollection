package com.wdz.module_basis.widget.recyclerview.delete;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wdz.module_basis.R;

import com.wdz.module_basis.widget.recyclerview.universal.base.MultiTypeAdapter;
import com.wdz.module_basis.widget.recyclerview.universal.base.SingleTypeAdapter;


import java.util.List;

public class DeleteAdapter extends SingleTypeAdapter<String> {
    public DeleteAdapter(Context context,List<String> list) {
        super(context,list);
    }
//
//    @Override
//    public int getTitleId() {
//        return R.layout.recycler_item_title;
//    }
//
//    @Override
//    public boolean isTitleOneLine() {
//        return true;
//    }
//
//    @Override
//    public boolean isTitleItem(int position) {
//        if (position%5==0){
//            return true;
//        }
//        return false;
//    }

    @Override
    public int getLayoutId() {
        return R.layout.recycler_item_delete;
    }

    @Override
    public void bindData(BaseViewHolder holder, String data, int position) {

    }

//    @Override
//    public void bindTitleData(BaseViewHolder holder, String data, int position) {
//        TextView title = (TextView) holder.getView(R.id.title);
//        title.setText("标题："+data);
//    }

    @Override
    public int getEmptyLayoutId() {
        return R.layout.recycler_item_empty;
    }


}
