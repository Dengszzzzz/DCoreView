package com.fykj.dcoreview.model.TestAndVerify.imageSummary;

import com.bumptech.glide.load.model.GlideUrl;

/**
 * Created by administrator on 2018/8/27.
 * http://url.com/image.jpg?token=d9caa6e02c990b0a
 * 遇到token随时可能变化的，当两次token不一致，同一张图片就会缓存两次。例如：七牛云
 *
 * 问题解决方案：
 * 看源码
 * 1.HttpUrlFetcher.getId() 获取图片url
 * 2.getId()方法中又调用了GlideUrl的getCacheKey()方法。
 *   【glideUrl的来源是在load()方法中传入的图片url地址，然后Glide在内部把这个url地址包装成了一个GlideUrl对象】
 * 3.glideUrl的getCacheKey()就是缓存的key，只需修改这部分规则即可，这里是把token的值去掉。
 */
public class MyGlideUrl extends GlideUrl{

    private String mUrl;

    public MyGlideUrl(String url) {
        super(url);
        mUrl = url;
    }

    @Override
    public String getCacheKey() {
        return mUrl.replace(findTokenParam(), "");
    }

    private String findTokenParam() {
        String tokenParam = "";
        int tokenKeyIndex = mUrl.indexOf("?token=") >= 0 ? mUrl.indexOf("?token=") : mUrl.indexOf("&token=");
        if (tokenKeyIndex != -1) {
            int nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1);
            if (nextAndIndex != -1) {
                tokenParam = mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1);
            } else {
                tokenParam = mUrl.substring(tokenKeyIndex);
            }
        }
        return tokenParam;
    }
}
