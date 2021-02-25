package com.wdz.module_basis.widget.recyclerview.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wdz.module_basis.R;
import com.wdz.module_basis.widget.recyclerview.bean.MyData;
import com.wdz.module_basis.widget.recyclerview.universal.base.SingleTypeAdapter;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dezhi.wang
 * @Date 2021/1/9 15:17
 */
public class ListViewAdapter extends BaseAdapter {
    private List<MyData> list = new ArrayList<>();
    private final LayoutInflater layoutInflater;


    public ListViewAdapter(Context context,  List<MyData> list) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }
//
//
//    @Override
//    public void bindData(BaseViewHolder holder, final MyData data, final int position) {
//        if (data.isSelect){
//
//            holder.getView(R.id.control_part).setVisibility(View.VISIBLE);
////            viewHolder.cotrolPart.findViewById(R.id.seekbar_light).requestLayout();
////            viewHolder.cotrolPart.requestLayout();
//        }
//        else {
//
//            holder.getView(R.id.control_part).setVisibility(View.GONE);
//        }
//
//        holder.getView(R.id.item_expand_icon).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                data.isSelect = !data.isSelect;
//                notifyDataSetChanged();
//            }
//        });
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.list_view_item;
//    }
//
//    @Override
//    public int getHeadLayoutId() {
//        return 0;
//    }
//
//    @Override
//    public int getEmptyLayoutId() {
//        return 0;
//    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_view_item,parent,false);
            viewHolder.btnExpand = (ImageView)convertView.findViewById(R.id.item_expand_icon);
            viewHolder.cotrolPart = (ConstraintLayout)convertView.findViewById(R.id.control_part);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list.get(position).isSelect){

            viewHolder.cotrolPart.setVisibility(View.VISIBLE);
//            viewHolder.cotrolPart.findViewById(R.id.seekbar_light).requestLayout();
//            viewHolder.cotrolPart.requestLayout();
        }
        else {

            viewHolder.cotrolPart.setVisibility(View.GONE);
        }

        viewHolder.btnExpand .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).isSelect = !list.get(position).isSelect;
                notifyDataSetChanged();
            }
        });

        return convertView;
    }



    private class ViewHolder{
        ImageView btnExpand;
        ConstraintLayout cotrolPart;
    }
}
