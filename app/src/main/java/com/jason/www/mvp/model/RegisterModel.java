package com.jason.www.mvp.model;

import com.google.gson.reflect.TypeToken;
import com.jason.www.http.BaseHttpCallback;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.response.Register;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.RegisterContract;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author：Jason
 * @date：2020/9/16 12:25
 * @email：1129847330@qq.com
 * @description:
 */
public class RegisterModel extends RegisterContract.Model {
    @Override
    public void register(String username, String password, String rePassword,
                         IRequestCallback callback) {
        RetrofitHelper.enqueue(new BaseHttpCallback<BaseResponse<Register>>() {
            @Override
            public void success(BaseResponse<Register> response) {
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
                return RetrofitHelper.getApi().register(username, password, rePassword);
            }
        }, new TypeToken<BaseResponse<Register>>() {
        }.getType());
    }
}