package com.wdz.module_basis.widget.recyclerview.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wdz.module_basis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dezhi.wang
 * @Date 2021/1/9 15:17
 */
public class ListViewAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public ListViewAdapter(Context context, List<String> list) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_view_item, parent,false);
            viewHolder.mTextView = convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(list.get(position));
        return convertView;
    }
    static class ViewHolder{
        private TextView mTextView;
    }
}
