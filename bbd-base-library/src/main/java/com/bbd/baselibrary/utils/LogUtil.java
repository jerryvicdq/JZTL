package com.bbd.baselibrary.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * created by Damon on 2017/6/19 13:13
 * <p>
 * description:
 */

public class LogUtil {

    private static String TAG = "Jungle";
    public static boolean isDebug = true;

    public static void i(String s) {
        if (isDebug && !TextUtils.isEmpty(s))
            Log.i(TAG, s);
    }

    public static void i(String tag, String s) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s))
            Log.i(tag, s);
    }

    public static void e(String s) {
        if (isDebug && !TextUtils.isEmpty(s))
            Log.e(TAG, s);
    }

    public static void e(String tag, String s) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s))
            Log.e(tag, s);
    }

    public static void w(String s) {
        if (isDebug && !TextUtils.isEmpty(s))
            Log.w(TAG, s);
    }

    public static void w(String tag, String s) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s))
            Log.w(tag, s);
    }

    public static void d(String s) {
        if (isDebug && !TextUtils.isEmpty(s))
            Log.d(TAG, s);
    }

    public static void d(String tag, String s) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(s))
            Log.d(tag, s);
    }

}
