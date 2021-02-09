package com.wdz.module_basis.widget.recyclerview.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wdz.module_basis.R;
import com.wdz.module_basis.widget.recyclerview.bean.MyData;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dezhi.wang
 * @Date 2021/1/9 15:17
 */
public class ListViewAdapter extends CommonAdapter<MyData> {
    private List<MyData> list = new ArrayList<>();
    private final LayoutInflater layoutInflater;


    public ListViewAdapter(Context context, int layoutId, List<MyData> list) {
        super(context, layoutId, list);
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }


    @Override
    protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, final MyData item, final int position) {
        TextView text = viewHolder.getView(R.id.text);
        ConstraintLayout constraintLayout = viewHolder.getView(R.id.cl);
        ConstraintLayout constraintLayout1 = viewHolder.getView(R.id.cl_1);
        Button btnExpand = viewHolder.getView(R.id.bt_expand);
        text.setText(item.s);
        if (item.isSelect){
            constraintLayout.setVisibility(View.VISIBLE);
            constraintLayout1.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayout.setVisibility(View.GONE);
            constraintLayout1.setVisibility(View.GONE);
        }

        btnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.isSelect = !item.isSelect;
                notifyDataSetChanged();
            }
        });
    }


}
