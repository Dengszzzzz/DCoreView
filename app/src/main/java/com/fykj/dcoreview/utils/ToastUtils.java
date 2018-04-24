package com.fykj.dcoreview.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.fykj.dcoreview.base.App;


/**
 * Created by dengzh on 2016/2/15 0015.
 */
public class ToastUtils {

    public static void showToast(String text) {
        if(AppUtils.isUiThread()){
            showToastAvoidRepeated(text);
        }
    }

    public static void showToast(int resId) {
        if(AppUtils.isUiThread()){
            showToastAvoidRepeated(resId);
        }
    }


    /**
     * 避免Toast重复显示
     * 之前显示的内容
     */
    private static String oldMsg;
    private static Toast toast   = null;
    private static long  oneTime = 0;
    private static long  twoTime = 0;
    public static void showToastAvoidRepeated(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.ctx, message, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToastAvoidRepeated(int res) {
        if (TextUtils.isEmpty(App.ctx.getString(res))) {
            return;
        }
        String message = App.ctx.getString(res);
        if (message != null) {
            if (toast == null) {
                toast = Toast.makeText(App.ctx, message, Toast.LENGTH_SHORT);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (message.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                        toast.show();
                    }
                } else {
                    oldMsg = message;
                    toast.setText(message);
                    toast.show();
                }
            }
            oneTime = twoTime;
        }
    }

}
