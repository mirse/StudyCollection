package com.wdz.studycollection.recyclerview.updownrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.List;

public class UpDownAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UpDownRecyclerViewActivity.Device> mList = new ArrayList<>();
    private Context context;
    private final int TYPE_BULB = 0;
    private final int TYPE_GATEWAY = 1;



    public static enum ITEM_TYPE{
        ITEM_TYPE_BULB,
        ITEM_TYPE_GATEWAY
    }

    public UpDownAdapter(List<UpDownRecyclerViewActivity.Device> deviceList, Context context) {
        mList = deviceList;
        this.context = context;
    }

    public void addDevice(UpDownRecyclerViewActivity.Device device) {
        mList.add(device);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_BULB:
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bulb,parent,false));
            case TYPE_GATEWAY:
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_gateway,parent,false));
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        UpDownRecyclerViewActivity.Device device = mList.get(position);
        if (device.deviceType == TYPE_BULB){
            return TYPE_BULB;
        }
        else if (device.deviceType == TYPE_GATEWAY){
            return TYPE_GATEWAY;
        }
        else{
            return TYPE_BULB;
        }

    }

    @Override
    public int getItemCount() {
        return mList == null?0:mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTvItem;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
