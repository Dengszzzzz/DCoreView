package com.fykj.dcoreview.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by administrator on 2018/7/9.
 */

public class TimeUtils {

    private static final String TAG = "TimeUtil";

    //日期格式
    public static final String FORMAT_DATE_EN = "yyyy-MM-dd";
    public static final String FORMAT_DATE_CN = "yyyy年MM月dd日";
    public static final String FORMAT_TIME_CN = "yyyy年MM月dd HH时mm分ss秒";
    public static final String FORMAT_TIME_CN_2 = "yyyy年MM月dd HH时mm分";
    public static final String FORMAT_TIME_EN = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TIME_EN_2 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DAY_CN = "HH时mm分ss秒";
    public static final String FORMAT_DAY_CN_2 = "HH时mm分";
    public static final String FORMAT_DAY_EN = "HH:mm:ss";
    public static final String FORMAT_DAY_EN_2 = "HH:mm";
    public static final String FORMAT_DAY_EN_3 = "mm:ss";

    //默认时间格式
    /**
     * G 年代标志符
     y 年
     M 月
     d 日
     h 时 在上午或下午 (1~12)
     H 时 在一天中 (0~23)
     m 分
     s 秒
     S 毫秒
     E 星期
     D 一年中的第几天
     F 一月中第几个星期几
     w 一年中第几个星期
     W 一月中第几个星期
     a 上午 / 下午 标记符
     k 时 在一天中 (1~24)
     K 时 在上午或下午 (0~11)
     z 时区
     * */
    private static final SimpleDateFormat SDF = new SimpleDateFormat(FORMAT_TIME_EN, Locale.CHINA);

    /**
     * 在之前
     */
    public static final int TIME_BEFORE = 1;
    /**
     * 在中间
     */
    public static final int TIME_ING = 2;
    /**
     * 在之后
     */
    public static final int TIME_AFTER = 3;



    /**
     * 默认SDF 的时间格式
     * @param timeFormat  时间格式
     * @param date        Date日期
     * @return
     */
    private static String format(String timeFormat, Date date) {
        SDF.setTimeZone(TimeZone.getDefault());   //设置默认时区
        SDF.applyPattern(timeFormat);
        return SDF.format(date);
    }

