package com.fykj.dcoreview.base;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.utils.CrashHandler;
import com.fykj.dcoreview.view.recyclerView.MagazineConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.socks.library.KLog;

/**
 * Created by dengzh on 2018/4/18.
 */

public class App extends Application{

    public static Context ctx;  //上下文
    //屏幕参数
    public static int screenWidth;
    public static int screenHeight;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        initConfig();
    }

    private void initConfig(){
        //1.本地打印奔溃信息
        CrashHandler.getInstance().init(this);

        //2.屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        //3.Log依赖库
        KLog.init(true);

        initMzConfig();
    }


    public static MagazineConfig mzConfig;
    public static void initMzConfig(){
        mzConfig = new MagazineConfig();
        mzConfig.setItemWidth(screenWidth/3-40);
        mzConfig.setItemMargin(40);
        mzConfig.setBlankItemNum(2);  //空白item数量
        mzConfig.setScrollDuration(3000);
    }

}
