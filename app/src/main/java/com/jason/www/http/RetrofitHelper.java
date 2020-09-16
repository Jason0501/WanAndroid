package com.jason.www.http;

import com.jason.www.http.response.base.BaseResponse;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author：Jason
 * @date：2020/8/4 15:09
 * @email：1129847330@qq.com
 * @description:
 */
public class RetrofitHelper {
    private static Retrofit retrofit;

    private RetrofitHelper() {
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitHelper.class) {
                if (retrofit == null) {
                    retrofit = initRetrofit();
                }
            }
        }
        return retrofit;
    }

    private static Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(RetrofitUrl.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 配置okhttp客户端
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //忽略https证书验证，允许抓包
        builder.sslSocketFactory(SSLFactory.createSSLSocketFactory(), new SSLFactory.TrustAllManager());
        builder.hostnameVerifier(new SSLFactory.TrustAllHostnameVerifier());
        builder.callTimeout(7000, TimeUnit.MILLISECONDS);
        OkHttpClient client = builder.build();
        return client;
    }

    public static RetrofitUrl getApi() {
        return getRetrofit().create(RetrofitUrl.class);
    }

    public static <T> void enqueue(BaseHttpCallback<T> callback, Type type) {
        if (callback == null) {
            throw new NullPointerException("callback must not be null");
        }
        Call<ResponseBody> call = callback.getApi();
        call.enqueue(new HttpCallback<T>(type) {
            @Override
            public void success(T response) {
                if (callback != null) {
                    callback.success(response);
                    if (callback instanceof SmartHttpCallback) {
                        ((SmartHttpCallback<T>) callback).finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
                if (callback != null) {
                    callback.fail(BaseResponse.FAIL, t.getMessage());
                    if (callback instanceof SmartHttpCallback) {
                        ((SmartHttpCallback<T>) callback).finish();
                    }
                }
            }
        });
    }

    public void cancel() {
    }
}