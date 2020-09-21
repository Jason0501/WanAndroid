package com.jason.www.event;

import com.jason.www.event.base.BaseEvent;

/**
 * @author：Jason
 * @date：2020/9/21 16:53
 * @email：1129847330@qq.com
 * @description:
 */
public class LoginEvent extends BaseEvent {
    private boolean isLogin;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}