package com.fykj.dcoreview.model.TestAndVerify.imageSummary.module;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by administrator on 2018/8/28.
 */
public class ProgressInterceptor  implements Interceptor {

    /**
     * 使用了一个Map来保存注册的监听器，Map的键是一个URL地址。
     * 因为你可能会使用Glide同时加载很多张图片，而这种情况下，必须要能区分出来每个下载进度的回调到底是对应哪个图片URL地址的
     **/
    static final Map<String, ProgressListener> LISTENER_MAP = new HashMap<>();

    public static void addListener(String url, ProgressListener listener) {
        LISTENER_MAP.put(url, listener);
    }

    public static void removeListener(String url) {
        LISTENER_MAP.remove(url);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        Response newResponse = response.newBuilder().body(new ProgressResponseBody(url, body)).build();
        return newResponse;
    }
}
