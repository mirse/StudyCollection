//package com.wdz.module_basis.widget.recyclerview.universal;
//
//import android.content.Context;
//
//import java.util.List;
//
///**
// * @author 张新锦
// */
//public abstract class SingleSelectRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {
//
//    //默认-1为无选中项
//    int mSelectedIndex = -1;
//
//    public SingleSelectRecyclerViewAdapter(Context mContext) {
//        super(mContext);
//        mSelectedIndex = -1;
//    }
//
//    @Override
//    public void setDatas(List<T> list) {
//        super.setDatas(list);
//        mSelectedIndex = -1;
//    }
//
//    @Override
//    public void deleteData(int position){
//        if(mSelectedIndex == position){
//            mSelectedIndex = -1;
//        }
//        super.deleteData(position);
//    }
//
//    @Override
//    public void deleteAll(){
//        mSelectedIndex = -1;
//        super.deleteAll();
//    }
//
//    /**
//     * 设置选中的item
//     *
//     * @param position
//     */
//    public void setItemChecked(int position) {
//        mSelectedIndex = position;
//    }
//
//
//    /**
//     * 获取选中的position
//     * @return
//     */
//    public int getSelectPosition(){
//        return mSelectedIndex;
//    }
//
//
//
//
//
//
//}
