package com.jason.www.net;

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

    public RetrofitUrl getRetrofitUrl() {
        return retrofitUrl;
    }

    private RetrofitHelper() {
        createRetrofitClient();
    }

    private void createRetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitUrl.baseUrl)
                .build();
        retrofitUrl = retrofit.create(RetrofitUrl.class);
    }
}