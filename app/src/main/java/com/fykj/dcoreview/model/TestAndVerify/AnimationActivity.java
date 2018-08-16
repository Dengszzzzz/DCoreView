package com.fykj.dcoreview.model.TestAndVerify;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.view.anim.PointAnimView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/8/10.
 * 逐帧,补间，属性动画对比
 * 1.逐帧动画
 * 1)在drawable文件夹下，写 animation-list,在布局文件ImageView的src设置该resId，
 * 再用iv.getDrawable()得到AnimationDrawable，再调用drawable.start()开启。
 * <p>
 * 2.补间动画
 * 1)单：在anim包下写anim资源文件，例如scale，大多数参数都是fromXX，toXX。再调用AnimationUtils.loadAnimation()得到Animation，
 * 调用iv.startAnimation(animation)开启动画;
 * 2)混合：在anim包下写anim资源文件，用set包裹scale,alpha等。调用方式和上述一致
 * 3)Interpolator 主要作用是可以控制动画的变化速率; pivot 决定了当前动画执行的参考位置
 * <p>
 * 3.属性动画
 * 1）ObjectAnimator对象，设置相关属性，调用start()开启动画。
 * 例如：ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(targetView,"scaleX", 0.0f, 1.0f);
 * 2)具体看PointAnimView类
 * 3）也可以用xml写属性动画，只是不推荐。
 * 4) Interpolator:插值器，控制动画速率，自定义需要重写getInterpolation(float input)。
 * 5）TypeEvaluator:估值器，自定义需要重写evaluate()方法，计算对象的属性值并将其封装成一个新对象返回。
 * <p>
 * 4.传统动画和属性动画对比
 * 1）属性动画，真正移动了view的位置。传统动画没有。
 * 2)属性动画，repeatCount设置为无限循环时，记得在onStop()将动画停止，否则内存泄漏
 * 3)属性动画不如xml实现的补间动画复用率高。
 */
public class AnimationActivity extends BaseActivity {

    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.mendingIv)
    ImageView mMendingIv;
    @BindView(R.id.compenceIv)
    ImageView mCompenceIv;
    @BindView(R.id.objectBt)
    Button mObjectBt;
    @BindView(R.id.pointAnimView)
    PointAnimView mPointAnimView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_animation);
        initTitle();
        tvTitle.setText("逐帧,补间，属性动画对比");
        ButterKnife.bind(this);

        //逐帧动画
        AnimationDrawable animationDrawable = (AnimationDrawable) mIv1.getDrawable();
        animationDrawable.start();

        //补间动画-单
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_single);
        mMendingIv.startAnimation(animation);

        //补间动画-组合
        Animation combinationAnim = AnimationUtils.loadAnimation(this, R.anim.anim_combination);
        mCompenceIv.startAnimation(combinationAnim);

        //属性动画
        //combinationObject();
        //runnable对象中的方法会在View的measure、layout等事件后触发
        //,解决onCreate()过程中获取View的width和Height为0的问题
        mPointAnimView.post(new Runnable() {
            @Override
            public void run() {
                mPointAnimView.startAnimation();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPointAnimView.stopAnimation();
    }

    private void RotateAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mObjectBt, "rotation", 0f, 360f);
        anim.setDuration(1000);
        anim.start();
    }

    private void AlphaAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mObjectBt, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f);
        animator.setRepeatCount(-1);  //无限循环
        animator.setRepeatCount(ObjectAnimator.REVERSE);
        animator.setDuration(2000);
        animator.start();
    }

    private void combinationObject() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mObjectBt, "alpha", 1.0f, 0.5f, 0.8f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(mObjectBt, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(mObjectBt, "scaleY", 0.0f, 2.0f);
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(mObjectBt, "rotation", 0, 360);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(mObjectBt, "translationX", 100, 400);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(mObjectBt, "translationY", 100, 750);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);   //同时执行
        //set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);  //按顺序执行
        set.setDuration(3000);
        set.start();
    }


}
