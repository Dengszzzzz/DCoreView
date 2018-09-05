package com.fykj.dcoreview.model.TestAndVerify.imageSummary;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.socks.library.KLog;

import java.io.File;

/**
 * Created by administrator on 2018/8/28.
 * downloadOnly(Y target) 最简单用法
 */
public class MyDownloadImageTarget implements Target<File> {

    private static final String TAG = "MyDownloadImageTarget";

    @Override
    public void onLoadStarted(Drawable placeholder) {

    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {

    }

    @Override
    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
        KLog.d(TAG, resource.getPath());
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {

    }

    @Override
    public void getSize(SizeReadyCallback cb) {
        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
    }

    @Override
    public void setRequest(Request request) {

    }

    @Override
    public Request getRequest() {
        return null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
