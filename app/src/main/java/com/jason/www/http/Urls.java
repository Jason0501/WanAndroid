package com.jason.www.http;

/**
 * @author：Jason
 * @date：2020/8/3 18:06
 * @email：1129847330@qq.com
 * @description:
 */
public class Urls {
    public static final String BASE_SERVER_URL = "https://www.wanandroid.com/";
    /**
     * 用户相关,post
     */
    //登录
    public static final String USER_LOGIN = BASE_SERVER_URL + "user/login";
    //注册
    public static final String USER_REGISTER = BASE_SERVER_URL + "user/register";

    /**
     * 登出，get
     */
    public static String getUserLogOut() {
        return BASE_SERVER_URL + "user/logout/json";
    }

    /**
     * 首页相关
     */
    //首页banner
    public static String getHomeBanner() {
        return BASE_SERVER_URL + "banner/json";
    }

    //首页文章列表
    public static String getHomeArticleList(String page) {
        return BASE_SERVER_URL + "article/list/" + page + "/json";
    }
}