package com.wdz.studycollection.internet.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wdz.studycollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GlideActivity extends AppCompatActivity {

    @BindView(R.id.iv_glide)
    ImageView ivGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);
        initImg();
    }

    private void initImg() {
        Glide.with(this)
                .load("http://dmimg.5054399.com/allimg/pkm/pk/22.jpg")
                .into(ivGlide);
    }
}
