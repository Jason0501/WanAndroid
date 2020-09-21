package com.jason.www.mvp.model;

import com.google.gson.reflect.TypeToken;
import com.jason.www.http.BaseHttpCallback;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.response.UserInfo;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.MineContract;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author：Jason
 * @date：2020/9/21 15:15
 * @email：1129847330@qq.com
 * @description:
 */
public class MineModel extends MineContract.Model {
    @Override
    public void getUserInfo(IRequestCallback callback) {
        RetrofitHelper.enqueue(new BaseHttpCallback<BaseResponse<UserInfo>>() {
            @Override
            public void success(BaseResponse<UserInfo> response) {
                callback.success(response);
            }

            @Override
            public void fail(int code, String msg) {
                callback.fail(msg);
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getApi().getUserInfo();
            }
        }, new TypeToken<BaseResponse<UserInfo>>() {
        }.getType());
    }
}