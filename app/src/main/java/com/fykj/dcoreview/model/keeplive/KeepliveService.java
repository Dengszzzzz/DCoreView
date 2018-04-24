package com.fykj.dcoreview.model.keeplive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.socks.library.KLog;

/**
 * Created by carmelo on 2018/3/16.
 * 来自：
 *   https://github.com/08carmelo/android-keeplive
 *   https://www.jianshu.com/p/53c4d8303e19
 */

public class KeepliveService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        KeepLiveManager.getInstance().registerReceiver(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KeepLiveManager.getInstance().setServiceForeground(this);
        KLog.d("keeplive","KeepliveService process = " + android.os.Process.myPid());
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        KeepLiveManager.getInstance().unRegisterReceiver(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
