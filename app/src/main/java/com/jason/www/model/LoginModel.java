package com.jason.www.model;

import com.google.gson.reflect.TypeToken;
import com.jason.www.base.IBaseModel;
import com.jason.www.contract.LoginContract;
import com.jason.www.http.BaseHttpCallback;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.response.Login;
import com.jason.www.http.response.base.BaseResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author：Jason
 * @date：2020/9/16 10:11
 * @email：1129847330@qq.com
 * @description:
 */
public class LoginModel extends LoginContract.Model {
    @Override
    public IBaseModel login(String username, String password, IBaseModel.IRequestCallback callback) {
        RetrofitHelper.enqueue(new BaseHttpCallback<BaseResponse<Login>>() {
            @Override
            public void success(BaseResponse response) {
                if (response.isOk()) {
                    callback.success(response);
                } else {
                    callback.fail(response.errorMsg);
                }
            }

            @Override
            public void fail(int code, String msg) {
                callback.fail(msg);
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getApi().login(username, password);
            }
        }, new TypeToken<BaseResponse<Login>>() {
        }.getType());
        return this;
    }
}