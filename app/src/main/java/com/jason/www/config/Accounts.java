package com.jason.www.config;


import com.jason.www.http.response.Login;

public class Accounts {

    public static final int DEFAULT_VALUE = -1;
    public static final long DEFAULT_VALUE_L = -1;

    public static boolean getIsLogin() {
        return ShaPrefer.getBoolean(Constants.SharedPrefferenceKey.IS_LOGIN, false);
    }

    public static void setIsLogin(boolean isLogin) {
        ShaPrefer.put(Constants.SharedPrefferenceKey.IS_LOGIN, isLogin);
    }

    public static int getUserId() {
        return ShaPrefer.getInt(Constants.SharedPrefferenceKey.USER_ID, 0);
    }

    public static String getUserName() {
        return ShaPrefer.getString(Constants.SharedPrefferenceKey.USER_NICKNAME, "");
    }

    public static String getUserAvatar() {
        return ShaPrefer.getString(Constants.SharedPrefferenceKey.USER_ICON, "");
    }

    public static String getUserPublicName() {
        return ShaPrefer.getString(Constants.SharedPrefferenceKey.USER_PUBLICNAME, "");
    }

    public static void setLoginInfo(Login login) {
        ShaPrefer.batPut(Constants.SharedPrefferenceKey.USER_ID, login.id);
        ShaPrefer.batPut(Constants.SharedPrefferenceKey.USER_NICKNAME, login.nickname);
        ShaPrefer.batPut(Constants.SharedPrefferenceKey.USER_ICON, login.icon);
        ShaPrefer.batPut(Constants.SharedPrefferenceKey.USER_PUBLICNAME, login.publicName);
        ShaPrefer.put(Constants.SharedPrefferenceKey.IS_LOGIN, true);
        ShaPrefer.commit();
    }

    public static void loginOut() {
        ShaPrefer.clear();
    }
}
