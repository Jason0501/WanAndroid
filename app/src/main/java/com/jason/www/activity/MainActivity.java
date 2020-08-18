package com.jason.www.activity;

import com.google.gson.reflect.TypeToken;
import com.jason.www.R;
import com.jason.www.adapter.HomeAdapter;
import com.jason.www.base.BaseActivity;
import com.jason.www.net.HttpRequestCallback;
import com.jason.www.net.RetrofitHelper;
import com.jason.www.net.response.HomeArticleBody;
import com.jason.www.net.response.base.BaseResponse;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private HomeAdapter homeAdapter;

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(manager);
        recyclerview.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        homeAdapter = new HomeAdapter();
        recyclerview.setAdapter(homeAdapter);
    }

    @Override
    protected void initData() {
        requestBeauty();
    }

    private void requestBeauty() {
        RetrofitHelper.getInstance().enqueue(new HttpRequestCallback<BaseResponse<HomeArticleBody>>() {
            @Override
            public void success(BaseResponse<HomeArticleBody> response) {
                if (response.isOk()) {
                    homeAdapter.addData(response.data.getDatas());
                }
            }

            @Override
            public void fail(int code, String msg) {

            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getInstance().getRetrofitUrl().getHomeArticles(0);
            }
        }, new TypeToken<BaseResponse<HomeArticleBody>>() {
        }.getType());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}