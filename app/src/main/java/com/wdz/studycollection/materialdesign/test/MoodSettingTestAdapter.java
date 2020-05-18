package com.wdz.studycollection.materialdesign.test;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdz.studycollection.R;
import com.wdz.studycollection.materialdesign.CoordinatorLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoodSettingTestAdapter extends RecyclerView.Adapter<MoodSettingTestAdapter.ViewHolder> {
    private static final String TAG = "MoodSettingAdapter";
    private List<CoordinatorLayoutTestActivity.Mood> mData;
    private onItemClickListener onItemClickListener;
    private Context mContext;

    //点击区域
    public enum REGION {
        //添加mood item(特殊)
        MOOD_ADD,
        //mood item
        MOOD
    }
    public MoodSettingTestAdapter(List<CoordinatorLayoutTestActivity.Mood> data, Context mContext) {
        this.mData = data;
        this.mContext = mContext;
    }


    public void addItem(){
        if (mData == null){
            mData = new ArrayList<>();
        }
        mData.add(new CoordinatorLayoutTestActivity.Mood());
        notifyItemInserted(0);

    }

    public void deleteItem(int position){
        if (mData == null || mData.isEmpty()){
            return;
        }
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public interface onItemClickListener{

        /**
         * 整个item的点击事件
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);

        /**
         * 长按点击事件
         * @param view
         * @param position
         */
        void onItemLongClick(View view, int position);

        /**
         * item内的点击事件
         * @param position
         * @param mood
         * @param isChecked
         */
        void onPowerClickListener(REGION region, int position, CoordinatorLayoutTestActivity.Mood mood, boolean isChecked);
    }


    public void setOnClickListener(onItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_item_mood_card, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }





    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTvItem;
        ImageView mIvIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvItem = itemView.findViewById(R.id.tv_item);
            mIvIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
