package com.bbd.baselibrary.utils;

import android.view.View;

/**
 *
 *created by Damon on 2017/7/4 10:01
 *
 *description: 
 *
 */

public class ViewUtils {

    public static void showView(View view){
        view.setVisibility(View.VISIBLE);
    }

    public static void hideView(View view){
        view.setVisibility(View.GONE);
    }

    public static void enableView(View view){
        view.setEnabled(true);
    }

    public static void disableView(View view){
        view.setEnabled(false);
    }

}
