package com.jason.www.mvp.presenter;

import com.jason.www.http.response.UserInfo;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.MineContract;
import com.jason.www.mvp.model.MineModel;

/**
 * @author：Jason
 * @date：2020/9/21 15:15
 * @email：1129847330@qq.com
 * @description:
 */
public class MinePresenter extends MineContract.Presenter {
    @Override
    public void getUserInfo() {
        getModel().getUserInfo(new IRequestCallback<UserInfo>() {
            @Override
            public void success(BaseResponse<UserInfo> response) {
                if (response.isOk()) {
                    getView().successGetUserInfo(response.data);
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
    protected MineContract.Model createModel() {
        return new MineModel();
    }
}