package com.bbd.jztl.net;

import com.bbd.baselibrary.nets.entities.BBDPageListEntity;
import com.bbd.jztl.account.bean.LoginResponseBean;
import com.bbd.jztl.main.User;

import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 类描述：
 * 创建人：xieyi
 * 创建时间：2017/7/7 14:40
 */

public interface AppApi {

    //    String people = "-tj";
    String people = "";

    String BBD_USER = "bbd-user";
    String BBD_SEARCH = "bbd-search";
    String WEB_API = "web-api";


    String baseUrl = "http://192.168.1.134";


    //登录
    @POST("/api/auth/login")
    Observable<LoginResponseBean> login(@Query("police_code") String code, @Query("password") String pwd);

    //获取用户信息
    @POST("/api/auth/me")
    Observable<User> getUser();
}
