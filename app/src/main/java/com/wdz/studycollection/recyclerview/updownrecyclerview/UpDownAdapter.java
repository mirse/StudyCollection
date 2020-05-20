package com.wdz.studycollection.recyclerview.updownrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

public class UpDownAdapter extends RecyclerView.Adapter<UpDownAdapter.ViewHolder> {
    private UpDownRecyclerViewActivity.Device mDevice;
    private Context context;
    private final int TYPE_BULB = 0;
    private final int TYPE_GATEWAY = 1;



    public UpDownAdapter(UpDownRecyclerViewActivity.Device device, Context context) {
        mDevice = device;
        this.context = context;
    }

    public void updateDevice(UpDownRecyclerViewActivity.Device device) {
        mDevice = device;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_content,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UpDownAdapter.ViewHolder holder, int position) {
        switch (position){
            case 0:
                List<UpDownRecyclerViewActivity.Bulb> bulbList = mDevice.getBulbList();
                holder.mTextView.setText("灯");
                holder.mRecyclerView.setHasFixedSize(true);
                holder.mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3,
                        GridLayoutManager.VERTICAL, false));
                ItemRecyclerViewAdapter mItemRecyclerViewAdapter = new ItemRecyclerViewAdapter(position);
                holder.mRecyclerView.setAdapter(mItemRecyclerViewAdapter);
                break;
            case 1:
                List<UpDownRecyclerViewActivity.Gateway> gatewayList = mDevice.getGatewayList();
                holder.mTextView.setText("网关");
                holder.mRecyclerView.setHasFixedSize(true);
                holder.mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3,
                        GridLayoutManager.VERTICAL, false));
                ItemRecyclerViewAdapter mItemRecyclerViewAdapter1 = new ItemRecyclerViewAdapter(gatewayList);
                holder.mRecyclerView.setAdapter(mItemRecyclerViewAdapter1);
                break;
            default:
                break;
        }


    }


    @Override
    public int getItemCount() {
        return 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        RecyclerView mRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_recycler_name);
            mRecyclerView = itemView.findViewById(R.id.tv_recycler_view);
        }
    }
}
