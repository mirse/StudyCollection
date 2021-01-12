package com.wdz.module_architecture.paging;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.module_architecture.R;
import com.wdz.module_architecture.paging.room.User;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author dezhi.wang
 * @Date 2021/1/9 9:14
 */
public class PagingAdapter extends PagedListAdapter<User, PagingAdapter.MyViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private List<User> users = new ArrayList<>();
    private Context context;

    protected PagingAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback, List<User> userList, Context context) {
        super(diffCallback);
        this.users = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_paging, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: "+position);
        TextView textView = holder.itemView.findViewById(R.id.textView);
        textView.setText(getItem(position).toString());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
