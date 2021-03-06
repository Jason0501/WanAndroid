package com.jason.www.http;

import com.jason.www.config.ShaPrefer;
import com.jason.www.utils.TextUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author：Jason
 * @date：2020/9/21 16:35
 * @email：1129847330@qq.com
 * @description:
 */
public class ReceiveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //set-cookie可能为多个
        if (!response.headers("set-cookie").isEmpty()) {
            List<String> cookies = response.headers("set-cookie");
            String cookie = encodeCookie(cookies);
            saveCookie(request.url().toString(), request.url().host(), cookie);
        }

        return response;
    }

    //整合cookie为唯一字符串
    private String encodeCookie(List<String> cookies) {
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) continue;
                set.add(s);

            }
        }

        Iterator<String> ite = set.iterator();
        while (ite.hasNext()) {
            String cookie = ite.next();
            sb.append(cookie).append(";");
        }

        int last = sb.lastIndexOf(";");
        if (sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }

        return sb.toString();
    }

    //保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
    //这样能使得该cookie的应用范围更广
    private void saveCookie(String url, String domain, String cookies) {
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("url is null.");
        } else {
            ShaPrefer.batPut(url, cookies);
        }

        if (!TextUtils.isEmpty(domain)) {
            ShaPrefer.batPut(domain, cookies);
        }
        ShaPrefer.commit();
    }
}