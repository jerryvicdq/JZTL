package com.bbd.jztl;

import android.app.Application;
import android.content.Context;

/**
 * 类描述：
 * 创建人：圈圈D
 * 创建时间：2018-10-10 13:02
 */
public class JZTLApplication extends Application {

    static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        //LeakCanary.install(this);
    }

    public static synchronized JZTLApplication context() {
        return (JZTLApplication) _context;
    }
}
