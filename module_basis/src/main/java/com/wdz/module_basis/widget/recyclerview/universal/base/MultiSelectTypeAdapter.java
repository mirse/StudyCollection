package com.wdz.module_basis.widget.recyclerview.universal.base;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多选类型adapter，不支持第二次新增数据
 * @author dezhi.wang
 */
public abstract class MultiSelectTypeAdapter<T> extends BaseRecyclerViewAdapter{
    /**
     * 是否选中某项
     */
    HashMap<Integer,Boolean> mSelectList = new HashMap<>();

    public MultiSelectTypeAdapter(Context mContext, List list) {
        super(mContext, list);

    }

    /**
     * 设置选中的item
     *
     * @param position
     */
    public void setItemChecked(int position, boolean value) {
        if (mSelectList!=null){
            mSelectList.put(position,value);
        }
    }


    // TODO: 2020/7/12 根据position得到item的选中状态
    /**
     * 获取item的选中状态
     * @param position
     * @return
     */
    public boolean getItemChecked(int position) {
        for (Map.Entry<Integer,Boolean> entry:mSelectList.entrySet()) {
            if (position == entry.getKey()){
                return entry.getValue();
            }
        }
        return false;
    }


    /**
     * 获取选中的position
     * @return
     */
    public List<Integer> getSelectListIndex(){
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer,Boolean> entry:mSelectList.entrySet()) {
            result.add(entry.getKey());
        }
        return result;
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int type, Object data, int position) {
        if (type == VIEW_TYPE_NORMAL){
            bindData(holder,(T) data,position);
        }
    }

    /**
     * 绑定数据源
     * @param holder
     * @param data
     * @param position
     */
    public abstract void bindData(BaseViewHolder holder,T data, int position);
}
