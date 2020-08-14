package com.jason.www.net;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jason.www.net.response.base.BaseResponse;
import com.jason.www.utils.LogUtils;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
    private static final Gson gson = new GsonBuilder().create();
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
        builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
        builder.hostnameVerifier(new TrustAllHostnameVerifier());
        builder.callTimeout(7000, TimeUnit.MILLISECONDS);
        OkHttpClient client = builder.build();
        return client;
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {


        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {


        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

    }

    public RetrofitUrl getRetrofitUrl() {
        return retrofitUrl;
    }

    /**
     * 异步请求
     *
     * @param callback
     */
    public <T> void enqueue(HttpRequestCallback<T> callback) {
        if (callback == null) {
            throw new NullPointerException("callback must not be null");
        }
        call = callback.getApi();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String json = response.body().string();
                        LogUtils.i("请求成功：\r\n" + json);
                        BaseResponse t = gson.fromJson(json, new TypeToken<BaseResponse>() {
                        }.getType());
                        LogUtils.i("t:" + t.toString());
                        if (callback != null) {
                            callback.success(t);
                        }
                    } catch (Exception e) {
                        LogUtils.i("try-catch:" + e.getMessage());
                        e.printStackTrace();
                        if (callback != null) {
                            callback.fail(response.code(), e.getMessage());
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