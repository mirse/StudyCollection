package com.wdz.module_customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdz.module_customview.R;

/**
 * Created by dezhi.wang on 2018/9/27.
 */

public class TabGroup extends LinearLayout {


    private String mTabText;
    private int tabImage;

    public TabGroup(Context context) {
        this(context,null);
    }

    public TabGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.tab, this);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TabGroup, defStyleAttr, 0);
        mTabText = typedArray.getString(R.styleable.TabGroup_tab_text);
        tabImage = typedArray.getResourceId(R.styleable.TabGroup_tab_image,R.drawable.ic_launcher_background);

        typedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {


    }

    @Override
    protected void onFinishInflate() {//当View中所有的子控件均被映射成xml后触发
        super.onFinishInflate();
        TextView tabTextView = findViewById(R.id.tv_tab);
        ImageView tabImageView = findViewById(R.id.iv_tab);
        tabTextView.setText(mTabText);
        tabImageView.setBackgroundResource(tabImage);
    }
}
