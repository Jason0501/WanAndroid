package com.jason.www.net;

import com.jason.www.net.response.base.BaseResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author：Jason
 * @date：2020/8/4 14:58
 * @email：1129847330@qq.com
 * @description:
 */
public interface RetrofitUrl {
    String BASE_URL = Urls.BASE_SERVER_URL;

    @FormUrlEncoded
    @POST("user/register")
    Call<BaseResponse> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);

    @GET("article/list/{page}/json")
    Call<BaseResponse> getHomeArticles(@Path("page") int page);

}