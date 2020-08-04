package com.jason.www.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author：Jason
 * @date：2020/8/4 14:58
 * @email：1129847330@qq.com
 * @description:
 */
public interface RetrofitUrl {
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseBody> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    String baseUrl = Urls.BASE_SERVER_URL;
}