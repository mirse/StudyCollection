package com.example.dezhiwang.studycollection.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
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
                //属性动画 Property Animation
                //ValueAnimator 类是先改变值，然后 手动赋值 给对象的属性从而实现动画；是 间接 对对象属性进行操作；
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
                //ObjectAnimator类是先改变值，然后 自动赋值 给对象的属性从而实现动画；是 直接 对对象属性进行操作；
                ObjectAnimator scale = ObjectAnimator.ofFloat(mBtnObject, "scaleX", 1, 2,1);
                scale.setDuration(2000);
                scale.start();
            }
        });
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimationView);
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        lottieAnimationView.playAnimation();
    }
}
