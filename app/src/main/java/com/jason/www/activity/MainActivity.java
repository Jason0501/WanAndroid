package com.jason.www.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.reflect.TypeToken;
import com.jason.www.R;
import com.jason.www.adapter.HomeAdapter;
import com.jason.www.base.BaseActivity;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.SmartHttpCallback;
import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.utils.IntentUtils;
import com.jason.www.utils.SystemUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private HomeAdapter homeAdapter;
    private boolean isRefresh;
    private int page;

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(manager);
        recyclerview.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        homeAdapter = new HomeAdapter();
        recyclerview.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HomeArticleBody.HomeArticle homeArticle = homeAdapter.getData().get(position);
                IntentUtils.goToWebViewActivity(homeArticle.getLink());
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 0;
                requestHomeArticle();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreArticle();
            }
        });
    }

    private void loadMoreArticle() {
        requestHomeArticle();
    }

    @Override
    protected void initData() {
        smartRefreshLayout.autoRefresh();
        requestHomeArticle();
    }

    private void requestHomeArticle() {
        RetrofitHelper.enqueue2(new SmartHttpCallback<HomeArticleBody>() {
            @Override
            public void success(BaseResponse<HomeArticleBody> response) {
                if (response.isOk()) {
                    if (isRefresh) {
                        homeAdapter.getData().clear();
                    }
                    homeAdapter.addData(response.data.getDatas());
                } else {
                    showToast(response.errorMsg);
                }
            }

            @Override
            public void fail(int code, String msg) {
                showToast(msg);
            }

            @Override
            public void finish() {
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();
                isRefresh = false;
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getApi().getHomeArticles(page++);
            }
        }, new TypeToken<BaseResponse<HomeArticleBody>>() {
        }.getType());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    private long firstTimeMillis;

    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - firstTimeMillis <= 1500) {
            super.onBackPressed();
            SystemUtils.killMySelfProcess();
        } else {
            showToast("再按一次退出");
            firstTimeMillis = t;
        }
    }
}