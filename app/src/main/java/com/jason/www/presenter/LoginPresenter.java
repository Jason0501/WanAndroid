package com.jason.www.presenter;

import com.jason.www.base.IBaseModel;
import com.jason.www.contract.LoginContract;
import com.jason.www.http.response.Login;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.model.LoginModel;

/**
 * @author：Jason
 * @date：2020/9/16 10:11
 * @email：1129847330@qq.com
 * @description:
 */
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    protected LoginContract.Model createModel() {
        return new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        getModel().login(username, password, new IBaseModel.IRequestCallback<Login>() {
            @Override
            public void success(BaseResponse<Login> response) {
                if (response.isOk()) {
                    getView().successLogin(response.data);
                } else {
                    getView().failLoad(response.errorMsg);
                }
            }

            @Override
            public void fail(String msg) {
                getView().failLoad(msg);
            }
        });
    }
}