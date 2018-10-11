package com.bbd.baselibrary.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Author:Ariana Wong
 * Date: 2016/12/22
 * Desctiption:
 */

public class HandlerUtil extends Handler{
    private static HandlerUtil instance;

    public static HandlerUtil getInstance() {
        if (instance == null)
            synchronized (HandlerUtil.class) {
                if (instance == null)
                    instance = new HandlerUtil();
            }
        return instance;
    }

    private HandlerUtil(){
        super(Looper.getMainLooper());
    }
}
