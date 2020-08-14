package com.jason.www.base;

import android.app.Application;

import org.xutils.x;

/**
 * @author：Jason
 * @date：2020/8/14 15:12
 * @email：1129847330@qq.com
 * @description:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}