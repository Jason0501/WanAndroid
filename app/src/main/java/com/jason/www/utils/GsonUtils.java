package com.jason.www.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author：Jason
 * @date：2020/8/18 10:22
 * @email：1129847330@qq.com
 * @description:
 */
public class GsonUtils {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            return new GsonBuilder().create();
        }
        return gson;
    }
}