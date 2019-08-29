package com.thenightlion.everyonelovesmemes.data.authRepository;

import android.content.Context;
import android.content.SharedPreferences;

import com.thenightlion.everyonelovesmemes.data.model.AuthInfoDto;

public class UserStorage {

    private static final String APP_PREFERENCES = "mySettingss";
    private static final String APP_PREFERENCES_TOKEN = "tokens";
    private static final String APP_PREFERENCES_ID = "ids";
    private static final String APP_PREFERENCES_USERNAME = "usernames";
    private static final String APP_PREFERENCES_FIRST_NAME = "firstNames";
    private static final String APP_PREFERENCES_LAST_NAME = "lastNames";
    private static final String APP_PREFERENCES_USER_DESCRIPTION = "userDescriptions";

    private SharedPreferences mSettings;

    public void saveUser(AuthInfoDto authInfoDto, Context context) {
        mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_TOKEN, authInfoDto.getAccessToken());
        editor.putInt(APP_PREFERENCES_ID, authInfoDto.userInfo.getId());
        editor.putString(APP_PREFERENCES_USERNAME, authInfoDto.userInfo.getUserName());
        editor.putString(APP_PREFERENCES_FIRST_NAME, authInfoDto.userInfo.getFirstName());
        editor.putString(APP_PREFERENCES_LAST_NAME, authInfoDto.userInfo.getLastName());
        editor.putString(APP_PREFERENCES_USER_DESCRIPTION, authInfoDto.userInfo.getUserDescription());
        editor.apply();
    }

    ////тестовый
    public void getUser(Context context) {
        /*Toast.makeText(context, mSettings.getString(APP_PREFERENCES_TOKEN, ""), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, Integer.toString(mSettings.getInt(APP_PREFERENCES_ID, 0)), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, mSettings.getString(APP_PREFERENCES_USERNAME, ""), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, mSettings.getString(APP_PREFERENCES_FIRST_NAME, ""), Toast.LENGTH_SHORT).show();*/
    }
}
