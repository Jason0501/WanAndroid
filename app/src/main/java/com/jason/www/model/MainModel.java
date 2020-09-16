package com.jason.www.model;

import com.google.gson.reflect.TypeToken;
import com.jason.www.base.IBaseModel;
import com.jason.www.contract.MainContract;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.SmartHttpCallback;
import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.http.response.HomeBanner;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.utils.CollectionUtils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author：Jason
 * @date：2020/9/16 11:36
 * @email：1129847330@qq.com
 * @description:
 */
public class MainModel extends MainContract.Model {
    @Override
    public void getBannerHome(IBaseModel.IRequestCallback callback) {
        RetrofitHelper.enqueue(new SmartHttpCallback<BaseResponse<List<HomeBanner>>>() {
            @Override
            public void success(BaseResponse<List<HomeBanner>> response) {
                if (response.isOk() && CollectionUtils.isNotEmpty(response.data)) {
                    callback.success(response);
                }
            }

            @Override
            public void fail(int code, String msg) {
                callback.fail(msg);
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getApi().getHomeBanner();
            }
        }, new TypeToken<BaseResponse<List<HomeBanner>>>() {
        }.getType());
    }

    @Override
    public void getHomeArticles(int page, IBaseModel.IRequestCallback callback) {
        RetrofitHelper.enqueue(new SmartHttpCallback<BaseResponse<HomeArticleBody>>() {
            @Override
            public void success(BaseResponse<HomeArticleBody> response) {
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
            public void finish() {
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getApi().getHomeArticles(page);
            }
        }, new TypeToken<BaseResponse<HomeArticleBody>>() {
        }.getType());
    }
}