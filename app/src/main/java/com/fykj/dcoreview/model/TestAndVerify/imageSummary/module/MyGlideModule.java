package com.fykj.dcoreview.model.TestAndVerify.imageSummary.module;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by administrator on 2018/8/28.
 * Glide自定义模块功能
 * 看源码，会去AndroidMainfest中遍历出所有的GlideModule
 *
 * 使用方法： 在清单文件中配置一下
 * <manifest>
 ...
 <application>
 <meta-data
 android:name="com.example.glidetest.MyGlideModule"
 android:value="GlideModule" />
 ...
 </application>
 </manifest>
 *
 */
public class MyGlideModule implements GlideModule {

    /**
     * 更改Glide和配置
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //举例：所有Glide加载的图片都会缓存到SD卡上
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
    }

    /**
     * 替换Glide组件
     * 举例：可以把HttpURLConnection 替换成 okhttp3
     * 【官方也有替换 HTTP组件的替换方式】
     * @param context
     * @param glide
     */
    @Override
    public void registerComponents(Context context, Glide glide) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory(okHttpClient));


        //glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory());
    }
}
