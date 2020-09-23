package com.jason.www.mvp.model;

import com.google.gson.reflect.TypeToken;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.SmartHttpCallback;
import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.Collect;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.CollectionContract;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author：Jason
 * @date：2020/9/23 11:34
 * @email：1129847330@qq.com
 * @description:
 */
public class CollectionModel extends CollectionContract.Model {

    private MainModel mainModel;

    public CollectionModel() {
        mainModel = new MainModel();
    }

    @Override
    public void addCollection(int articleId, IRequestCallback callback) {
        mainModel.addCollection(articleId, callback);
    }

    @Override
    public void cancelCollection(int articleId, IRequestCallback callback) {
        mainModel.cancelCollection(articleId, callback);
    }

    @Override
    public void getWebSiteCollection(int page, IRequestCallback callback) {
        RetrofitHelper.enqueue(new SmartHttpCallback<BaseResponse<BaseListResponse<Collect>>>() {
            @Override
            public void success(BaseResponse<BaseListResponse<Collect>> response) {
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
                return RetrofitHelper.getApi().getCollection(page);
            }
        }, new TypeToken<BaseResponse<BaseListResponse<Collect>>>() {
        }.getType());
    }

    @Override
    public void getArticleCollection(int page, IRequestCallback callback) {
        RetrofitHelper.enqueue(new SmartHttpCallback<BaseResponse<BaseListResponse<Collect>>>() {
            @Override
            public void success(BaseResponse<BaseListResponse<Collect>> response) {
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
                return RetrofitHelper.getApi().getCollection(page);
            }
        }, new TypeToken<BaseResponse<BaseListResponse<Collect>>>() {
        }.getType());
    }
}