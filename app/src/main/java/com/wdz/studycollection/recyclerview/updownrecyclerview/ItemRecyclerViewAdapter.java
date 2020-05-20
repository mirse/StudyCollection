package com.wdz.studycollection.recyclerview.updownrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.studycollection.R;

import java.util.ArrayList;

public class ItemRecyclerViewAdapter extends
        RecyclerView.Adapter<ItemRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {


    //新建一个私有变量用于保存用户设置的监听器
    private OnItemClickListener mOnItemClickListener = null;

    //set方法：
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    //define interface 自定义一个接口
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ItemRecyclerViewAdapter(int pI){


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bulb,null);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mView.setText(mHomes.get(position).getName());
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mHomes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.tv_name);
        }
    }
}

