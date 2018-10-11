package com.bbd.baselibrary.nets;

import android.content.Context;
import android.util.Log;

import com.bbd.baselibrary.nets.converters.GsonConverterFactory;
import com.bbd.baselibrary.utils.LogUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * created by Damon on 2017/7/4 9:20
 * <p>
 * description:
 */
public abstract class BaseNet<T> {
    private T api;
    private T customApi;
    private Class<T> clazz;
    private OkHttpClient okHttpClient;
    private String token = "";
    private String deviceId;
    private long userId;
    private Context mContext;

    @SuppressWarnings("FieldCanBeLocal")
    private InputStream mCertificate;

    private Converter.Factory converterFactory;
    private CallAdapter.Factory rxJavaCallAdapterFactory;

    protected BaseNet() {
        converterFactory = GsonConverterFactory.create();
        rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
        clazz = getApiClazz();
        mContext = getContext();
    }

    public T getApi() {
        if (api == null) {
            initHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            api = retrofit.create(clazz);
        }
        return api;
    }

    public T getFaceApi() {
        if (customApi == null) {
            initHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            customApi = retrofit.create(clazz);
        }
        return customApi;
    }

    private void initHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(60, TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间

                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request;
                                request = chain.request().newBuilder()
                                        .addHeader("Authorization", token)
                                        .addHeader("From", "App")
                                        .addHeader("X-Requested-With", "XMLHttpRequest")
                                        .build();
                            request = dealRequest(request);

                            LogUtil.e("xieyi request" + request);
                            Response response = null;
                            try {
                                response = chain.proceed(request);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            LogUtil.e("xieyi response=" + response);
                            dealResponse(response);
                            return response;
                        }
                    });
            if (isNeedHttps()) {
                builder.sslSocketFactory(HTTPSUtils.getSSLSocketFactory1(mContext))
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        });
            }
            okHttpClient = builder.build();
        }
    }

    protected abstract String getSfzh();

    protected abstract String getCurrentToken();


    protected abstract long getCurrentUserId();

    protected void dealResponse(Response response) {
    }

    /**
     * 若需要使用https请求,请设置证书信息
     */
    private boolean isNeedHttps() {
        if (getBaseUrl().contains("https://")) {
            return true;
        } else {
            return false;
        }
    }

    protected void setCertificateInputStream(InputStream certificate) {
        mCertificate = certificate;
    }

    public void setToken(String token) {
        this.token = token;
        clear();
    }


    public String getToken() {
        return token;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    private void clear() {
        okHttpClient = null;
        api = null;
    }

    protected abstract Class<T> getApiClazz();

    protected abstract String getBaseUrl();

    protected Request dealRequest(Request request) {
        return request;
    }

    protected abstract Context getContext();
}
