package com.jason.www.mvp.model;

import com.google.gson.reflect.TypeToken;
import com.jason.www.http.BaseHttpCallback;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.Question;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.mvp.callback.IRequestCallback;
import com.jason.www.mvp.contract.QuestionContract;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author：动脑学院-Zee老师
 * @date：2020/9/16 22:05
 * @email：575569745@qq.com
 * @description:
 */
public class QuestionModel extends QuestionContract.Model {
    @Override
    public void getQuestion(int page, IRequestCallback callback) {
        RetrofitHelper.enqueue(new BaseHttpCallback<BaseResponse<BaseListResponse<Question>>>() {
            @Override
            public void success(BaseResponse<BaseListResponse<Question>> response) {
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
                return RetrofitHelper.getApi().getQuestion(page);
            }
        }, new TypeToken<BaseResponse<BaseListResponse<Question>>>() {
        }.getType());
    }
}