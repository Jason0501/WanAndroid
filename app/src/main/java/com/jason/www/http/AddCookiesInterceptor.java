package com.jason.www.http;

import com.jason.www.config.ShaPrefer;
import com.jason.www.utils.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author：Jason
 * @date：2020/9/21 16:37
 * @email：1129847330@qq.com
 * @description:
 */
public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookie = getCookie(request.url().toString(), request.url().host());
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("cookie", cookie);
        }

        return chain.proceed(builder.build());
    }

    private String getCookie(String url, String domain) {
        if (!TextUtils.isEmpty(url) && ShaPrefer.contains(url) && !TextUtils.isEmpty(ShaPrefer.getString(url, ""))) {
            return ShaPrefer.getString(url, "");
        }
        if (!TextUtils.isEmpty(domain) && ShaPrefer.contains(domain) && !TextUtils.isEmpty(ShaPrefer.getString(domain, ""))) {
            return ShaPrefer.getString(domain, "");
        }
        return null;
    }
}