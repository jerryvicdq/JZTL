package com.bbd.baselibrary.nets.converters;

import android.text.TextUtils;
import android.util.Log;

import com.bbd.baselibrary.nets.entities.BBDResultEntity;
import com.bbd.baselibrary.nets.exceptions.ResultException;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * created by Damon on 2017/7/4 9:19
 * <p>
 * description:
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type adapter;

    public GsonResponseBodyConverter(Gson gson, Type adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//        String rawResponse = value.string();
//        String response = rawResponse.replace("SUCCESS", 200 + "");
//        Log.e("WLH", "raw response string:"+ rawResponse);
//        if (response.contains("body_test") && response.contains("body")){
//            try {
//                response = decodeResult(response);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            LogUtil.i(response);
//            BBDResultEntity resultModel = gson.fromJson(response, BBDResultEntity.class);
//            if (resultModel.getCode() == 200 || "successed".equals(resultModel.getMsg())) {
//                if (resultModel.getData() != null && !TextUtils.equals(resultModel.getData().toString(), "null")) {
//                    if (resultModel.getData().isJsonArray()) {
//                        return gson.fromJson(response, adapter);
//                    }
//                    return gson.fromJson(resultModel.getData(), adapter);
//                }
//                return null;
//            } else {
//                throw new ResultException(resultModel.getCode(), resultModel.getMsg(), resultModel.getData().toString());
//            }
//        } finally {
//            value.close();
//        }

        String response = value.string();
        Log.e("xieyi", "raw response string :" + response);
        try {
            BBDResultEntity resultModel = gson.fromJson(response, BBDResultEntity.class);
            if (resultModel.getCode() == 0) {
                if (resultModel.getData() != null) {
                    if (resultModel.getData().isJsonArray()) {
                        return gson.fromJson(response, adapter);
                    }
                    return gson.fromJson(resultModel.getData(), adapter);
                }
                return null;
            } else {
                Log.e("convert: ", "出错了");
                throw new ResultException(resultModel.getCode(), resultModel.getMsg(), resultModel.getData() != null ? resultModel.getData().toString() : "");
            }
        } finally {
            value.close();
        }


    }


    private String decodeResult(String original) throws JSONException, UnsupportedEncodingException {
        JSONObject result = new JSONObject(original);
        if (result.has("salt") && result.has("body")) {
            String salt = result.getString("salt");
            String body = result.getString("body");
            if (!TextUtils.isEmpty(body)) {
                String key = DigestUtils.md5(salt + DigestUtils.E_KEY);
                String json = new String(Des3Utils.des3DecodeECB(key,
                        DigestUtils.decodeBase64(body.getBytes("utf-8"))),
                        "utf-8");
                if (json.length() > 0 && json.charAt(0) == '[') {
                    result.put("body", new JSONArray(json));
                } else {
                    result.put("body", new JSONObject(json));
                }
            }
        }

        return result.toString();
    }
}
