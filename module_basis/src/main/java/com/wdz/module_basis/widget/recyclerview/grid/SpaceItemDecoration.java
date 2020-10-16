package com.wdz.module_basis.widget.recyclerview.grid;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 设置左右间距 其余均分间距
 * @author dezhi.wang
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final String TAG = this.getClass().getSimpleName();
    private int leftMargin;
    private int rightMargin;
    private Context context;

    public SpaceItemDecoration(Context context,int leftMargin,int rightMargin) {
        this.context = context;
        this.leftMargin = dp2px(context,leftMargin);
        this.rightMargin = dp2px(context,rightMargin);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int spanCount = 0;
        if (parent.getLayoutManager()!=null){
            spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        }
        if (spanCount<2){
            return;
        }
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        int screenWidth = getScreenWidth(context);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int width = layoutParams.width;


        //每个item所占宽度
        int spanWidth = screenWidth/spanCount;

        //除头尾item后剩余宽度
        int remainWidth = (screenWidth-width*2 - leftMargin - rightMargin);
        //除头尾后剩余item间距值
        int remainWidthSpan = (remainWidth-width*(spanCount-2))/(spanCount-1);
        //首个item所剩余距离
        int firstRemain = spanWidth-leftMargin-width;

        //头item
        if (childLayoutPosition%spanCount == 0){
            outRect.left = leftMargin;
        }
        //尾item
        else if (childLayoutPosition%spanCount == spanCount-1){
            outRect.left = spanWidth-width-rightMargin;
        }
        //每行第2个item
        else if (childLayoutPosition%spanCount==1){
            outRect.left = remainWidthSpan-firstRemain;
        }
        else{
            //第一个item所剩余的距离值 spanWidth - (remainWidthSpan - firstRemain + width)
            outRect.left = remainWidthSpan - (spanWidth - (remainWidthSpan - firstRemain + width));
        }
    }


    /**
     * dp->px
     */
    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 获取屏幕宽度
     */
    private int getScreenWidth(Context context) {
        int width = 0;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        }
        return width;
    }
}
