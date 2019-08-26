package com.example.dezhiwang.studycollection.Anim;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dezhiwang.studycollection.R;

public class AnimDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        initUI();
    }

    private void initUI() {
        final Button mBtnValue = findViewById(R.id.bt_value);
        mBtnValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //属性动画 valueAnimator
                ValueAnimator valueAnimator = ValueAnimator.ofInt(mBtnValue.getLayoutParams().width, 500);
                valueAnimator.setDuration(2000);

                //插值器 ---设置动画效果
                Interpolator overshootInterpolator = new BounceInterpolator();
                valueAnimator.setInterpolator(overshootInterpolator);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currentValue = (int) animation.getAnimatedValue();
                        mBtnValue.getLayoutParams().width = currentValue;
                        mBtnValue.requestLayout();
                    }
                });

                valueAnimator.start();
            }
        });
        final Button mBtnObject = findViewById(R.id.bt_object);
        mBtnObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator scale = ObjectAnimator.ofFloat(mBtnObject, "scaleX", 1, 2,1);
                scale.setDuration(2000);
                scale.start();
            }
        });
    }
}
