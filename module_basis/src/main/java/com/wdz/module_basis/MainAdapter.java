package com.wdz.module_basis;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wdz.common.base.BaseRecyclerViewAdapter;
import com.wdz.common.bean.MainBean;


public class MainAdapter extends BaseRecyclerViewAdapter<MainBean> {
    private ItemClickListener itemClickListener;

    public MainAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public int getEmptyLayoutId() {
        return 0;
    }

    @Override
    public int getHeaderLayoutId() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_basis_item_main;
    }


    @Override
    public void bindData(final BaseViewHolder holder, MainBean data, int position) {
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

        TextView tvTitle = (TextView) holder.getView(R.id.textView);
        Button btn = (Button) holder.getView(R.id.button);
        Button btn2 = (Button) holder.getView(R.id.button2);
        Button btn3 = (Button) holder.getView(R.id.button3);
        Button btn4 = (Button) holder.getView(R.id.button4);
        Button btn5 = (Button) holder.getView(R.id.button5);
        Button btn6 = (Button) holder.getView(R.id.button6);
        tvTitle.setText(data.getTitle());
        btn.setText(data.getBtn());
        btn2.setText(data.getBtn2());
        btn3.setText(data.getBtn3());
        btn4.setText(data.getBtn4());
        btn5.setText(data.getBtn5());
        btn6.setText(data.getBtn6());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick(v,holder.getAdapterPosition());
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick(v,holder.getAdapterPosition());
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick(v,holder.getAdapterPosition());
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick(v,holder.getAdapterPosition());
                }
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick(v,holder.getAdapterPosition());
                }
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.onItemClick(v,holder.getAdapterPosition());
                }
            }
        });

    }


    private void setBackGroundColor(BaseViewHolder holder, int color) {
        holder.getView(R.id.textView).setBackgroundColor(getmContext().getResources().getColor(color));
        holder.getView(R.id.button).setBackgroundColor(getmContext().getResources().getColor(color));
        holder.getView(R.id.button2).setBackgroundColor(getmContext().getResources().getColor(color));
        holder.getView(R.id.button3).setBackgroundColor(getmContext().getResources().getColor(color));
        holder.getView(R.id.button4).setBackgroundColor(getmContext().getResources().getColor(color));
        holder.getView(R.id.button5).setBackgroundColor(getmContext().getResources().getColor(color));
        holder.getView(R.id.button6).setBackgroundColor(getmContext().getResources().getColor(color));

    }

    @Override
    public void bindHeaderData(BaseViewHolder holder) {

    }

    @Override
    public boolean isHeaderOnlyLine() {
        return false;
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
}
