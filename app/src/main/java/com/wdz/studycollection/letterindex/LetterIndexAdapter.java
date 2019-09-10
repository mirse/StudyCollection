package com.wdz.studycollection.letterindex;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wdz.studycollection.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

/**
 * Created by dezhi.wang on 2018/10/26.
 */

public class LetterIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Person> persons;
    private LayoutInflater layoutInflater;
    public LetterIndexAdapter(Context context,ArrayList<Person> persons) {
        this.context=context;
        this.persons=persons;
        layoutInflater=LayoutInflater.from(context);

    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IndexHolder(layoutInflater.inflate(R.layout.index_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Person person = persons.get(position);
        String substring = person.getPingyin().substring(0, 1);
        IndexHolder indexHolder= (IndexHolder) holder;
        if (position==0){
            indexHolder.mTVTop.setVisibility(View.VISIBLE);
        }else{
            String pinyin = persons.get(position - 1).getPingyin().substring(0, 1);
            if (pinyin.equals(substring)){
                indexHolder.mTVTop.setVisibility(View.GONE);
            }else{
                indexHolder.mTVTop.setVisibility(View.VISIBLE);
            }
        }
        indexHolder.mTVTop.setText(substring);
        indexHolder.mTvName.setText(person.getName());
    }
    public void getPingYin(){
        return ;
    }

    @Override
    public int getItemCount() {
        return persons==null?0:persons.size();
    }
    public class IndexHolder extends RecyclerView.ViewHolder {

        private final TextView mTVTop;
        private final TextView mTvName;

        public IndexHolder(View itemView) {
            super(itemView);
            mTVTop = itemView.findViewById(R.id.tv_top);
            mTvName = itemView.findViewById(R.id.tv_pwd);
        }
    }
}
