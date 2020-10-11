package com.wdz.module_basis.widget.recyclerview.universal.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * 通用adapter定义normalItem与emptyItem
 * @author wdz
 */
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    //数据源
    private List mList = new ArrayList<>();
    //普通item布局
    static final int VIEW_TYPE_NORMAL = 1;
    //空布局
    static final int VIEW_TYPE_EMPTY = 2;
    //带标题布局
    static final int VIEW_TYPE_TITLE = 3;




    /**
     * 获取layoutId
     *
     * @return
     */
    public abstract int getLayoutId();


    /**
     * 获取空布局layoutId
     *
     * @return =0 代表没有emptyView
     */
    public abstract int getEmptyLayoutId();


    public BaseRecyclerViewAdapter(Context mContext,List list) {
        this.mContext = mContext;
        this.mList =list;
    }

    /**
     * 返回数据源
     * @return
     */
    protected List getData(){
        return mList;
    }

    public Context getContext() {
        return mContext;
    }


    @Override
    public int getItemViewType(int position) {
        if(mList.isEmpty() && getEmptyLayoutId()!=0){
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_NORMAL;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId;
        if(viewType == VIEW_TYPE_EMPTY){
            layoutId = getEmptyLayoutId();
            View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            if(view!=null) {
                return new EmptyViewHolder(view);
            }
        }
        else if(viewType == VIEW_TYPE_NORMAL){
            layoutId = getLayoutId();
            View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            if(view!=null) {
                return new BaseViewHolder(view);
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.BaseViewHolder holder, final int position) {
        int type = getItemViewType(position);
        if (type!= VIEW_TYPE_EMPTY){
            onBindViewHolder(holder,type,mList.get(position),position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClickNormal(position);
                    }
                }
            });
            //全部都加上长按监听
            holder.itemView.setLongClickable(true);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onLongClick(position,v);
                    }
                    return true;
                }
            });
        }






    }




    /**
     * 点击事件
     */
    public interface OnItemClickListener {
        /**
         * 通用点击事件
         * @param devicePosition
         */
        void onClickNormal(int devicePosition);
    }

    public interface OnItemLongClickListener{
        void onLongClick(int position, View view);
    }

    /**
     * 设置点击监听
     *
     * @param listener
     */
    public void setOnClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    /**
     * 将数据与view绑定
     *
     * @param holder
     * @param type
     * @param data
     * @param position 点击位置
     */
    public abstract void onBindViewHolder(BaseViewHolder holder,int type, Object data, int position);




    @Override
    public int getItemCount() {
        int itemCount = mList.size();
        if (0 != getEmptyLayoutId() && itemCount == 0) {
            //总数0变为1
            itemCount++;
        }
        return itemCount;
    }

    class EmptyViewHolder extends BaseViewHolder{
        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> views;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            views = new SparseArray<>();
        }

        /**
         * 获取id所对应的控件
         *
         * @param id
         * @return
         */
        public View getView(int id) {
            View view = views.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                views.put(id, view);
            }
            return view;
        }
    }





}
