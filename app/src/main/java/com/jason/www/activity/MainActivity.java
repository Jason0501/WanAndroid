package com.jason.www.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jason.www.R;
import com.jason.www.adapter.HomeAdapter;
import com.jason.www.adapter.HomeBannerAdapter;
import com.jason.www.base.BaseMvpActivity;
import com.jason.www.contract.MainContract;
import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.http.response.HomeBanner;
import com.jason.www.presenter.MainPresenter;
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

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    Banner mBanner;
    private HomeAdapter mHomeAdapter;
    private boolean isRefresh;
    private int page;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

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
                getPresenter().getBannerHome();
                getPresenter().getHomeArticles(page);
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
        getPresenter().getHomeArticles(++page);
    }

    @Override
    protected void initData() {
        getPresenter().getBannerHome();
        getPresenter().getHomeArticles(page);
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

    @Override
    public void successGetBanner(List<HomeBanner> list) {
        mBanner.setDatas(list);
    }

    @Override
    public void successGetHomeArticles(HomeArticleBody homeArticleBody) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        if (isRefresh) {
            mHomeAdapter.getData().clear();
        }
        mHomeAdapter.addData(homeArticleBody.getDatas());
        isRefresh = false;
    }
}