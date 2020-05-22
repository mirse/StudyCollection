package com.wdz.studycollection.recyclerview.updownrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdz.studycollection.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpDownAdapter extends RecyclerView.Adapter<UpDownAdapter.ViewHolder> {
    private static final String TAG = "UpDownAdapter";
    private List<UpDownRecyclerViewActivity.Device> mList = new ArrayList<>();
    private Context context;
    public static final int VIEW_TYPE_HEADER = 3;
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_BULB = 1;
    private static final int VIEW_TYPE_GATEWAY = 2;
    private LayoutInflater mInflater;
    private final int COLUMN = 3;
    private View mHeaderView;

    public UpDownAdapter(List<UpDownRecyclerViewActivity.Device> list, Context context) {
        refreshList(list);
        this.context = context;
    }

    /**
     * 设置recyclerView头view
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 获取recyclerView头view
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    public void updateDevice(List<UpDownRecyclerViewActivity.Device> list) {
        refreshList(list);
        notifyDataSetChanged();
    }

    /**
     * 重新整理list数据
     * @param list
     */
    private void refreshList(List<UpDownRecyclerViewActivity.Device> list){
        mList.clear();
        List<UpDownRecyclerViewActivity.Device> bulbList = new ArrayList<>();
        List<UpDownRecyclerViewActivity.Device> gatewayList = new ArrayList<>();
        for (UpDownRecyclerViewActivity.Device device:list) {
            if (device.deviceType == 1){
                bulbList.add(device);
            }
            if (device.deviceType == 2){
                gatewayList.add(device);
            }
        }
        if (bulbList.size()!=0){
            mList.add(new UpDownRecyclerViewActivity.Device(0));
            mList.addAll(bulbList);
        }
        if (gatewayList.size()!=0){
            mList.add(new UpDownRecyclerViewActivity.Device(0));
            mList.addAll(gatewayList);
        }
        for (UpDownRecyclerViewActivity.Device device:mList) {
            Log.i(TAG, "refreshList: "+device.toString());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        mInflater = LayoutInflater.from(context);
        if(mHeaderView != null && viewType == VIEW_TYPE_HEADER){
            return new ViewHolder(mHeaderView);
        }
        switch (viewType){
            case VIEW_TYPE_TITLE:
                viewHolder = new ViewHolder(mInflater.inflate(R.layout.item_home_content,parent,false));
                break;
            case VIEW_TYPE_BULB:
                viewHolder = new ViewHolder(mInflater.inflate(R.layout.item_bulb,parent,false));
                break;
            case VIEW_TYPE_GATEWAY:
                viewHolder = new ViewHolder(mInflater.inflate(R.layout.item_gateway,parent,false));
                break;
            default:
                break;
        }
        return viewHolder;

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager!=null){
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mHeaderView == null) {
                        if (mList.get(position).deviceType == 0) {
                            return COLUMN;
                        } else {
                            return 1;
                        }
                    }

                    if (position == 0) {
                        return COLUMN;
                    } else {
                        if (mList.get(position - 1).deviceType == 0) {
                            return COLUMN;
                        } else {
                            return 1;
                        }

                    }


//                    if (getItemViewType(position) == VIEW_TYPE_HEADER){
//                        return layoutManager.getSpanCount();
//                    }
//
//                    Log.i(TAG, "getSpanSize: "+mList.get(position).deviceType);
//                    if (mList.get(position-1).deviceType == 0){
//                        return COLUMN;
//                    }
//                    else{
//                        return 1;
//                    }

                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        Log.i(TAG, "getItemViewType: "+position+" mHeaderView:"+mHeaderView);
        if(mHeaderView == null){
            switch (mList.get(position).deviceType){
                //标题
                case 0:
                    return VIEW_TYPE_TITLE;
                //灯
                case 1:
                    return VIEW_TYPE_BULB;
                //网关
                case 2:
                    return VIEW_TYPE_GATEWAY;
                default:
                    return VIEW_TYPE_BULB;
            }
        }

        if(position == 0){
            return VIEW_TYPE_HEADER;
        }

        switch (mList.get(position-1).deviceType){
            //标题
            case 0:
                return VIEW_TYPE_TITLE;
            //灯
            case 1:
                return VIEW_TYPE_BULB;
            //网关
            case 2:
                return VIEW_TYPE_GATEWAY;
            default:
                return VIEW_TYPE_BULB;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull UpDownAdapter.ViewHolder holder, int position) {
//        UpDownRecyclerViewActivity.Device device = mList.get(position);



    }


    @Override
    public int getItemCount() {
        return mHeaderView==null? mList.size():mList.size()+1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{



        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
