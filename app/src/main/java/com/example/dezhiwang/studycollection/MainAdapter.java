package com.example.dezhiwang.studycollection;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by dezhi.wang on 2018/9/21.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<MainBean> mList;
    private ItemClickListener itemClickListener;
    public MainAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
    public void setList(List<MainBean> mList){
        this.mList=mList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        int pos = position % 6;
        switch (pos){

            case 0:
                setBackGroundColor(holder, R.color.main_color);
                break;
            case 1:
                setBackGroundColor(holder, R.color.blue_color);
                break;
            case 2:
                setBackGroundColor(holder,R.color.green_deep_color);
                break;
            case 3:
                setBackGroundColor(holder, R.color.light_grey_color);
                break;
            case 4:
                setBackGroundColor(holder, R.color.pure_yellow_color);
                break;
            case 5:
                setBackGroundColor(holder, R.color.reply_color);
                break;
            default:
                break;
        }
        if (mList.size()<=0)
            return;
        MainBean mainBean = mList.get(position);
        if (mainBean!=null){
            holder.mText.setText(mainBean.getTitle());
            holder.mButton.setText(mainBean.getBtn());
            holder.mButton2.setText(mainBean.getBtn2());
            holder.mButton3.setText(mainBean.getBtn3());
            holder.mButton4.setText(mainBean.getBtn4());
            holder.mButton5.setText(mainBean.getBtn5());
            holder.mButton6.setText(mainBean.getBtn6());
            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(v,holder.getAdapterPosition());
                    }
                }
            });
            holder.mButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(v,holder.getAdapterPosition());
                    }
                }
            });
            holder.mButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(v,holder.getAdapterPosition());
                    }
                }
            });
            holder.mButton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(v,holder.getAdapterPosition());
                    }
                }
            });
            holder.mButton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(v,holder.getAdapterPosition());
                    }
                }
            });
            holder.mButton6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(v,holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    private void setBackGroundColor(MyViewHolder holder, int color) {
        holder.mText.setBackgroundColor(context.getResources().getColor(color));
        holder.mButton.setBackgroundColor(context.getResources().getColor(color));
        holder.mButton2.setBackgroundColor(context.getResources().getColor(color));
        holder.mButton3.setBackgroundColor(context.getResources().getColor(color));
        holder.mButton4.setBackgroundColor(context.getResources().getColor(color));
        holder.mButton5.setBackgroundColor(context.getResources().getColor(color));
        holder.mButton6.setBackgroundColor(context.getResources().getColor(color));
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }
    public interface ItemClickListener{
        void onItemClick(View view,int position);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mText;
        private final Button mButton;
        private final Button mButton2;
        private final Button mButton3;
        private final Button mButton4;
        private final Button mButton5;
        private final Button mButton6;

        public MyViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.textView);
            mButton = itemView.findViewById(R.id.button);
            mButton2 = itemView.findViewById(R.id.button2);
            mButton3 = itemView.findViewById(R.id.button3);
            mButton4 = itemView.findViewById(R.id.button4);
            mButton5 = itemView.findViewById(R.id.button5);
            mButton6 = itemView.findViewById(R.id.button6);
        }
    }
}
