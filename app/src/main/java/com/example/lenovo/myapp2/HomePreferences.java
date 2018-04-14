package com.example.lenovo.myapp2;

import android.content.Context;
import android.content.SharedPreferences;

public class HomePreferences {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void initialize(Context context_) {
        if (null == preferences) {
            preferences = context_.getSharedPreferences("homes.connect.preferences", Context.MODE_PRIVATE);
        }
        if (null == editor) {
            editor = preferences.edit();
            editor.apply();
        }
    }

    public static void clear() {
        editor.clear();
        editor.commit();
    }

    public static void save(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void save(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void save(String key, Integer value) {
        save(key, String.valueOf(value));
    }

    public static void save(String key, Long value) {
        save(key, String.valueOf(value));
    }

    public static String get(String key) {
        return preferences.getString(key, null);
    }

    public static boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public static Boolean contains(String key) {
        return preferences.contains(key);
    }

    public static void removeKey(String key) {
        editor.remove(key);
        editor.commit();
    }
}
