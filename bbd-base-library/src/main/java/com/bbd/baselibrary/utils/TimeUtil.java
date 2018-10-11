package com.bbd.baselibrary.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间转换相关方法
 */
@SuppressWarnings("ALL")
public class TimeUtil {
    public static final long minute = 60 * 1000; //分钟
    public static final long hour = 60 * minute; //小时
    public static final long day = 24 * hour;    //天
    public static final long week = 7 * day;     //周
    public static final long month = 31 * day;   //月
    public static final long year = 12 * month;  //年
    public static final String SIMPLE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 将time转换为 1970-1-1 00:00:00 格式的时间
     */
    public static String getAllTime(Date time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }

    /**
     * 将time转换为 1970-1-1 00:00:00 格式的时间
     */
    public static String getAllTimeNoSecond(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

    public static long ymdToLong(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 将time转换为 1970-1-1 格式的时间
     */
    public static String getYMdTime(long time) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1970-1-1 格式的时间
     */
    public static String getYMdTimeDot(long time) {
        if (time == 0) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1-1 格式的时间
     */
    public static String getMDTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00 格式的时间
     */
    public static String getHmTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00:00 格式的时间
     */
    public static String getHmsTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00:00 格式的时间
     */
    public static String getMsTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 获取最近时间根据差值确定时间显示的样式
     */
    public static String getRecentlyTime(long time) {
        if (time <= 0) {
            return null;
        }
        long diff = new Date().getTime() - time;
        long r = 0;
        if (diff > year) {
            return getYMdTime(time);
        }
        if (diff > day) {
            return getMDTime(time);
        }
        if (diff > hour) {
            if (diff - hour <= 3) {
                return getHmTime(time);
            }
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 获取年纪
     */
    public static int getAge(long time) {
        if (time <= 0) {
            return 0;
        }
        Date birthday = new Date();
        birthday.setTime(time);
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getDate(String data) {
        if (TextUtils.isEmpty(data)) {
            return "";
        }
        long time = getDataTime(data);
//        isToday(time)
//        int day = (int) ((getCurrentTime() - time) / 24 / 60 / 60 / 1000);
        if (isToday(time)) {
            return getHmTime(time);
        } else {
            return getYMdTime(time);
        }

    }

    public static String getSignTime(String data) {
        long time = getDataTime(data);
        int hour = (int) ((getCurrentTime() - time) / 60 / 60 / 1000);
        int second = (int) (((getCurrentTime() - time) % (60 * 60 * 1000)) / 60 / 1000);
        return hour + ":" + second;
    }

    public static long getDataTime(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_FORMAT);
            return Long.valueOf(String.valueOf(sdf.parse(data).getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.valueOf("");
    }

    public static boolean isToday(long time) {
        Date date = new Date(time);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) {//格式化为相同格式
            return true;
        } else {
            return false;
        }


    }

    public static String getTodayTime(String date) {
        long time = getDataTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date(cal.getTimeInMillis());
        cal.setTime(curDate);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);
        cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
        long dayTime = cal.getTimeInMillis();
        int day = (int) ((dayTime - time) / 24 / 60 / 60 / 1000);
        if (day < 1) {
            return getHmTime(time);
        } else {
            Date now = new Date(time);
            return sdf.format(now);
        }
    }

    public static String getCurrentTimeStr() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());

            String str = formatter.format(curDate);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }
}
