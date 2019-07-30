//package com.example.dezhiwang.studycollection.RecyclerView.StaggerGrid;
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.dezhiwang.studycollection.R;
//
//import java.util.ArrayList;
//
//public class MDStaggeredRvAdapter extends RecyclerView.Adapter<MDStaggeredRvAdapter.ViewHolder> {
//
//    private ArrayList<String> mData;
//
//    public MDStaggeredRvAdapter(ArrayList<String> data) {
//        this.mData = data;
//    }
//
//    public void move(int fromPosition, int toPosition) {
//
//        String prev = mData.remove(fromPosition);
//        mData.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
//        notifyItemMoved(fromPosition, toPosition);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position%2;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
//        View view;
//        if (viewType == 1){
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_rv_staggered_item, viewGroup, false);
//        }
//        else{
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_rv_staggered_item_two, viewGroup, false);
//        }
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        viewHolder.mTv.setText(mData.get(i));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData == null? 0:mData.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView mTv;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            mTv = (TextView) itemView.findViewById(R.id.item_tv);
//        }
//    }
//}
