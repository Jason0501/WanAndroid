package com.jason.www.config;


public class Accounts {

    public static final int DEFAULT_VALUE = -1;
    public static final long DEFAULT_VALUE_L = -1;

    public static boolean getIsLogin() {
        return ShaPrefer.getBoolean(Constants.SharedPrefferenceKey.IS_LOGIN, false);
    }

    public static void setIsLogin(boolean isLogin) {
        ShaPrefer.put(Constants.SharedPrefferenceKey.IS_LOGIN, isLogin);
    }
}
