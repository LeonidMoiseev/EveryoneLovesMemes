package com.thenightlion.everyonelovesmemes.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    private static final String APP_PREFERENCES = "mySettings";
    private static final String DEFAULT_STRING_VALUE = "default";
    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtils(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, DEFAULT_STRING_VALUE);
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void putInt(String key) {
        sharedPreferences.edit().putInt(key, 0).apply();
    }
}
