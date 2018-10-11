package com.bbd.baselibrary.nets.entities;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.Set;

/**
 *
 *created by Damon on 2017/7/4 9:19
 *
 *description: 
 *
 */
public class InputErrorEntity {

    private JsonObject objectErrors;

    private JsonObject fieldErrors;

    private String errorMessage;

    public JsonObject getObjectErrors() {
        return objectErrors;
    }

    public void setObjectErrors(JsonObject objectErrors) {
        this.objectErrors = objectErrors;
    }

    public JsonObject getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(JsonObject fieldErrors) {
        this.fieldErrors = fieldErrors;
    }


    public String getMessage() {
        if (errorMessage != null) {
            return errorMessage;
        }
        try {
            if (fieldErrors != null) {
                Set<Map.Entry<String, JsonElement>> entrySet = fieldErrors.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    JsonElement jObject = fieldErrors.get(entry.getKey());
                    String[] datas = new Gson().fromJson(jObject, new TypeToken<String[]>() {
                    }.getType());
                    for (String s : datas) {
                        errorMessage = s;
                        return s;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
