package com.dante.knowledge.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dante.knowledge.KnowledgeApp;

/**
 * Created by yons on 16/2/16.
 */
public class Shared {

    private static Context context = KnowledgeApp.context;
    private static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

    public static void save(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static void save(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public static void save(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public static String get(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }
    public static int getInt(String key) {
        return sp.getInt(key, 0);
    }
    public static boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }
}
