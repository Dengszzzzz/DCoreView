package com.fykj.dcoreview.model.TestAndVerify.imageSummary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;

import com.socks.library.KLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by administrator on 2018/8/23.
 * 自定义 图片3级缓存例子演示
 * 内存 -> 文件 ->网络
 * 流程：
 * 1.创建LruCache<String,SoftReference<Bitmap>> 作为内存缓存容器，每次从文件或网络加载图片时，要加入缓存中
 *   LruCache有做算法优化。
 * 2.文件，得到缓存目录 getExternalCacheDir() 或 getCacheDir()，在缓存目录下找到文件，用BitmapFactory.decodeFile(xx)得到bitmap。
 *   并将bitmap放入lrucache中。
 *   getExternalCacheDir()： SDCard/Android/data/<application package>/cache/目录
 *   getCacheDir():          /data/data/<application package>/cache目录
 * 3.请求网络流数据，放入内存且保存file。
 */
public class MyImageLoad {

    private static MyImageLoad instance;
    private static Activity mContext;   //这里设为静态会出问题  生命周期，内存泄漏等
    /**
     * LruCache其实是一个Hash表，内部使用的是LinkedHashMap存储数据。
     * 使用LruCache类可以规定缓存内存的大小，并且这个类内部使用到了最近最少使用算法来管理缓存内存。
     * 这里定义 4M的大小作为缓存
     */
    private static LruCache<String,SoftReference<Bitmap>> mImageCache = new LruCache<>(1024 * 1024 * 4 );

    private MyImageLoad() {}

    private static MyImageLoad getInstance() {
        if(instance == null){
            synchronized (MyImageLoad.class){
                if(instance == null){
                    instance = new MyImageLoad();
                }
            }
        }
        return instance;
    }

    public static MyImageLoad with(Activity context){
        mContext = context;
        return getInstance();
    }

    public static void load(ImageView iv,String url){
        //1.从内存读取
        SoftReference<Bitmap> reference = mImageCache.get(url);
        Bitmap cacheBitmp;
        if(reference != null){
            cacheBitmp = reference.get();
            iv.setImageBitmap(cacheBitmp);
            KLog.d("MyImageLoad","内存中图片显示");
            return;
        }
        //2.从文件读取
        cacheBitmp = getBitmapFromFile(url);
        if(cacheBitmp!=null){
            //bitmap保存到内存
            mImageCache.put(url,new SoftReference<Bitmap>(cacheBitmp));
            iv.setImageBitmap(cacheBitmp);
            KLog.d("MyImageLoad","文件中图片显示");
            return;
        }
        //3.连网处理
        getBitmapFromUrl(iv,url);
    }

    /**
     * 从文件获取bitmap
     * @param url
     * @return
     */
    private static Bitmap getBitmapFromFile(String url){
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        //文件名一般要用Md5加密的。
        File file = new File(getCacheDir(),fileName);
        if(file.exists() && file.length()>0){
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }else{
            return null;
        }
    }

    /**
     * 找缓存目录
     * @return
     */
    private static File getCacheDir(){
        //考虑是否有sd卡
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
            return mContext.getExternalCacheDir();
        }
        return mContext.getCacheDir();
    }

    /**
     * 从网络获取bitmap
     * @param iv
     * @param url
     */
    private static void getBitmapFromUrl(final ImageView iv, final String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {

            }
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();//得到图片的流
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                saveBitmap(url,bitmap);
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    /**
     * 保存图片
     * @param url
     * @param bitmap
     */
    private static void saveBitmap(String url,Bitmap bitmap){
        //1.放入内存
        mImageCache.put(url,new SoftReference<Bitmap>(bitmap));
        //2.放入cache目录
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = new File(getCacheDir(),fileName);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(os!=null){
                try {
                    os.close();
                    os = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
