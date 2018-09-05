package com.fykj.dcoreview.model.TestAndVerify.imageSummary;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.App;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.model.TestAndVerify.imageSummary.module.ProgressInterceptor;
import com.fykj.dcoreview.model.TestAndVerify.imageSummary.module.ProgressListener;
import com.fykj.dcoreview.utils.ToastUtils;
import com.fykj.dcoreview.utils.glideUtils.GlideCircleTransform;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/8/27.
 * Glide 3.7 源码解析
 * 1.Glide.with(this).load(bitmapUrl).into(ImageView) 解析
 * 2.Glide缓存
 * 3.Glide回调与监听   SimpleTarget、ViewTarget、preload()、downloadOnly()、listener()。
 * 4.Glide图形变换     源码看CenterCrop，自定义实现看GlideCircleTransform，
 *                    具体使用推荐 https://github.com/wasabeef/glide-transformations
 * 5.Glide自定义模块功能，进度框加载   GlideModule
 *
 * Glide 4.0做了许多变动。
 */
public class GlideActivity extends BaseActivity {

    @BindView(R.id.glideIv)
    ImageView mGlideIv;
    @BindView(R.id.glideLl)
    MyGlideLayout mGlideLl;

    String bitmapUrl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
    String gifUrl = "http://p1.pstatp.com/large/166200019850062839d3";
    private String qiNiuUrl = "http://url.com/image.jpg?token=d9caa6e02c990b0a";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("Glide源码解析");
        //testTarget();
        testModule(gifUrl);
    }

    /**
     * Glide 普通用法
     * 基本使用如下
     * 1.各项功能加载load()和into()之间。
     * 2.asBitmap，设定强制指定加载静态图片
     * 3.会自动根据imageView的宽高，决定图片的大小，所以我们大多数情况不需要指定图片大小。
     */
    private void glide() {
        Glide.with(this)
                .load(gifUrl)
                .asBitmap()   //设定强制指定加载静态图片
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .diskCacheStrategy(DiskCacheStrategy.NONE) //设置不缓存
                //   .override(100,100)  //指定图片大小,只加载100*100像素尺寸，而不管imageView大小是多少
                .into(mGlideIv);
    }

    /**
     * 1.with()解析：
     *  目的：得到一个RequestManager对象
     * 1）传入参数可以分application(和应用程序生命周期绑定)和非application
     * 2) 非application的，添加一个隐藏fragment，因为fragment生命周期和activity同步的。
     * 3）如果在非主线程使用Glide，那么不管是传入activity还是fragment，都会强制转换成application处理。
     * */

    /**
     * 2.load()解析：
     *   目的：得到一个DrawableTypeRequest对象，详细看RequestManager类 和 DrawableTypeRequest类。
     *  1）看RequestManager的loadGeneric()方法，可以看到ModelLoader对象(用来加载图片的，load()传入参数不同，就会得到不同的ModelLoader对象)
     *     最后返回DrawableTypeRequest。
     *  2）DrawableTypeRequest，主要的就是它提供了asBitmap()和asGif()这两个方法。
     *  3）DrawableRequestBuilder，DrawableTypeRequest的父类，有Glide绝大多数的API，比如placeholder()、error()等。
     *     然后发现into()方法，返回的实际上是DrawableTypeRequest对象。
     * */

    /**
     * 3.into()解析：
     *   目的：整个Glide图片加载流程中最复杂的地方，找出最终发起网络请求，解析数据和显示view的地方。
     *   记住HttpUrlFetcher，HttpURLConnection请求；Downsampler.decode()解析；ImageViewTarget.onResourceReady()返回bitmap，setResource()显示即可。
     *
     *  1）GenericRequestBuilder.into(Y target)，看到buildRequest() 构建了一个Request对象，Request是用来发出加载图片请求的。
     *  2）GenericRequestBuilder.buildRequest()，可以看到load()中调用的API，都组装到这个Request对象中。
     *  4) RequestTracker.runRequest()，执行上面的Request，当前的Request对象是GenericRequest。
     *  5) GenericRequest.begin()，判断是否显示占位图，如果设置override()，会走onSizeReady() -> engine.load（）。
     *  7） Engine.load()部分代码处理缓存 -> EngineJob(主要作用是开启线程，为异步加载图片做准备) ->EngineRunnable.run() ->EngineRunnable.decode()
     *      -> DecodeJob.decodeFromSource() ->DecodeJob.decodeSource() ->ImageVideoFetcher.loadData() ->
     *      HttpUrlFetcher.loadData(),这里就是网络通讯的代码，可以看到使用的HttpURLConnection。
     *  8） 由HttpUrlFetcher.loadData()返回inputStream，接下来要做图片解析。DecodeJob.decodeSource() -> decodeFromSourceData() ->
     *      loadProvider.getSourceDecoder().decode(),也就是GifBitmapWrapperResourceDecoder.decode()。
     *      最终在Downsampler.decode()解析。
     *  9)  最终得到bitmap并设置在ImageView，具体可看ImageViewTarget.onResourceReady() 和 setResource()。
     * */


    /**
     * 4.Glide缓存
     * 缓存Key:参数很多，与url、signature、width、height等有关。
     * <p>
     * 内存缓存：
     * LruCache算法 和 弱引用 结合使用
     * 1）Engine.load(),可以看到loadFromCache()和loadFromActiveResources()获取缓存图片。前者是LruCache算法，后者是弱引用。
     * 2）从LruResourceCache中获取到缓存图片之后会将它从缓存中移除，并存储到activeResources当中。
     * 也就是缓存正在使用中的图片，保护这些图片不被LruCache算法回收掉。
     * 3）Engine.onEngineJobComplete() 里做了操作，写入弱引用缓存。
     * 4）Engine.onResourceReleased() 里将 弱引用缓存移除，写入LruCache缓存。
     * <p>
     * <p>
     * 硬盘缓存：
     * DiskLruCache，封装LruCache的工具类
     * 1)EngineRunnable.decode(),默认情况下Glide会优先从缓存当中读取，只有缓存中不存在要读取的图片时，才会去读取原始图片。
     * 2）先调转换后图片缓存，找不到再找原始图片缓存，原始图片缓存得到后，还要对数据进行转换。
     * 3）原图缓存的key只和url和signature有关
     */
    private void test() {
        //DiskCacheStrategy.NONE： 表示不缓存任何内容。
        //DiskCacheStrategy.SOURCE： 表示只缓存原始图片。
        //DiskCacheStrategy.RESULT： 表示只缓存转换过后的图片（默认选项）。
        //DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
        Glide.with(this)
                .load(new MyGlideUrl(qiNiuUrl))
                .skipMemoryCache(true)  //禁用内存缓存功能，默认是false，开启
                .diskCacheStrategy(DiskCacheStrategy.NONE)  //禁止硬盘缓存，默认是 DiskCacheStrategy.RESULT
                .into(mGlideIv);
    }


    /**
     * 5.Glide的回调与监听
     * 1) into()方法还能接收Target参数，其实传入ImageView，内部也构建了一个Target对象。
     * 2) 创建SimpleTarget，重写onResourceReady，可以获取bitmap且给ImageView设值。
     * 3) 创建ViewTarget,可以给其他类型的View设置图片
     */
    private void testTarget() {

        //1.SimpleTarget 的用法示例，只能给ImageView设置图片
        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                mGlideIv.setImageDrawable(resource);
            }
        };
        Glide.with(this)
                .load(bitmapUrl)
                .into(simpleTarget);

        //2.ViewTarget 的用法示例,可以给非ImageView设置图片
        Glide.with(this)
                .load(bitmapUrl)
                .into(mGlideLl.getTarget());
    }

    /**
     * 6.Glide预加载  加载图片到缓存，但暂时不需要显示图片。
     * 1)preload()不带参数，加载图片原始尺寸。另一个通过参数指定加载图片的宽和高。
     * 2)设置diskCacheStrategy(DiskCacheStrategy.SOURCE)，因为要加载原始尺寸，不然源码into()方法又去根据ImageView宽高加载一次。
     * 3）源码追寻到  GenericRequestBuilder.preload()，可以看到就是构建了个PreloadTarget给into方法，PreloadTarget是SimpleTarget的子类。
     * 查看PreloadTarget，可看onResourceReady()只是调了 Glide.clear(this)；代表加载完成，而没有做其他事情。
     */
    private void testPreload() {
        Glide.with(this)
                .load(bitmapUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .preload();
    }

    /**
     * 7.downloadOnly()  只下载图片，不进行图片加载  FutureTarget
     * 1)downloadOnly(int width, int height),用在子线程中下载图片
     * 2)downloadOnly(Y target),用在主线程中下载图片
     * 4）源码追寻 RequestFutureTarget -> doGet()。
     */
    private void testDownloadOnly() {

        //1.子线程下载
        new Thread(new Runnable() {
            @Override
            public void run() {
                FutureTarget<File> target = Glide.with(App.ctx)
                        .load(bitmapUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                //此处原图大小，到时显示时要设置DiskCacheStrategy.SOURCE 或 DiskCacheStrategy.ALL
                try {
                    final File imageFile = target.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showToast(imageFile.getPath());
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //2.主线程下载
        Glide.with(this).load(bitmapUrl).downloadOnly(new MyDownloadImageTarget());
    }


    /**
     * 8.listener()
     * 监听Glide加载图片的状态
     * 1)两个方法里返回值：
     *   返回false就表示这个事件没有被处理，还会继续向下传递。返回true就表示这个事件已经被处理掉了，从而不会再继续向下传递。
     *   例如：在onResourceReady()方法中返回了true，那么就不会再回调Target的onResourceReady()方法了。
     */
    private void testListener() {
        Glide.with(this)
                .load(bitmapUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(mGlideIv);
    }

    /**
     * 9.图形变换
     *   源码看CenterCrop
     *   继承BitmapTransformation,实现transform()和getId(),
     *   其中getId()要求返回一个唯一的字符串来作为id，以和其他的图片变换做区分。
     *   通常情况下，我们直接返回当前类的完整类名就可以了。
     * */
    private void testTransform(){
        Glide.with(this)
                .load(bitmapUrl)
                .transform(new GlideCircleTransform(this))
                .into(mGlideIv);
    }

    /**
     * 10.自定义模块 和 进度框加载
     */
    private void testModule(final String url){
        //设置进度条
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("加载中");
        //进度监听绑定
        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                progressDialog.setProgress(progress);
            }
        });

        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(new GlideDrawableImageViewTarget(mGlideIv) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        progressDialog.show();  //开始加载显示进度条
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressDialog.dismiss();  //成功后，隐藏进度条，且移除进度条监听
                        ProgressInterceptor.removeListener(url);
                    }
                });
    }



}
