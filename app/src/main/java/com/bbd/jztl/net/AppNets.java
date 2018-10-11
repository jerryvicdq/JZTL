package com.bbd.jztl.net;

import android.content.Context;

import com.bbd.baselibrary.nets.BaseNet;
import com.bbd.jztl.JZTLApplication;

/**
 * 类描述：
 * 创建人：xieyi
 * 创建时间：2017/7/7 14:39
 */

public class AppNets extends BaseNet<AppApi> {

    private static AppNets sInstance;
    private static String sfzh="";

    private AppNets() {
    }

    @Override
    protected String getSfzh() {
        return sfzh;
    }

    public static AppNets getInstance() {
        if (sInstance == null) {
            sInstance = new AppNets();
        }
        return sInstance;
    }

    @Override
    protected String getCurrentToken() {
        return null;
    }

    @Override
    protected long getCurrentUserId() {
        return 0;
    }

    @Override
    protected Class<AppApi> getApiClazz() {
        return AppApi.class;
    }

    @Override
    protected String getBaseUrl() {
        return AppApi.baseUrl;
    }

    @Override
    protected Context getContext() {
        return JZTLApplication.context();
    }


}
