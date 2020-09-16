package com.jason.www.http;

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

    /**
     * 获取微信公众号列表
     *
     * @return
     */
    @GET("wxarticle/chapters/json")
    Call<ResponseBody> getWeChatPublicAccounts();

    /**
     * 获取首页banner
     *
     * @return
     */
    @GET("banner/json")
    Call<ResponseBody> getHomeBanner();

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseBody> register(@Field("username") String username,
                                @Field("password") String password,
                                @Field("repassword") String repassword);

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> login(@Field("username") String username,
                             @Field("password") String password);

    /**
     * 获取首页文章列表
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Call<ResponseBody> getHomeArticles(@Path("page") int page);

    /**
     * 获取常用网站
     *
     * @return
     */
    @GET("friend/json")
    Call<ResponseBody> getFrequentWebSite();

    @GET("wenda/list/{page}/json ")
    Call<ResponseBody> getQuestion(@Path("page") int page);
}