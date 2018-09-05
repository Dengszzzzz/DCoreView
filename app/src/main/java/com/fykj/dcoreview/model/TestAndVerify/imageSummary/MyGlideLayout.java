package com.fykj.dcoreview.model.TestAndVerify.imageSummary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by administrator on 2018/8/28.
 * Glide 创建ViewTarget 的监听回调
 */
public class MyGlideLayout extends LinearLayout{

    private ViewTarget<MyGlideLayout, GlideDrawable> viewTarget;

    public MyGlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewTarget = new ViewTarget<MyGlideLayout, GlideDrawable>(this) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                MyGlideLayout myLayout = getView();
                myLayout.setImageAsBackground(resource);
            }
        };
    }

    public ViewTarget<MyGlideLayout, GlideDrawable> getTarget() {
        return viewTarget;
    }

    public void setImageAsBackground(GlideDrawable resource) {
        setBackground(resource);
    }

}
