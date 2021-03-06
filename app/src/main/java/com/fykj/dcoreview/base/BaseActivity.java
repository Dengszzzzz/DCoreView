package com.fykj.dcoreview.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.utils.AppManager;
import com.fykj.rxjava.rxbus.RxBus;
import com.fykj.rxjava.rxbus.RxEvents;
import com.just.agentweb.LogUtils;
import com.socks.library.KLog;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by dengzh on 2018/4/18.
 */

public abstract class BaseActivity extends FragmentActivity{

    protected String TAG = getClass().getSimpleName();  //class中获取的this，和new的对象有关，比如这个是得到子类的class

    //标题栏
    protected RelativeLayout titleBar;
    protected ImageView ivBack,ivRight;
    protected TextView tvBack,tvTitle,tvRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //加入activity管理器
        AppManager.getAppManager().addActivity(this);
        KLog.d("创建界面");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);   //将当前Activity移除出容器
        KLog.d("移除界面");
       // RxBus.getIntanceBus().unSubscribe(this);   //RxBus解绑
    }

    /**
     * 初始化标题
     */
    protected void initTitle(){
        titleBar = findViewById(R.id.titleBar);
        ivBack =  findViewById(R.id.ivBack);
        tvBack = findViewById(R.id.tvBack);
        tvTitle =  findViewById(R.id.tvTitle);
        ivRight =  findViewById(R.id.ivRight);
        tvRight =  findViewById(R.id.tvRight);
        ivBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转 减少重复代码
     * @param tarActivity 目标activity
     */
    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }


    /**
     * 注册RxBus
     */
//    public <T> void registerRxBus(Consumer<RxEvents> action) {
//        Disposable disposable = RxBus.getIntanceBus().doSubscribe(RxEvents.class, action, new Consumer<Throwable>() {
//            @Override
//            public void accept(@NonNull Throwable throwable) throws Exception {
//                KLog.e("RxBusAccept", throwable.toString());
//            }
//        });
//        RxBus.getIntanceBus().addSubscription(this,disposable);
//    }
}
