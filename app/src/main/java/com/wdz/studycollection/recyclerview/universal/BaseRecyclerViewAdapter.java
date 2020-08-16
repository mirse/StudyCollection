package com.wdz.studycollection.recyclerview.universal;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wdz
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {
    private Context context;
    private List<T> list = new ArrayList<>();
    //头布局
    private static final int VIEW_TYPE_HEADER = 0;
    //普通item布局
    private static final int VIEW_TYPE_NORMAL = 1;
    //空布局
    private static final int VIEW_TYPE_EMPTY = 2;
    private OnItemClickListener onItemClickListener;
    private List<MyItem> mList = new ArrayList<>();
    private boolean hasHeaderView = false;
    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        refreshList(list);
    }

    /**
     * 重新整理数据源，添加header与normal type
     * @param list
     */
    public void refreshList(List<T> list) {
        if (getHeaderLayoutId()!=0){
            mList.clear();
            mList.add(0,new MyItem(null,false,VIEW_TYPE_HEADER));
            for (T t:list) {
                mList.add(new MyItem(t,false,VIEW_TYPE_NORMAL));
            }
            notifyDataSetChanged();
        }
        else {
            mList.clear();
            for (T t:list) {
                mList.add(new MyItem(t,false,VIEW_TYPE_NORMAL));
            }
            notifyDataSetChanged();
        }

    }

    public void addData(T t){
        mList.add(new MyItem(t,false,VIEW_TYPE_NORMAL));
        notifyDataSetChanged();
    }
    public void deleteData(T t){

        for (int i=0;i<mList.size();i++){
            if (mList.get(i).t.equals(t)){
                mList.remove(mList.get(i));
                break;
            }
        }
//        for (MyItem myItem:mList) {
//            if (myItem.t.equals(t)){
//                mList.remove(myItem);
//            }
//        }

        //mList.remove(new MyItem(t,false,VIEW_TYPE_NORMAL));
        notifyDataSetChanged();
    }

    /**
     * 获取头view
     * @return =0 代表没有headView
     */
    public abstract int getHeaderLayoutId();

    /**获取layoutId
     * @return
     */
    public abstract int getLayoutId();

    /**获取layoutId
     * @return
     */
    public abstract int getEmptyLayoutId();

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 0){
            return VIEW_TYPE_EMPTY;
        }
        else{
            switch (mList.get(position).type){
                case VIEW_TYPE_HEADER:
                    hasHeaderView = true;
                    return VIEW_TYPE_HEADER;
                default:
                    return VIEW_TYPE_NORMAL;
            }
        }

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case VIEW_TYPE_HEADER:
                return new BaseViewHolder(LayoutInflater.from(context).inflate(getHeaderLayoutId(), parent, false));
            case VIEW_TYPE_NORMAL:
                return new BaseViewHolder(LayoutInflater.from(context).inflate(getLayoutId(), parent, false));
            case VIEW_TYPE_EMPTY:
                return new BaseViewHolder(LayoutInflater.from(context).inflate(getEmptyLayoutId(), parent, false));
            default:
                break;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.BaseViewHolder holder, final int position) {
        final MyItem myItem = mList.get(position);

        if (myItem.isSelect()){
            holder.itemView.setSelected(true);
        }
        else {
            holder.itemView.setSelected(false);
        }

        if (myItem.type == VIEW_TYPE_HEADER){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onClickHeader(position);
                    }
                }
            });
            bindHeaderData(holder);
        }
        else if (myItem.type == VIEW_TYPE_NORMAL){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (MyItem item:mList) {
                        item.isSelect = false;
                    }
                    myItem.isSelect = true;
                    notifyDataSetChanged();
                    if (onItemClickListener!=null){
                        if (hasHeaderView){
                            onItemClickListener.onClickNormal(position-1);
                        }
                        else {
                            onItemClickListener.onClickNormal(position);
                        }
                    }


                }
            });
            if (hasHeaderView){
                bindData(holder,myItem,position-1);
            }
            else{
                bindData(holder,myItem,position);
            }

        }

    }


    /**
     * 点击事件
     */
    public interface OnItemClickListener{

        /**
         * 选择房间
         * @param devicePosition
         */
        void onClickHeader(int devicePosition);

        /**
         * 新建房间
         * @param devicePosition
         */
        void onClickNormal(int devicePosition);
    }

    /**
     * 设置点击监听
     * @param listener
     */
    public void setOnClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }



    /**
     * 将数据与view绑定
     * @param holder
     * @param myItem
     * @param position 点击位置
     */
    public abstract void bindData(BaseViewHolder holder, MyItem myItem,int position);
    /**
     * 将数据与view绑定
     * @param holder
     */
    public abstract void bindHeaderData(BaseViewHolder holder);

    /**
     * 是否有空View显示
     * @return
     */
    public abstract boolean hasEmptyView();

    @Override
    public int getItemCount() {
        if (hasEmptyView()){
            return mList == null|| mList.size() == 0?1:mList.size();
        }
        else{
            return mList == null?0:mList.size();
        }
//        if (mList.size() == 0) {
//            return 1;
//        }
//        else{
//            return mList==null?0:mList.size();
//        }

    }

    /**
     * 设置headView是否需要单独一列
     * @return
     */
    public abstract boolean isHeaderOnlyLine();

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (isHeaderOnlyLine()){
            if(manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return getItemViewType(position) == VIEW_TYPE_HEADER
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> views;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            views = new SparseArray<>();
        }

        /**
         * 获取id所对应的控件
         * @param id
         * @return
         */
        public View getView(int id){
            View view = views.get(id);
            if (view==null){
                view = itemView.findViewById(id);
                views.put(id,view);
            }
            return view;
        }
    }

    public class MyItem{
        public T t;
        private boolean isSelect;
        private int type;

        public MyItem(T t, boolean isSelect, int type) {
            this.t = t;
            this.isSelect = isSelect;
            this.type = type;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "MyItem{" +
                    "t=" + t +
                    ", isSelect=" + isSelect +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
