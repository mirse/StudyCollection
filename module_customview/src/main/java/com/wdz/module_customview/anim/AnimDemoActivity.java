package com.wdz.module_customview.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.wdz.common.constant.ARouterConstant;
import com.wdz.module_customview.R;
import com.wdz.module_customview.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = ARouterConstant.ACTIVITY_ANIM_DEMO)
public class AnimDemoActivity extends AppCompatActivity {
    @BindView(R2.id.iv_add)
    ImageView ivAdd;
    @BindView(R2.id.cl_fab_add)
    ConstraintLayout clFabAdd;
    private Animation showAnimation;
    private Animation hideAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);
        ButterKnife.bind(this);
        initUI();
        initAnim();
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        showAnimation = AnimationUtils.loadAnimation(this,R.anim.fab_add_show);
        hideAnimation = AnimationUtils.loadAnimation(this,R.anim.fab_add_hide);
        showAnimation.setDuration(100);
        hideAnimation.setDuration(100);
        showAnimation.setFillAfter(true);
        hideAnimation.setFillAfter(true);
    }
    @OnClick({R2.id.iv_add,R2.id.cl_root})
    public void onClick(View view){
        if (R.id.iv_add == view.getId()){
            if (isOpenFabMore) {
                hideFab();
            } else {
                showFab();
            }
        }
        else if (R.id.cl_root == view.getId()){
            if (isOpenFabMore){
                hideFab();
            }

        }

    }
    private boolean isOpenFabMore = false;
    /**
     * 开启fab视图
     */
    private void showFab() {
        isOpenFabMore = true;
        ivAdd.setImageResource(R.mipmap.fab_add_pressed);
        clFabAdd.startAnimation(showAnimation);
    }

    /**
     * 关闭fab视图
     */
    private void hideFab() {
        isOpenFabMore = false;
        clFabAdd.startAnimation(hideAnimation);
        ivAdd.setImageResource(R.mipmap.fab_add);
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
