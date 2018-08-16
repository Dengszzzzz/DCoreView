package com.fykj.dcoreview.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.model.MainListActivity;
import com.socks.library.KLog;

/**
 * Created by administrator on 2018/8/2.
 */

public class MyService extends Service{

    private String TAG = "MyService";

    private MyBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d(TAG,"onCreate（）");
        binder = new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d(TAG,"onStartCommand（）");

        testForeground();
        //testThread();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        KLog.d(TAG,"onDestroy（）");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        KLog.d(TAG,"onBind（）");
        //返回实例
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        KLog.d(TAG,"onUnbind（）");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder{

        public void connectTest(){
            KLog.d(TAG,"Binder 的 connectTest() 方法执行");
        }

    }


    /**
     * 1.测试子线程
     * service运行在UI线程，如果要做耗时任务，需要新开线程工作，如下
     * 如果要开启子线程，可以使用IntentService
     */
    private void testThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                KLog.d(TAG,"开始service耗时任务");
                try {
                    Thread.sleep(1000 * 3);  //模拟耗时3s
                    KLog.d(TAG,"结束service的耗时任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 2.前台服务
     */
    private void testForeground(){
        //添加下列代码将后台Service变成前台Service
        //构建"点击通知后打开MainActivity"的Intent对象
        Intent notificationIntent = new Intent(this, MainListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        //新建Builder对象
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("通知标题");
        builder.setContentText("通知内容");
        builder.setSmallIcon(R.mipmap.hide_pwd_icon);
        builder.setContentIntent(pendingIntent);  ////设置点击通知后的操作

        Notification notification = builder.getNotification(); //将Builder对象转变成普通的notification
        startForeground(1,notification);
    }
}
