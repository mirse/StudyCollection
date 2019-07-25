package com.example.dezhiwang.studycollection.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dezhiwang.studycollection.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private onItemClickListener onItemClickListener;

    public MyAdapter(ArrayList<String> data) {
        this.mData = data;
    }


    public void addItem(){
        if (mData == null){
            mData = new ArrayList<>();
        }
        mData.add(0,"new item");
        notifyItemInserted(0);
        //notifyDataSetChanged();
    }

    public void deleteItem(){
        if (mData == null || mData.isEmpty()){
            return;
        }
        mData.remove(0);
        notifyItemRemoved(0);
    }

    public interface onItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }


    public void setOnClickListener(MyAdapter.onItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_rv_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.mTvItem.setText(mData.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener !=null){
                    int position = viewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(viewHolder.itemView,position);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int position = viewHolder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(viewHolder.itemView, position);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvItem = itemView.findViewById(R.id.tv_item);
        }
    }
}
