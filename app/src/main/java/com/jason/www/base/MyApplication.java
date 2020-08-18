package com.jason.www.base;

import android.app.Application;

/**
 * @author：Jason
 * @date：2020/8/14 15:12
 * @email：1129847330@qq.com
 * @description:
 */
public class MyApplication extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
//        x.Ext.init(this);
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }
}