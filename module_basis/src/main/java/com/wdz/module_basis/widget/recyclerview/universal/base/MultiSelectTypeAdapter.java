package com.wdz.module_basis.widget.recyclerview.universal.base;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 多选类型adapter，不支持第二次新增数据
 * @author dezhi.wang
 */
public abstract class MultiSelectTypeAdapter<T> extends BaseRecyclerViewAdapter{
    /**
     * 是否选中某项
     */
    List<Boolean> mSelectList = new ArrayList<>();

    public MultiSelectTypeAdapter(Context mContext, List list) {
        super(mContext, list);
        for (int i = 0;i<list.size();i++){
            mSelectList.add(false);
        }
    }

    /**
     * 设置选中的item
     *
     * @param position
     */
    public void setItemChecked(int position, boolean value) {
        if(mSelectList!=null&&mSelectList.size()>=position+1) {
            mSelectList.set(position, value);
        }
        else{
            ;
        }
    }


    // TODO: 2020/7/12 根据position得到item的选中状态
    /**
     * 获取item的选中状态
     * @param position
     * @return
     */
    public boolean getItemChecked(int position) {
        if(mSelectList!=null&&mSelectList.size()!=0) {
            return mSelectList.get(position);

        }
        return false;
    }


    /**
     * 获取选中的position
     * @return
     */
    public List<Integer> getSelectListIndex(){
        List<Integer> result = new ArrayList<>();
        if(mSelectList!=null&&mSelectList.size()!=0) {
            for (int i = 0; i < mSelectList.size(); i++) {
                if (mSelectList.get(i)) {
                    result.add(i);
                }
            }
        }
        else{
            ;
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
