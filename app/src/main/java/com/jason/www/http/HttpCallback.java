package com.jason.www.http;

import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.utils.GsonUtils;

import java.io.IOException;

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
public abstract class HttpCallback<T> implements Callback<ResponseBody> {
    private Class clazz;

    public HttpCallback(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            try {
                String content = response.body().string();
                ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl(BaseResponse.class, new Class[]{clazz});
                BaseResponse<T> result = GsonUtils.getGson().fromJson(content, parameterizedType);
                success(result);
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

    public abstract void success(BaseResponse<T> response);
}