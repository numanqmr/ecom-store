package com.numan.fakestoreapp.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.numan.fakestoreapp.common.responseDtos.LoginResponse;


/**
 * Helper class for shared preferences methods that is used to store
 * offline data in the applications app data storage.
 */
public class MySharedPreference {

    private static Gson sGson;
    private static LoginResponse sLoginResponse;

    public static Gson getGson() {
        if (sGson == null)
            sGson = new Gson();
        return sGson;
    }

    public static void setUser(Context context, LoginResponse response) {
        if (context != null) {
            if (response != null)
                saveToSharedPreferences(context, Constants.KEY_PREF_LOGIN_RESPONSE, getGson().toJson(response));
            else
                saveToSharedPreferences(context, Constants.KEY_PREF_LOGIN_RESPONSE, "");
        }
        sLoginResponse = response;
    }

    public static LoginResponse getUser(Context context) {
        if (sLoginResponse == null && context != null) {
            String json = getStringSharedPreferences(context, Constants.KEY_PREF_LOGIN_RESPONSE);
            sLoginResponse = json.length() > 0 && !json.equals("") ? (LoginResponse) getGson().fromJson(json, new TypeToken<LoginResponse>() {
            }.getType()) : null;
        }
        return sLoginResponse;
    }

    private static void saveToSharedPreferences(Context context, String key, String data) {
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences(Constants.FILE_PREF_APP_DATA, Context.MODE_PRIVATE).edit();
        editor.putString(key, data);
        editor.apply();
    }

    private static String getStringSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(Constants.FILE_PREF_APP_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

}
