package com.fykj.dcoreview.model.keeplive;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;


/**
 * Created by dml on 2018/3/3.
 * 后台进程保活管理类
 * 1.锁屏，解锁 保留1像素 (仿手Q)
 *   目的：在大多数国产手机下，进入锁屏状态一段时间，省电机制会kill后台进程
 *
 * 2.设置前台服务 (仿微信后台保活) 无效
 *   目的：解决点击home键使app长时间停留在后台，内存不足被kill
 *
 */

public class KeepLiveManager{

    /**
     * 前台进程的NotificationId
     */
    private final static int GRAY_SERVICE_ID = 1001;

    /**
     * 单例模式
     */
    private static KeepLiveManager instance = new KeepLiveManager();

    /**
     * 1像素的透明Activity
     */
    private PixelActivity activity;

    /**
     * 监听锁屏/解锁的广播（必须动态注册）
     */
    private LockReceiver lockReceiver;

    public static KeepLiveManager getInstance(){
        return instance;
    }

    /**
     * 传入1像素的透明Activity实例
     * @param activity
     */
    public void setKeepLiveActivity(PixelActivity activity){
        this.activity = activity;
    }

    /**
     * 注册锁屏/解锁广播
     * @param context
     */
    public void registerReceiver(Context context){
        lockReceiver = new LockReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(lockReceiver,filter);
    }

    /**
     * 注销锁屏/解锁广播
     * @param context
     */
    public void unRegisterReceiver(Context context){
        if(lockReceiver!=null){
            context.unregisterReceiver(lockReceiver);
        }
    }

    /**
     * 设置服务为前台服务
     * @param service
     */
    public void setServiceForeground(Service service){
        //设置service为前台服务，提高优先级
        if (Build.VERSION.SDK_INT < 18) {
            //Android4.3以下 ，此方法能有效隐藏Notification上的图标
            service.startForeground(GRAY_SERVICE_ID, new Notification());
        } else if(Build.VERSION.SDK_INT>18 && Build.VERSION.SDK_INT<25){
            //Android4.3 - Android7.0，此方法能有效隐藏Notification上的图标
            Intent innerIntent = new Intent(service, GrayInnerService.class);
            service.startService(innerIntent);
            service.startForeground(GRAY_SERVICE_ID, new Notification());
        }else{
            //Android7.1 google修复了此漏洞，暂无解决方法（现状：Android7.1以上app启动后通知栏会出现一条"正在运行"的通知消息）
            service.startForeground(GRAY_SERVICE_ID, new Notification());
        }
    }

    private void startLiveActivity(Context context){
        Intent intent = new Intent(context,PixelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void destroyLiveActivity(){
        if(activity!=null){
            activity.finish();
        }
    }

    class LockReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Intent.ACTION_SCREEN_OFF:  //锁屏
                    startLiveActivity(context);
                    break;
                case Intent.ACTION_USER_PRESENT: //解锁
                    destroyLiveActivity();
                    break;
            }
        }
    }

    /**
     * 辅助Service
     */
    public static class GrayInnerService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
