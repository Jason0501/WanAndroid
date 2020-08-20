package com.jason.www.http;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author：Jason
 * @date：2020/8/20 11:33
 * @email：1129847330@qq.com
 * @description:
 */
public class ParameterizedTypeImpl implements ParameterizedType {
    private Class clazz;
    private Type[] types;

    public ParameterizedTypeImpl(Class clazz, Type[] types) {
        this.clazz = clazz;
        this.types = types != null ? types : new Type[0];
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    public Type[] getActualTypeArguments() {
        for (Type type : types) {
            Log.d("ParameterizedTypeImpl", "getActualTypeArguments():" + type.getTypeName());
        }
        return types;
    }

    @NonNull
    @Override
    public Type getRawType() {
        Log.d("ParameterizedTypeImpl", "getRawType():" + clazz.getSimpleName());
        return clazz;
    }

    @Nullable
    @Override
    public Type getOwnerType() {
        return null;
    }
}