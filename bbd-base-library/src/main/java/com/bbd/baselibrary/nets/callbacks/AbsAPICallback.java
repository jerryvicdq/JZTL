package com.bbd.baselibrary.nets.callbacks;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bbd.baselibrary.nets.exceptions.ApiException;
import com.bbd.baselibrary.nets.exceptions.ResultException;
import com.bbd.baselibrary.utils.ActivityUtil;
import com.bbd.baselibrary.utils.LogUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * created by Damon on 2017/6/19 13:32
 * <p>
 * description:
 */
public abstract class AbsAPICallback<T> extends Subscriber<T> {

    //出错提示
    public final String networkMsg = "服务器开小差";
    public final String parseMsg = "数据解析出错";
    public final String unknownMsg = "网络错误";

    protected AbsAPICallback() {
    }


    @Override
    public void onError(Throwable e) {
        e = getThrowable(e);
        if (e instanceof HttpException) {//HTTP错误
//            if (((HttpException) e).code() == 401) {
            if (((HttpException) e).code() == -1) {
                resultError((HttpException) e);
            } else if (((HttpException) e).code() == 401) {
                resultError((HttpException) e);
            } else {
                error(e, ((HttpException) e).code(), networkMsg);
            }
        } else if (e instanceof SocketTimeoutException) {
            error(e, 12002, getSocketExceotion());
        } else if (e instanceof ResultException) {//服务器返回的错误
            ResultException resultException = (ResultException) e;
            if (!TextUtils.isEmpty(resultException.getData())) {
                error(e, resultException.getErrCode(), resultException.getData());
            } else {
                error(e, resultException.getErrCode(), resultException.getMessage());
            }
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {//解析错误
            error(e, ApiException.PARSE_ERROR, parseMsg);
        } else {//未知错误
            error(e, ApiException.UNKNOWN, unknownMsg);
        }
    }

    private String getSocketExceotion() {
        return "访问超时";
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private Throwable getThrowable(Throwable e) {
        Throwable throwable = e;
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        return e;
    }

    private void resultError(HttpException e) {
        if (e.code() == 401) {
            try {
                Context currentActivity = ActivityUtil.getCurrentActivity();
                if (currentActivity != null) {
                    Intent intent = new Intent(currentActivity, Class.forName("com.bbd.police.account.activities.LoginActivity"));
                    currentActivity.startActivity(intent);
                    Toast.makeText(currentActivity, "登录标识过期，请重新登录", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 错误信息回调
     */
    private void error(Throwable e, int errorCode, String errorMsg) {
        ApiException ex = new ApiException(e, errorCode);
        LogUtil.d(e.getMessage());
        ex.setDisplayMessage(errorMsg);
        onResultError(ex);
    }

    /**
     * 服务器返回的错误
     */
    protected abstract void onResultError(ApiException ex);

    @Override
    public void onCompleted() {
    }

}
