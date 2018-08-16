package com.fykj.dcoreview.model.TestAndVerify;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;

import com.fykj.dcoreview.R;
import com.fykj.dcoreview.base.BaseActivity;
import com.fykj.dcoreview.service.MyIntentService;
import com.fykj.dcoreview.service.MyService;
import com.socks.library.KLog;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by administrator on 2018/8/2.
 * 验证service
 */

public class ServiceTestActivity extends BaseActivity {

    private boolean isBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_service_test);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("Service总结");
    }

    @OnClick({R.id.startBt, R.id.stopBt, R.id.bindBt, R.id.unBindBt,R.id.start2Bt, R.id.stop2Bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startBt:
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                break;
            case R.id.stopBt:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bindBt:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, myConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unBindBt:
                //多次调用unBindService会报异常
                if (isBind) {
                    unbindService(myConnection);
                }
                break;
            case R.id.start2Bt:  //IntentService 启动
                Intent serviceIntent = new Intent(this, MyIntentService.class);
                startService(serviceIntent);
                break;
            case R.id.stop2Bt:   //IntentService 停止
                Intent serviceIntent2 = new Intent(this, MyIntentService.class);
                stopService(serviceIntent2);
                break;
        }
    }

    private ServiceConnection myConnection = new ServiceConnection() {

        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            KLog.d("MyService", "onServiceConnected()");
            isBind = true;
            MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
            myBinder.connectTest();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            KLog.d("MyService", "onServiceDisconnected()");
            isBind = false;
        }
    };

}
