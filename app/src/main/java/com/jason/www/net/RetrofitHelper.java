package com.jason.www.net;

import com.jason.www.net.response.base.BaseResponse;
import com.jason.www.utils.GsonUtils;
import com.jason.www.utils.LogUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author：Jason
 * @date：2020/8/4 15:09
 * @email：1129847330@qq.com
 * @description:
 */
public class RetrofitHelper {
    private static RetrofitHelper retrofitHelper;
    private RetrofitUrl retrofitUrl;
    private Call<ResponseBody> call;

    public static RetrofitHelper getInstance() {
        if (retrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (retrofitHelper == null) {
                    retrofitHelper = new RetrofitHelper();
                }
            }
        }
        return retrofitHelper;
    }

    private RetrofitHelper() {
        retrofitUrl = new Retrofit.Builder()
                .baseUrl(RetrofitUrl.BASE_URL)
                .client(getOkHttpClient())
//                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitUrl.class);
    }

    /**
     * 配置okhttp客户端
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //忽略https证书验证，允许抓包
        builder.sslSocketFactory(SSLFactory.createSSLSocketFactory(), new SSLFactory.TrustAllManager());
        builder.hostnameVerifier(new SSLFactory.TrustAllHostnameVerifier());
        builder.callTimeout(7000, TimeUnit.MILLISECONDS);
        OkHttpClient client = builder.build();
        return client;
    }

    public RetrofitUrl getRetrofitUrl() {
        return retrofitUrl;
    }

    /**
     * 异步请求
     *
     * @param callback
     */
    public <T> void enqueue(HttpRequestCallback<T> callback, Type type) {
        if (callback == null) {
            throw new NullPointerException("callback must not be null");
        }
        call = callback.getApi();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (callback != null) {
                        try {
                            String content = response.body().string();
                            LogUtils.i("onResponse:" + content);
                            T baseResponse = GsonUtils.getGson().fromJson(content, type);
                            callback.success(baseResponse);
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
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtils.i("onFailure->请求失败：" + t.getMessage());
                if (callback != null) {
                    callback.fail(BaseResponse.FAIL, t.getMessage());
                }
            }
        });
    }

    public void cancel() {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }
}