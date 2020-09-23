package com.jason.www.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.jason.www.R;
import com.jason.www.adapter.ArticleAdapter;
import com.jason.www.adapter.HomeBannerAdapter;
import com.jason.www.base.BaseMvpFragment;
import com.jason.www.http.Article;
import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.HomeBanner;
import com.jason.www.mvp.contract.ArticleContract;
import com.jason.www.mvp.presenter.ArticlePresenter;
import com.jason.www.utils.DisplayUtils;
import com.jason.www.utils.GlideUtils;
import com.jason.www.utils.IntentUtils;
import com.jason.www.widget.CommonItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author：Jason
 * @date：2020/9/16 16:45
 * @email：1129847330@qq.com
 * @description:
 */
public class HomeFragment extends BaseMvpFragment<ArticlePresenter> implements ArticleContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.default_toolbar_toolbar)
    Toolbar toolbar;
    Banner mBanner;
    private ArticleAdapter mArticleAdapter;
    private boolean mIsRefresh;
    private int mPage;
    private int mBannerHeight;

    private HomeFragment() {
    }

    public static Fragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .titleBar(R.id.default_toolbar_toolbar)
                .init();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(manager);
        recyclerview.addItemDecoration(CommonItemDecoration.createVertical());
        mArticleAdapter = new ArticleAdapter();
        initBanner();
        recyclerview.setAdapter(mArticleAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mTotalDy;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    GlideUtils.resumeLoad();
                } else {
                    GlideUtils.pauseLoad();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalDy += dy;
                if (mTotalDy <= mBannerHeight) {
                    float alpha = (float) mTotalDy / mBannerHeight;
                    toolbar.setAlpha(alpha);
                } else {
                    toolbar.setAlpha(1);
                }
            }
        });
        mArticleAdapter.setOnCollectListener(new ArticleAdapter.OnCollectListener() {
            @Override
            public void addCollect(Article article) {
                getPresenter().addCollection(article.id);
            }

            @Override
            public void cancelCollect(Article article) {
                getPresenter().cancelCollection(article.id);
            }
        });
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIsRefresh = true;
                mPage = 0;
                getPresenter().getBannerHome();
                getPresenter().getHomeArticles(mPage);
            }
        });

        smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
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
        int bannerWidth = DisplayUtils.realWidth();
        mBannerHeight = bannerWidth * 5 / 9;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mBanner.getLayoutParams();
        layoutParams.width = bannerWidth;
        layoutParams.height = mBannerHeight;
        mBanner.setLayoutParams(layoutParams);
        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter();
        mBanner.setAdapter(bannerAdapter);
        mBanner.addBannerLifecycleObserver(this);
        mBanner.setIndicator(new CircleIndicator(mContext), true);
        mArticleAdapter.addHeaderView(view);
    }

    private void loadMoreArticle() {
        getPresenter().getHomeArticles(++mPage);
    }

    @Override
    protected void initData() {
        super.initData();
        getPresenter().getBannerHome();
        getPresenter().getHomeArticles(mPage);
    }

    @Override
    public void successGetBanner(List<HomeBanner> list) {
        mBanner.setDatas(list);
    }

    @Override
    public void successGetHomeArticles(BaseListResponse<Article> response) {
        smartrefreshlayout.finishRefresh();
        smartrefreshlayout.finishLoadMore();
        if (response.isOver()) {
            if (mIsRefresh) {
                smartrefreshlayout.finishRefreshWithNoMoreData();
            } else {
                smartrefreshlayout.finishLoadMoreWithNoMoreData();
            }
        }
        if (mIsRefresh) {
            mArticleAdapter.setList(response.datas);
        } else {
            mArticleAdapter.addData(response.getDatas());
        }
        mIsRefresh = false;
    }

    @Override
    public void successAddCollection() {
        showToast("收藏成功");
    }

    @Override
    public void successCancelCollection() {
        showToast("成功取消收藏");
    }

    @Override
    public void failLoad(String msg) {
        super.failLoad(msg);
        smartrefreshlayout.finishRefresh(false);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected ArticlePresenter createPresenter() {
        return new ArticlePresenter();
    }
}