    /**
     * 将long型时间戳转为固定格式的时间字符串
     *
     * @param timeFormat 时间格式    如：FORMAT_DATE_EN
     * @param timestamp  时间戳      如：1532506463657
     * @return string时间格式  如：2018-07-25
     */
    public static String convertTime(String timeFormat, long timestamp) {
        try {
            Date date = new Date(timestamp);
            return format(timeFormat, date);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * 将String类型时间转为long类型时间
     *
     * @param timeformat 解析格式
     * @param timestamp  yyyy-MM-dd HH:mm:ss
     * @return 时间
     */
    public static long covertToLong(String timeformat, String timestamp) {
        try {
            Date date = SDF.parse(timestamp);
            return date.getTime();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * 将Date型时间转为固定格式的时间字符串
     *
     * @param timeformat 时间格式
     * @param date       时间
     * @return timeformat
     */
    public static String convertToTime(String timeformat, Date date) {
        return format(timeformat, date);
    }

    /**
     * 将Calendar型时间转为固定格式的时间字符串
     *
     * @param timeformat 时间格式
     * @param calendar   时间
     * @return timeformat
     */
    public static String convertToTime(String timeformat, Calendar calendar) {
        return format(timeformat, calendar.getTime());
    }



    /**
     * 计算上一个时间离当前时间间隔
     *
     * @param timestamp   时间戳
     * @param includeAfter 是否包括后面的时间
     * @return 刚刚  x分钟  一天内  ...
     */
    public static String intervalTime(long timestamp, boolean includeAfter) {
        String timeStr;

        long interval = (System.currentTimeMillis() - timestamp) / 1000;
        if (!includeAfter || interval >= 0) {
            if (interval <= 60) { //1分钟内 服务端的时间 可能和本地的有区别 所以小于0的 对于这个情况全部都显示刚刚
                timeStr = "刚刚";
            } else if (interval < 60 * 60) { // 1小时内
                timeStr = (interval / 60 == 0 ? 1 : interval / 60) + "分钟前";
            } else if (interval < 24 * 60 * 60) { // 一天内
                timeStr = (interval / 60 * 60 == 0 ? 1 : interval / (60 * 60)) + "小时前";
            } else if (interval < 30 * 24 * 60 * 60) { // 天前
                timeStr = interval / 24 * 60 * 60 == 0 ? "昨天" : interval / (24 * 60 * 60) + "天前";
            } else {
                Date date = new Date();
                date.setTime(timestamp);

                timeStr = format(FORMAT_DATE_CN, date);
            }
        } else {
            return intervalAfterTime(timestamp);
        }

        return timeStr;
    }

    /**
     * int型时间转换 比较距离结束
     *
     * @param timestamp 时间
     * @return 刚刚  x分钟  一天后  ...
     */
    public static String intervalAfterTime(long timestamp) {
        String timeStr;
        long interval = (timestamp - System.currentTimeMillis()) / 1000;
        if (interval < 0) {  //如果小于0，则不再进行往下执行
            return "0";
        }
        if (interval <= 60) { //1分钟内 服务端的时间 可能和本地的有区别 所以小于0的 对于这个情况全部都显示刚刚
            timeStr = interval + "秒后";
        } else if (interval < 60 * 60) { // 1小时内
            timeStr = (interval / 60 == 0 ? 1 : interval / 60) + "分钟后";
        } else if (interval < 24 * 60 * 60) { // 一天内
            timeStr = (interval / 60 * 60 == 0 ? 1 : interval / (60 * 60)) + "小时后";
        } else if (interval < 30 * 24 * 60 * 60) { // 天前
            timeStr = (interval / 24 * 60 * 60 == 0 ? 1 : interval / (24 * 60 * 60)) + "天后";
        } else if (interval < 12 * 30 * 24 * 60 * 60) { // 月前
            timeStr = (interval / 30 * 24 * 60 * 60 == 0 ? 1 : interval / (30 * 24 * 60 * 60)) + "月后";
        } else if (interval < 12 * 30 * 24 * 60 * 60) { // 年前
            timeStr = (interval / 12 * 30 * 24 * 60 * 60 == 0 ? 1 : interval / (12 * 30 * 24 * 60 * 60)) + "年后";
        } else {
            Date date = new Date();
            date.setTime(interval);

            timeStr = format(FORMAT_DATE_CN, date);
        }

        return timeStr;
    }





    /**
     * long型时间转换
     *
     * @param longTime 长整型时间
     * @return 2013年7月3日 18:05(星期三)
     */
    public static String convertDayOfWeek(String timeFormat, long longTime) {
        Calendar c = Calendar.getInstance(); // 日历实例
        c.setTime(new Date(longTime));

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String h = hour > 9 ? String.valueOf(hour) : "0" + hour;
        int minute = c.get(Calendar.MINUTE);
        String m = minute > 9 ? String.valueOf(minute) : "0" + minute;
        return String.format(Locale.getDefault(), timeFormat, year, month + 1, date, h, m, converToWeek(c.get(Calendar.DAY_OF_WEEK)));
    }

    /**
     * 转换数字的星期为字符串的
     *
     * @param w 星期
     * @return 星期x
     */
    private static String converToWeek(int w) {
        String week = null;

        switch (w) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }

        return week;
    }

    /**
     * 计算时间是否在区间内
     *
     * @param time  time
     * @param time1 time
     * @param time2 time
     */
    public static int betweenTime(long time, long time1, long time2) {
        if (time1 > time2) {  //时间1大
            long testTime = time1;
            time1 = time2;
            time2 = testTime;
        }

        //已经过去
        if (time1 > time) {
            return TIME_BEFORE;
        } else if (time2 < time) {
            return TIME_AFTER;
        } else {
            return TIME_ING;
        }
    }

}
