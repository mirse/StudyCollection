//package com.wdz.module_basis.widget.recyclerview.universal;
//
//import android.content.Context;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 张新锦
// */
//public abstract class SelectRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> {
//
//
//    //是否选中某项
//    List<Boolean> mSelectList;
//
//    public SelectRecyclerViewAdapter(Context mContext) {
//        super(mContext);
//        mSelectList = new ArrayList<>();
//    }
//
//    @Override
//    public void setDatas(List<T> list) {
//        super.setDatas(list);
//        for (int i = 0;i<list.size();i++){
//            mSelectList.add(false);
//        }
//    }
//
//    @Override
//    public void setDatas(int position , T t){
//        super.setDatas(position, t);
//    }
//
//    @Override
//    public void addData(T t){
//        mSelectList.add(false);
//        super.addData(t);
//    }
//
//    @Override
//    public void deleteData(int position){
//        if(mSelectList!=null&&mSelectList.size()>=position+1){
//            mSelectList.remove(position);
//        }
//        else{
//            ;
//        }
//        super.deleteData(position);
//    }
//
//    @Override
//    public void deleteAll(){
//        if(mSelectList!=null){
//            mSelectList.clear();
//        }
//        else{
//            ;
//        }
//        super.deleteAll();
//    }
//
//
//
//    /**
//     * 设置选中的item
//     *
//     * @param position
//     */
//    public void setItemChecked(int position, boolean value) {
//        if(mSelectList!=null&&mSelectList.size()>=position+1) {
//            mSelectList.set(position, value);
//            notifyDataSetChanged();
//        }
//        else{
//            ;
//        }
//    }
//
//
//    // TODO: 2020/7/12 根据position得到item的选中状态
//    /**
//     * 获取item的选中状态
//     * @param position
//     * @return
//     */
//    public boolean getItemChecked(int position) {
//        if(mSelectList!=null&&mSelectList.size()!=0) {
//            return mSelectList.get(position);
//
//        }
//        return false;
//    }
//
//    /**
//     * 获取选中的position
//     * @return
//     */
//    public List<Integer> getSelectListIndex(){
//        List<Integer> result = new ArrayList<>();
//        if(mSelectList!=null&&mSelectList.size()!=0) {
//            for (int i = 0; i < mSelectList.size(); i++) {
//                if (mSelectList.get(i)) {
//                    result.add(i);
//                }
//            }
//        }
//        else{
//            ;
//        }
//        return result;
//    }
//}
