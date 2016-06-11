package com.sherily.shieh.asteria.engine;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/6/30.
 */

//TODO: 目前只提供了存储\读取String类型的接口, 有兴趣的请提供更多类型的接口
public class SharePrefHelper {

    private static final String SHAREPREF_NAME = "househelper.sharepref";

    private static SharePrefHelper instance = new SharePrefHelper();
    private static SharedPreferences sharedPreferences = null;

    /**
     * 单例
     * @param context
     * @return
     */
    public static SharePrefHelper getInstance(Context context) {

        if (sharedPreferences == null) {
            sharedPreferences = context.getApplicationContext().getSharedPreferences(SHAREPREF_NAME, Context.MODE_PRIVATE);
        }
        return instance;
    }


    /**
     *
     * @param key
     * @param value
     * @return
     */
    public boolean putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public boolean putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
    /**
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public boolean putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }


}
