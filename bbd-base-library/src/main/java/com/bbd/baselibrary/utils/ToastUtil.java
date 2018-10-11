package com.bbd.baselibrary.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 *
 *created by Damon on 2017/6/1 10:52
 *
 *description: 
 *
 */

public class ToastUtil {

        // Toast
        private static Toast toast;
        public static Context context;

        public static void init(Application application) {
            context = application;
        }

        public static void showShort(CharSequence message) {
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
            }
            toast.show();
        }

        public static void showShort(int message) {
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                toast.setText(message);
            }
            toast.show();
        }

    /**
     * 调用系统的 Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
     * @param message
     */
    public static void showShort(final String message){
        ThreadExecutor.initial(context);
        if(ThreadExecutor.isOnMainThread())
            Toast.makeText(ThreadExecutor.getContext(), message, Toast.LENGTH_SHORT).show();
        else
            ThreadExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ThreadExecutor.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
    }

        public static void showLong(CharSequence message) {
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            } else {
                toast.setText(message);
            }
            toast.show();
        }

        public static void showLong(int message) {
            if (null == toast) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            } else {
                toast.setText(message);
            }
            toast.show();
        }

        public static void hideToast() {
            if (null != toast) {
                toast.cancel();
            }
        }
}
