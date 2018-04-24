package com.fykj.dcoreview.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.fykj.dcoreview.R;


/**
 * Created by dengzh on 2018/4/9.
 * 验证码帮助类
 */

public class CodeManager {

    private Context mContext;
    private TextView codeBt;     //button继承TextView
    private boolean canClick = true;
    private MyCountDown myCountDown;

    /**
     * 构造函数
     * @param mContext 上下文
     * @param codeBt   验证码的button
     */
    public CodeManager(Context mContext, TextView codeBt) {
        this.mContext = mContext;
        this.codeBt = codeBt;
        myCountDown = new MyCountDown(60 * 1000, 1000);
    }

    /**
     * 开启倒计时
     */
    public void start(){
        myCountDown.start();
    }

    /**
     * 界面销毁时 释放
     */
    public void stop(){
        handler.removeMessages(1);
        handler.removeMessages(2);
        myCountDown.cancel();
    }

    /**
     * 是否可以点击
     * @return
     */
    public boolean isCanClick(){
        return canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    /************* 倒计时 handler创建在主线程***************/

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: //可点击
                    canClick = true;
                    codeBt.setText("获取短信验证");
                    codeBt.setBackgroundResource(R.drawable.shape_code_clickable);
                    codeBt.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    break;
                case 2:  //不可点击
                    codeBt.setText(msg.obj + "秒后重新发送");
                    codeBt.setBackgroundResource(R.drawable.shape_code_not_clickable);
                    codeBt.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    break;
                default:
                    break;

            }
        }
    };

    /**
     * 定义一个倒计时的内部类
     * 倒计时结束后，会发送2到handler停止倒计时
     */
    class MyCountDown extends CountDownTimer {
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            handler.sendEmptyMessage(1);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Message msg = new Message();
            msg.what = 2;
            msg.obj = millisUntilFinished / 1000;
            handler.sendMessage(msg);
        }
    }

}
