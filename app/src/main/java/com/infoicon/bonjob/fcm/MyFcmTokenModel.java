package com.infoicon.bonjob.fcm;

import android.content.Context;
import android.content.SharedPreferences;

import com.infoicon.bonjob.utils.Keys;

public class MyFcmTokenModel {

    private SharedPreferences sharedPreferences;

    public MyFcmTokenModel(Context context)
    {
        sharedPreferences=context.getSharedPreferences(Keys.PREF_FCM_TOKEN,Context.MODE_PRIVATE);
    }

    public String getToken()
    {
        return sharedPreferences.getString(Keys.FCM_TOKEN,"");
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Keys.FCM_TOKEN,token);
        editor.apply();
    }
}