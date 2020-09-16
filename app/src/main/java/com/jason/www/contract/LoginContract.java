package com.jason.www.contract;

import com.jason.www.base.IBaseModel;
import com.jason.www.base.IBasePresenter;
import com.jason.www.base.IBaseView;
import com.jason.www.http.response.Login;

/**
 * @author：Jason
 * @date：2020/9/16 10:11
 * @email：1129847330@qq.com
 * @description:
 */
public interface LoginContract {
    abstract class Model extends IBaseModel {
        public abstract IBaseModel login(String username, String password, IBaseModel.IRequestCallback callback);
    }

    interface View extends IBaseView {
        void successLogin(Login login);
    }

    abstract class Presenter extends IBasePresenter<LoginContract.Model, LoginContract.View> {
        public abstract void login(String username, String password);
    }
}
