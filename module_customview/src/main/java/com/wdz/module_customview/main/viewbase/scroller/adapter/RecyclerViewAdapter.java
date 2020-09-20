package com.wdz.module_customview.main.viewbase.scroller.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wdz.module_customview.R;
import com.wdz.module_customview.main.viewbase.scroller.ShadowCardView;
import com.wdz.module_customview.main.viewbase.scroller.bean.Device;
import com.wdz.module_customview.main.viewbase.scroller.bean.Page;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<Page> mPage = new ArrayList<>();
    private onItemClickListener onItemClickListener;
    private Context context;
    private Page newPage;

    public RecyclerViewAdapter(List<Page> pages,Context context) {
        mPage = pages;
        this.context = context;
        Log.i(TAG, "RecyclerViewAdapter: "+mPage.toString());
    }


    public List<Page> getPages(){
        return mPage;
    }

    public void setPages(List<Page> mPage){
        Log.i(TAG, "setPages: ");
        this.mPage = mPage;
        notifyDataSetChanged();
    }

    /**
     * 新增扫描到的新设备
     */
    public void insertDevice(Device mDevice){

        Page page = mPage.get(mPage.size() - 1);
        List<Device> device = page.getDevice();
        if (device.size()<7){
            Log.i(TAG, "insertDevice: mPage:"+mPage.size()+" device"+device.size());
            device.add(mDevice);

        }
        else {
            newPage = new Page();
            List<Device> deviceList = newPage.getDevice();
            if (deviceList.size()<7){
                deviceList.add(mDevice);
            }
            Log.i(TAG, "insertDevice: new Page");
            mPage.add(newPage);
        }
        notifyDataSetChanged();


    }


    public interface onItemClickListener{
        /**
         * @param device 当前点击的设备
         * @param pagePosition page位置
         * @param devicePosition deivice位置
         */
        void onItemClick(Device device, int pagePosition, int devicePosition);
        void onItemLongClick(View view, int position);
    }

    /**
     * 设置点击监听
     * @param listener
     */
    public void setOnClickListener(onItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.module_customview_scan_device_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.setIsRecyclable(false);
        Page page = mPage.get(i);
        final List<Device> deviceList = page.getDevice();
        if (deviceList==null){
            return;
        }
        for (int j=0;j<deviceList.size();j++){
            Log.i(TAG, "onBindViewHolder: size:"+deviceList.size()+" j:"+j+" viewHolder:"+viewHolder);
            final Device device = deviceList.get(j);
            switch (j){
                case 0:
                    viewHolder.scanDevice1.setVisibility(View.VISIBLE);
                    switchDeviceType(device,viewHolder.scanDevice1);
                    if (device.isSelected()){
                        viewHolder.scanDevice1.setShadowColor(true);
                    }
                    else{
                        viewHolder.scanDevice1.setShadowColor(false);
                    }
                    viewHolder.scanDevice1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(device,i,0);
                        }
                    });
                    break;
                case 1:
                    viewHolder.scanDevice2.setVisibility(View.VISIBLE);
                    switchDeviceType(device,viewHolder.scanDevice2);
                    if (deviceList.get(j).isSelected()){
                        viewHolder.scanDevice2.setShadowColor(true);
                    }
                    else{
                        viewHolder.scanDevice2.setShadowColor(false);
                    }
                    viewHolder.scanDevice2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(deviceList.get(1),i,1);

                        }
                    });
                    break;
                case 2:
                    viewHolder.scanDevice3.setVisibility(View.VISIBLE);
                    switchDeviceType(device,viewHolder.scanDevice3);
                    if (deviceList.get(j).isSelected()){
                        viewHolder.scanDevice3.setShadowColor(true);
                    }
                    else{
                        viewHolder.scanDevice3.setShadowColor(false);
                    }
                    viewHolder.scanDevice3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(deviceList.get(2),i,2);

                        }
                    });
                    break;
                case 3:
                    viewHolder.scanDevice4.setVisibility(View.VISIBLE);
                    switchDeviceType(device,viewHolder.scanDevice4);
                    if (deviceList.get(j).isSelected()){
                        viewHolder.scanDevice4.setShadowColor(true);
                    }
                    else{
                        viewHolder.scanDevice4.setShadowColor(false);
                    }
                    viewHolder.scanDevice4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(deviceList.get(3),i,3);

                        }
                    });
                    break;
                case 4:
                    viewHolder.scanDevice5.setVisibility(View.VISIBLE);
                    switchDeviceType(device,viewHolder.scanDevice5);
                    if (deviceList.get(j).isSelected()){
                        viewHolder.scanDevice5.setShadowColor(true);
                    }
                    else{
                        viewHolder.scanDevice5.setShadowColor(false);
                    }
                    viewHolder.scanDevice5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(deviceList.get(4),i,4);

                        }
                    });
                    break;
                case 5:
                    viewHolder.scanDevice6.setVisibility(View.VISIBLE);
                    switchDeviceType(device,viewHolder.scanDevice6);
                    if (deviceList.get(j).isSelected()){
                        viewHolder.scanDevice6.setShadowColor(true);
                    }
                    else{
                        viewHolder.scanDevice6.setShadowColor(false);
                    }
                    viewHolder.scanDevice6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(deviceList.get(5),i,5);

                        }
                    });
                    break;
                case 6:
                    viewHolder.scanDevice7.setVisibility(View.VISIBLE);
                    switchDeviceType(device,viewHolder.scanDevice7);
                    if (deviceList.get(j).isSelected()){
                        viewHolder.scanDevice7.setShadowColor(true);
                    }
                    else{
                        viewHolder.scanDevice7.setShadowColor(false);
                    }
                    viewHolder.scanDevice7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(deviceList.get(6),i,6);

                        }
                    });
                    break;
                default:
                    break;
            }

        }







    }

    private void switchDeviceType(Device device,ShadowCardView shadowCardView) {
        switch (device.getDeviceType()){
            case 1:
                shadowCardView.setBitmap(context.getResources().getDrawable(R.mipmap.img_avatar_adddevice_a60,null));
                break;
            case 2:
                shadowCardView.setBitmap(context.getResources().getDrawable(R.mipmap.img_avatar_adddevice_bridge,null));
                break;
            case 3:
                shadowCardView.setBitmap(context.getResources().getDrawable(R.mipmap.img_avatar_adddevice_ceiling,null));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mPage == null?0:mPage.size();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder{

        ShadowCardView scanDevice1;
        ShadowCardView scanDevice2;
        ShadowCardView scanDevice3;
        ShadowCardView scanDevice4;
        ShadowCardView scanDevice5;
        ShadowCardView scanDevice6;
        ShadowCardView scanDevice7;


        public ViewHolder(View itemView) {
            super(itemView);
            scanDevice1 = itemView.findViewById(R.id.img_scan_device1);
            scanDevice2 = itemView.findViewById(R.id.img_scan_device2);
            scanDevice3 = itemView.findViewById(R.id.img_scan_device3);
            scanDevice4 = itemView.findViewById(R.id.img_scan_device4);
            scanDevice5 = itemView.findViewById(R.id.img_scan_device5);
            scanDevice6 = itemView.findViewById(R.id.img_scan_device6);
            scanDevice7 = itemView.findViewById(R.id.img_scan_device7);
        }
    }
}
