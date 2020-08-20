package com.jason.www.http;

import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.utils.GsonUtils;
import com.jason.www.utils.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    /**
     * 异步请求
     *
     * @param callback
     */
    public static <T> void enqueue(BaseHttpCallback<T> callback) {
        if (callback == null) {
            throw new NullPointerException("callback must not be null");
        }
        Call<ResponseBody> call = callback.getApi();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (callback != null) {
                        try {
                            String content = response.body().string();
                            LogUtils.i("onResponse:" + content);
                            ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl(BaseResponse.class, new Class[]{BaseResponse.class});
                            BaseResponse<T> result = GsonUtils.getGson().fromJson(content, parameterizedType);
                            callback.success(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    LogUtils.i("onResponse->请求失败:" + response.message());
                    if (callback != null) {
                        callback.fail(response.code(), response.message());
                    }
                }
                if (callback != null && callback instanceof SmartHttpCallback) {
                    ((SmartHttpCallback) callback).finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.i("onFailure->请求失败：" + t.getMessage());
                if (callback != null) {
                    callback.fail(BaseResponse.FAIL, t.getMessage());
                }
                if (callback != null && callback instanceof SmartHttpCallback) {
                    ((SmartHttpCallback) callback).finish();
                }
            }
        });
    }

    public static <T> void enqueue2(BaseHttpCallback<T> callback, Class clazz) {
        if (callback == null) {
            throw new NullPointerException("callback must not be null");
        }
        Call<ResponseBody> call = callback.getApi();
        call.enqueue(new HttpCallback<T>(clazz) {
            @Override
            public void success(BaseResponse<T> response) {
                if (callback != null) {
                    callback.success(response);
                    if (callback instanceof SmartHttpCallback) {
                        ((SmartHttpCallback) callback).finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
                if (callback != null) {
                    callback.fail(BaseResponse.FAIL, t.getMessage());
                    if (callback instanceof SmartHttpCallback) {
                        ((SmartHttpCallback) callback).finish();
                    }
                }
            }
        });
    }

    public void cancel() {
//        if (call != null && !call.isCanceled()) {
//            call.cancel();
//        }
    }
}