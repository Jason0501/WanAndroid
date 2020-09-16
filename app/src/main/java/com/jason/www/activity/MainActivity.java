package com.jason.www.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.reflect.TypeToken;
import com.jason.www.R;
import com.jason.www.adapter.HomeAdapter;
import com.jason.www.adapter.HomeBannerAdapter;
import com.jason.www.base.BaseActivity;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.SmartHttpCallback;
import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.http.response.HomeBanner;
import com.jason.www.http.response.WeChatPublicAccount;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.utils.CollectionUtils;
import com.jason.www.utils.DisplayUtils;
import com.jason.www.utils.IntentUtils;
import com.jason.www.utils.SystemUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

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
    Banner mBanner;
    private HomeAdapter mHomeAdapter;
    private boolean isRefresh;
    private int page;

    @Override
    protected void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(manager);
        recyclerview.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mHomeAdapter = new HomeAdapter();
        initBanner();
        recyclerview.setAdapter(mHomeAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mHomeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HomeArticleBody.HomeArticle homeArticle = mHomeAdapter.getData().get(position);
                IntentUtils.goToWebViewActivity(homeArticle.getLink());
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 0;
                requestHomeArticle();
                requestBanner();
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreArticle();
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                HomeBanner banner = (HomeBanner) mBanner.getAdapter().getData(position);
                IntentUtils.goToWebViewActivity(banner.getUrl());
            }
        });
    }

    private void initBanner() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.header_home, null);
        mBanner = view.findViewById(R.id.banner_home);
        int width = DisplayUtils.realWidth();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mBanner.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width * 5 / 9;
        mBanner.setLayoutParams(layoutParams);
        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter();
        mBanner.setAdapter(bannerAdapter);
        mBanner.addBannerLifecycleObserver(this);
        mBanner.setIndicator(new CircleIndicator(mContext), true);
        mHomeAdapter.addHeaderView(view);
    }

    private void loadMoreArticle() {
        requestHomeArticle();
    }

    @Override
    protected void initData() {
        requestHomeArticle();
        requestBanner();
    }

    private void requestBanner() {
        RetrofitHelper.enqueue2(new SmartHttpCallback<BaseResponse<List<HomeBanner>>>() {
            @Override
            public void success(BaseResponse<List<HomeBanner>> response) {
                if (response.isOk() && CollectionUtils.isNotEmpty(response.data)) {
                    mBanner.setDatas(response.data);
                }
            }

            @Override
            public void fail(int code, String msg) {
                Log.d("MainActivity", msg);
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getApi().getHomeBanner();
            }
        }, new TypeToken<BaseResponse<List<HomeBanner>>>() {
        }.getType());
    }

    private void requestWeChatPublicAccounts() {
        RetrofitHelper.enqueue2(new SmartHttpCallback<BaseResponse<List<WeChatPublicAccount>>>() {
            @Override
            public void success(BaseResponse<List<WeChatPublicAccount>> response) {
                if (response.isOk()) {
                    Log.d("MainActivity", response.data.toString());
                } else {
                    showToast(response.errorMsg);
                }
            }

            @Override
            public void fail(int code, String msg) {
                Log.d("MainActivity", msg);
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getApi().getWeChatPublicAccounts();
            }
        }, new TypeToken<BaseResponse<List<HomeArticleBody.HomeArticle>>>() {
        }.getType());
    }

    private void requestHomeArticle() {
        RetrofitHelper.enqueue2(new SmartHttpCallback<BaseResponse<HomeArticleBody>>() {
            @Override
            public void success(BaseResponse<HomeArticleBody> response) {
                if (response.isOk()) {
                    if (isRefresh) {
                        mHomeAdapter.getData().clear();
                    }
                    mHomeAdapter.addData(response.data.getDatas());
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