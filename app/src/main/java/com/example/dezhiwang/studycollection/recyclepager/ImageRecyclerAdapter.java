package com.example.dezhiwang.studycollection.recyclepager;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dezhi.wang on 2018/9/30.
 */

public class ImageRecyclerAdapter extends PagerAdapter {
    private final int[] images;
    private final Context context;
    private List<ImageView> imageViews;

    public ImageRecyclerAdapter(Context context, int[] images) {
        this.context=context;
        this.images=images;
        imageViews=new ArrayList<>();
        initImageViews(images);
    }

    private void initImageViews(int[] images) {
        for (int i=0;i<images.length;i++){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(images[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }


//  众所周知，ViewPager一般会维护2~3个页，如果只有3个ImageView,很可能出现这种情况，在生成完3个Page页后并准备生成第4个页面时，
//  本应该移除的最前面的页面还未被移除，系统就会报异常并且给你这样一个提示：You must call removeView() on the child's parent first
// （意思是让你先将最前面页的子View从最前面页移除掉，再将该子View添加到第4页中）。

//  解决办法：在只有1张图片时，不能滑动，暂不处理；如果有2~3张图片时，
//  递归增加至大于等于4个ImageView为止(下方为小编解决该问题的代码)。
        if(imageViews.size() > 1 && imageViews.size() < 4){
            initImageViews(images);
        }
    }

    @Override
    public int getCount() {
       // return imageViews.size()==0?0:Integer.MAX_VALUE;
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageView = imageViews.get(position % imageViews.size());
//        ViewParent parent = imageView.getParent();
//        if (parent!=null){
//            ViewGroup vp= (ViewGroup) parent;
//            vp.removeView(imageView);
//        }

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);//删掉 正向滑动不流畅
        //super.destroyItem(container, position%imageViews.size(), object);
    }


}
