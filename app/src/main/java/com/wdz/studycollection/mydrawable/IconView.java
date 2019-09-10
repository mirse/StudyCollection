package com.wdz.studycollection.mydrawable;

import android.content.Context;
import android.content.res.TypedArray;


import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdz.studycollection.R;


/**
 * Created by dezhi.wang on 2018/9/27.
 */

public class IconView extends RelativeLayout {


    private String mTabText;
    private int iconImage;
    private int iconNum;

    public IconView(Context context) {
        this(context,null);
    }

    public IconView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.icon_view, this);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ViewWithIcon, defStyleAttr, 0);
        iconNum = typedArray.getInt(R.styleable.ViewWithIcon_icon_num,1);
        iconImage = typedArray.getResourceId(R.styleable.ViewWithIcon_view_image,R.drawable.ic_launcher_background);

        typedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {


    }

    @Override
    protected void onFinishInflate() {//当View中所有的子控件均被映射成xml后触发
        super.onFinishInflate();
        TextView tabTextView = findViewById(R.id.textView2);
        ImageView tabImageView = findViewById(R.id.imageView);
        tabTextView.setText(Integer.toString(iconNum));
        tabImageView.setBackgroundResource(iconImage);
    }
}
