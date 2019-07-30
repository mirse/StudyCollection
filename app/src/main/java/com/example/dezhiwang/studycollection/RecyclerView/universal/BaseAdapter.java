package com.example.dezhiwang.studycollection.RecyclerView.universal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;


import com.example.dezhiwang.studycollection.RecyclerView.MyAdapter;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.VH> {

    private List<T> mDatas;
    private static BaseAdapter.onItemClickListener onItemClickListener;
    public BaseAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public abstract int getLayoutId(int viewType);

    @Override
    public VH onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return VH.get(viewGroup,getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(VH vh, int position) {
        convert(vh,mDatas.get(position),position);
    }

    public abstract void convert(VH vh, T data, int position);


    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    public interface onItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(onItemClickListener listener){
        onItemClickListener = listener;
    }



    public static class VH extends RecyclerView.ViewHolder{
        private View mConvertView;
        private SparseArray<View> mView;
        public VH(View view) {
            super(view);
            mConvertView = view;
            mView = new SparseArray<>();
        }

        public static VH get(ViewGroup viewParent, int layoutId){
            View view = LayoutInflater.from(viewParent.getContext()).inflate(layoutId, viewParent, false);
            return new VH(view);

        }

        public <T extends View>T getView(int id){
            View view = mView.get(id);
            if (view == null){
                view = mConvertView.findViewById(id);
                mView.put(id,view);
            }
            return (T) view;
        }

        public void setText(int id,String value){
            TextView tv = getView(id);
            tv.setText(value);
        }

        public void addOnClickListener(final int position){
           mConvertView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (onItemClickListener !=null){
                       onItemClickListener.onItemClick(mConvertView,position);
                   }
               }
           });
        }

    }
}
