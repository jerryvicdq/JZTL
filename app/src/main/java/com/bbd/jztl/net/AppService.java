package com.bbd.jztl.net;


import android.text.Editable;

import com.bbd.jztl.account.bean.LoginResponseBean;
import com.bbd.jztl.main.User;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类描述：
 * 创建人：xieyi
 * 创建时间：2017/7/7 14:44
 */

public class AppService {

    private static AppService sInstance;
    private AppNets mNet;

    private AppService() {
        mNet = AppNets.getInstance();
    }

    public static AppService getInstance() {
        if (sInstance == null) {
            sInstance = new AppService();
        }
        return sInstance;
    }

    public Observable<LoginResponseBean> login(String name, String pwd) {
        return mNet.getApi().login(name, pwd).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> getUser() {
        return mNet.getApi().getUser().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
