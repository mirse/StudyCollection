package com.wdz.module_basis.widget.recyclerview.multi;

import android.content.Context;
import android.widget.LinearLayout;

import com.wdz.module_basis.R;
import com.wdz.module_basis.widget.recyclerview.universal.base.MultiSelectTypeAdapter;
import com.wdz.module_basis.widget.recyclerview.universal.base.MultiTypeAdapter;

import java.util.List;

/**
 * @Author dezhi.wang
 * @Date 2020/11/18 11:31
 */
public class MultiAdapter extends MultiSelectTypeAdapter<String> {
    public MultiAdapter(Context mContext, List list) {
        super(mContext, list);
    }

    @Override
    public void bindData(BaseViewHolder holder, String data, int position) {
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ll_root);
        if (getItemChecked(position)){
            linearLayout.setBackgroundColor(getContext().getColor(R.color.red_deep));
        }
        else{
            linearLayout.setBackgroundColor(getContext().getColor(R.color.blue_00aaee));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.recycler_item;
    }

    @Override
    public int getHeadLayoutId() {
        return 0;
    }

    @Override
    public int getEmptyLayoutId() {
        return 0;
    }
}
