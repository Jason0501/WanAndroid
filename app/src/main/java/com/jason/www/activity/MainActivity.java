package com.jason.www.activity;

import com.jason.www.R;
import com.jason.www.adapter.HomeAdapter;
import com.jason.www.base.BaseActivity;
import com.jason.www.net.RetrofitHelper;
import com.jason.www.net.response.base.BaseResponse;
import com.jason.www.utils.LogUtils;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        RetrofitHelper.getInstance().getRetrofitUrl().getHomeArticles(1).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().errorCode == BaseResponse.SUCCESS) {
//                    HomeArticleBody homeArticleBody = response.body().data;
//                    if (homeArticleBody == null || CollectionUtils.isEmpty(homeArticleBody.getDatas())) {
//                        showToast("暂无文章数据");
//                        return;
//                    }
//                    homeAdapter.addData(homeArticleBody.getDatas());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                LogUtils.i("MainActivity error:" + t.getMessage());
                showToast("加载失败：" + t.getMessage());
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}