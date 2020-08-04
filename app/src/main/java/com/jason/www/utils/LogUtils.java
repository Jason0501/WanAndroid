package com.jason.www.utils;

import android.util.Log;

import com.jason.www.BuildConfig;

/**
 * @author：Jason
 * @date：2020/7/28 10:48
 * @email：1129847330@qq.com
 * @description:
 */
public class LogUtils {
    private static final String TAG = "WanAndroid";

    public static void i(String log) {
        i(TAG, log);
    }

    public static void i(String tag, String log) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, log);
        }
    }

    public static void d(String tag, String log) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, log);
        }
    }

    public static void d(String log) {
        d(TAG, log);
    }

    public static void e(String tag, String log) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, log);
        }
    }

    public static void e(String log) {
        e(TAG, log);
    }
}