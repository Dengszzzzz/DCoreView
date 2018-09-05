package com.fykj.dcoreview.view.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by dengzh on 2018/4/10.
 * 封装WebView
 * 1.解决大图片左右滑动问题，图片适应屏幕大小
 * 2.解决 loadData 中文乱码问题
 *
 * 注：如果要加载url，建议用AgentWeb加载效果更佳
 */

public class MyWebView extends WebView{

    public MyWebView(Context context) {
        super(context);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        getSettings().setJavaScriptEnabled(true);//支持js
        getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        setWebViewClient(new MyWebViewClient());
    }

    /**
     * 加载数据
     * @param data
     */
    public void loadData(String data){
       //loadData(data,"text/html","utf-8");  //这种写法无法解决中文乱码
        loadData(CSS_STYLE + data, "text/html; charset=UTF-8", null);//这种写法可以正确解码
    }

    /**
     * 修改样式
     * 字体颜色设为白色, “p”标签内的字体颜色  “*”定义了字体大小以及行高；
     * */
    private final String CSS_STYLE ="<style>* {font-size:16px;line-height:20px;}p {color:#666666;}</style>";

    /**
     * 修改图片宽高
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                    "}" +
                    "})()");//重置webview中img标签的图片大小
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
