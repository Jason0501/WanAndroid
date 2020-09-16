package com.jason.www.mvp.contract;

import com.jason.www.http.response.Login;
import com.jason.www.mvp.base.IBaseModel;
import com.jason.www.mvp.base.IBasePresenter;
import com.jason.www.mvp.base.IBaseView;
import com.jason.www.mvp.callback.IRequestCallback;

/**
 * @author：Jason
 * @date：2020/9/16 10:11
 * @email：1129847330@qq.com
 * @description:
 */
public interface LoginContract {
    abstract class Model extends IBaseModel {
        public abstract IBaseModel login(String username, String password, IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successLogin(Login login);
    }

    abstract class Presenter extends IBasePresenter<LoginContract.Model, LoginContract.View> {
        public abstract void login(String username, String password);
    }
}
