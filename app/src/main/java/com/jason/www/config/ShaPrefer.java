package com.jason.www.config;

import android.content.Context;
import android.content.SharedPreferences;

public class ShaPrefer {

    private ShaPrefer() {
    }

    private static final String DEFAULT_SHAPRE_NAME = Constants.SP_NAME;

    private static SharedPreferences preferences = null;
    private static SharedPreferences.Editor editor = null;

    public static synchronized String getString(String key, String defValue) {
        checkDefault();
        return preferences.getString(key, defValue);
    }

    public static synchronized boolean getBoolean(String key, boolean defValue) {
        checkDefault();
        return preferences.getBoolean(key, defValue);
    }

    public static synchronized float getFloat(String key, float defValue) {
        checkDefault();
        return preferences.getFloat(key, defValue);
    }

    public static synchronized int getInt(String key, int defValue) {
        checkDefault();
        return preferences.getInt(key, defValue);
    }

    public static synchronized long getLong(String key, long defValue) {
        checkDefault();
        return preferences.getLong(key, defValue);
    }

    public static synchronized void remove(String... keys) {
        checkDefault();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.commit();
    }

    public static synchronized boolean contains(String key) {
        checkDefault();
        return preferences.contains(key);
    }

    public static synchronized void put(String key, Object value) {
        batPut(key, value);
        editor.commit();
    }

    public static void commit() {
        if (editor != null) {
            editor.commit();
        }
    }

    /**
     * 批处理
     *
     * @param key
     * @param value
     */
    public static synchronized void batPut(String key, Object value) {
        checkDefault();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
        }
    }


    private static void checkDefault() {
        if (preferences == null) {
            preferences = AppData.getContext().getSharedPreferences(DEFAULT_SHAPRE_NAME, Context.MODE_PRIVATE);
        }
        if (editor == null) {
            editor = preferences.edit();
        }
    }

    public static void clear() {
        if (preferences != null) {
            checkDefault();
            editor.clear();
            editor.commit();
        }
    }
}
