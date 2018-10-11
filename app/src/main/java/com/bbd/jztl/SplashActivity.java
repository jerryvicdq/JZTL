package com.bbd.jztl;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.bbd.baselibrary.utils.IWeakHandler;
import com.bbd.baselibrary.utils.WeakHandler;
import com.bbd.jztl.account.LoginActivity;
import com.bbd.jztl.base.BaseBlueStatusActivity;


public class SplashActivity extends BaseBlueStatusActivity implements IWeakHandler {

    private WeakHandler mHandler;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mHandler = new WeakHandler(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, 3000);
    }


    private void startApp() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void handleMessage(Message msg) {

    }
}