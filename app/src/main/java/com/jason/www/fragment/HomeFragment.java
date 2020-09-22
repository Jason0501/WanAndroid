package com.jason.www.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.jason.www.R;
import com.jason.www.adapter.HomeAdapter;
import com.jason.www.adapter.HomeBannerAdapter;
import com.jason.www.base.BaseMvpFragment;
import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.http.response.HomeBanner;
import com.jason.www.mvp.contract.MainContract;
import com.jason.www.mvp.presenter.MainPresenter;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author：Jason
 * @date：2020/9/16 16:45
 * @email：1129847330@qq.com
 * @description:
 */
public class HomeFragment extends BaseMvpFragment<MainPresenter> implements MainContract.View {
    @BindView(R.id.linearlayout_title)
    LinearLayout linearLayoutToolBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Banner mBanner;
    private HomeAdapter mHomeAdapter;
    private boolean mIsRefresh;
    private int mPage;
    private int mBannerHeight;

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .titleBar(toolbar)
                .init();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(manager);
        recyclerview.addItemDecoration(CommonItemDecoration.createVertical());
        mHomeAdapter = new HomeAdapter();
        initBanner();
        recyclerview.setAdapter(mHomeAdapter);
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
        mHomeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view,
                                    int position) {
                HomeArticleBody.HomeArticle homeArticle = mHomeAdapter.getData().get(position);
                IntentUtils.goToWebViewActivity(homeArticle.getLink());
            }
        });
        mHomeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_collect:
                        HomeArticleBody.HomeArticle article = mHomeAdapter.getData().get(position);
                        if (!article.isCollect()) {
                            getPresenter().addCollection(article.getId());
                        } else {
                            getPresenter().cancelCollection(article.getId());
                        }
                        article.setCollect(!article.isCollect());
                        mHomeAdapter.notifyItemChanged(position);
                        break;
                    case R.id.tv_author:
                        break;
                }
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIsRefresh = true;
                mPage = 0;
                getPresenter().getBannerHome();
                getPresenter().getHomeArticles(mPage);
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
        mHomeAdapter.addHeaderView(view);
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
    public void successGetHomeArticles(HomeArticleBody homeArticleBody) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        if (mIsRefresh) {
            mHomeAdapter.setList(homeArticleBody.datas);
        } else {
            mHomeAdapter.addData(homeArticleBody.getDatas());
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
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }
}