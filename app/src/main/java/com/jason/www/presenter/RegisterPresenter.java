package com.jason.www.presenter;

import com.jason.www.base.IBaseModel;
import com.jason.www.contract.RegisterContract;
import com.jason.www.http.response.Register;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.model.RegisterModel;

/**
 * @author：Jason
 * @date：2020/9/16 12:25
 * @email：1129847330@qq.com
 * @description:
 */
public class RegisterPresenter extends RegisterContract.Presenter {
    @Override
    public void register(String username, String password, String rePassword) {
        getModel().register(username, password, rePassword, new IBaseModel.IRequestCallback<Register>() {
            @Override
            public void success(BaseResponse<Register> response) {
                if (response.isOk()) {
                    getView().successRegister(response.data);
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

    @Override
    protected RegisterContract.Model createModel() {
        return new RegisterModel();
    }
}