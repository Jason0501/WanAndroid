package com.jason.www.http;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author：Jason
 * @date：2020/8/11 14:18
 * @email：1129847330@qq.com
 * @description:
 */
public interface BaseHttpCallback<T> {
    void success(T response);

    void fail(int code, String msg);

    Call<ResponseBody> getApi();
}
