package com.bbd.jztl.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.bbd.jztl.JZTLApplication;
public class AppSharedPreferences {


    private static final String TOKEN = "TOKEN";


    public static void saveToken(String token) {
        getPreferences().edit().putString(TOKEN, token).apply();
    }

    public static String getToken() {
        return getPreferences().getString(TOKEN, "");
    }

    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(JZTLApplication.context());
    }

    public static void save(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    public static String get(String key) {
        return getPreferences().getString(key, "");
    }

    public static String get(String key, String defalut) {
        return getPreferences().getString(key, defalut);
    }


}
