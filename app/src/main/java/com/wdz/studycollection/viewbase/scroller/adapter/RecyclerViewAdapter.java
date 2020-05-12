package com.wdz.studycollection.viewbase.scroller.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.viewbase.scroller.bean.Device;
import com.wdz.studycollection.viewbase.scroller.bean.Page;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<Page> mPage = new ArrayList<>();
    private List<String> mDevice;

    public RecyclerViewAdapter(List<Page> pages) {
        mPage = pages;
    }
//
//    //当设备个数大于7个新建page
//    public void insertPage(){
//        if (mDevice!=null){
//            if ((mDevice.size()/7+1)>mPage.size()){
//                mPage.add("1");
//                notifyItemInserted(mPage.size());
//            }
//
//        }
//    }
//
//
//    /**
//     * @return 当前设备个数
//     */
//    public int getDeviceSize(){
//        if (mDevice!=null){
//            return mDevice.size();
//        }
//        return 0;
//    }
//
//    /**
//     * 新增扫描到的新设备
//     */
//    public void insertDevice(){
//        if (mDevice==null){
//            mDevice = new ArrayList<>();
//        }
//        mDevice.add("1");
//        insertPage();
//    }


    public interface onItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }



    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Log.i(TAG, "onBindViewHolder: "+i);
        Page page = mPage.get(i);
        Log.i(TAG, "onBindViewHolder: page"+page.toString());
        List<Device> deviceList = page.getDevice();
        if (deviceList==null){
            return;
        }
        for (int j=0;j<deviceList.size();j++){
            switch (j){
                case 0:
                    viewHolder.imageView1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    viewHolder.imageView2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    viewHolder.imageView3.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    viewHolder.imageView4.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    viewHolder.imageView5.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    viewHolder.imageView6.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    viewHolder.imageView7.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

        }
//        viewHolder.imageView1.setVisibility(View.VISIBLE);
//        viewHolder.imageView2.setVisibility(View.VISIBLE);
//        viewHolder.imageView3.setVisibility(View.VISIBLE);
//        viewHolder.imageView4.setVisibility(View.VISIBLE);
//        viewHolder.imageView5.setVisibility(View.VISIBLE);
//        viewHolder.imageView6.setVisibility(View.VISIBLE);
//        viewHolder.imageView7.setVisibility(View.VISIBLE);






    }

    @Override
    public int getItemCount() {
        return mPage == null?0:mPage.size();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
        ImageView imageView7;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.imageView1);
            imageView2 = itemView.findViewById(R.id.imageView2);
            imageView3 = itemView.findViewById(R.id.imageView3);
            imageView4 = itemView.findViewById(R.id.imageView4);
            imageView5 = itemView.findViewById(R.id.imageView5);
            imageView6 = itemView.findViewById(R.id.imageView6);
            imageView7 = itemView.findViewById(R.id.imageView7);
        }
    }
}
