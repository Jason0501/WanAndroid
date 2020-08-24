package com.jason.www.http;

import com.jason.www.utils.GsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author：Jason
 * @date：2020/8/19 17:44
 * @email：1129847330@qq.com
 * @description:
 */
public abstract class HttpCallback<E> implements Callback<ResponseBody> {
    public HttpCallback(Type type) {
        this.type = type;
    }

    private Type type;

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            try {
                String content = response.body().string();
                E e = GsonUtils.getGson().fromJson(content, type);
                success(e);
            } catch (IOException e) {
                e.printStackTrace();
                onFailure(call, e);
            }
        } else {
            onFailure(call, new HttpException(response));
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

    }

    public abstract void success(E response);
}