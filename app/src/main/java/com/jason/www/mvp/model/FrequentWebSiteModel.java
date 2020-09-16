package com.jason.www.mvp.model;

import com.google.gson.reflect.TypeToken;
import com.jason.www.http.BaseHttpCallback;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.response.FrequentWebSite;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.FrequentWebSiteContract;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author：动脑学院-Zee老师
 * @date：2020/9/16 22:05
 * @email：575569745@qq.com
 * @description:
 */
public class FrequentWebSiteModel extends FrequentWebSiteContract.Model {
    @Override
    public void getFrequentWebSite(IRequestCallback callback) {
        RetrofitHelper.enqueue(new BaseHttpCallback<BaseResponse<List<FrequentWebSite>>>() {
            @Override
            public void success(BaseResponse<List<FrequentWebSite>> response) {
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
                return RetrofitHelper.getApi().getFrequentWebSite();
            }
        }, new TypeToken<BaseResponse<List<FrequentWebSite>>>() {
        }.getType());
    }
}