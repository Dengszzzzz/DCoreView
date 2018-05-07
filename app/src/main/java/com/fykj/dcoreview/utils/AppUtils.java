package com.fykj.dcoreview.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;

/**
 * Created by dengzh on 2018/4/18.
 */

public class AppUtils {

    /**
     * 是否是UI线程
     * @return
     */
    public static boolean isUiThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 判断是否有SD卡
     *
     * @return true为有SDcard，false则表示没有
     */
    public static boolean hasSdcard() {
        boolean hasCard = false;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            hasCard = true;
        }
        return hasCard;
    }


}
