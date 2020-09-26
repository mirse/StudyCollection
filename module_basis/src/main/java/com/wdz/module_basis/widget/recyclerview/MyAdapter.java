package com.wdz.module_basis.widget.recyclerview;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wdz.module_basis.R;


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

    public void deleteItem(int position){
        if (mData == null || mData.isEmpty()){
            return;
        }
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public interface onItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
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
                    onItemClickListener.onItemLongClick(viewHolder.itemView, position);
                    onItemClickListener.onItemClick(viewHolder.itemView,position);

                }
            }
        });

//        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (onItemClickListener != null) {
//                    int position = viewHolder.getLayoutPosition();
//                    //onItemClickListener.onItemLongClick(viewHolder.itemView, position);
//                }
//                return true;
//            }
//        });

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
