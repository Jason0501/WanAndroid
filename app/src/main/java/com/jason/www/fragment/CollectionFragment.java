package com.jason.www.fragment;

import com.jason.www.R;
import com.jason.www.adapter.ArticleAdapter;
import com.jason.www.base.BaseMvpFragment;
import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.Collect;
import com.jason.www.mvp.contract.CollectionContract;
import com.jason.www.mvp.presenter.CollectionPresenter;
import com.jason.www.widget.CommonItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author：动脑学院-Zee老师
 * @date：2020/9/16 22:05
 * @email：575569745@qq.com
 * @description:
 */
public class CollectionFragment extends BaseMvpFragment<CollectionPresenter> implements CollectionContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    private ArticleAdapter mAdapter;
    private int mPage;
    private boolean mIsRefresh;

    private CollectionFragment() {
    }

    public static Fragment getInstance() {
        return new CollectionFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(CommonItemDecoration.createVertical());
        mAdapter = new ArticleAdapter();
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getPresenter().getArticleCollection(mPage);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mIsRefresh = true;
                mPage = 0;
                initData();
            }
        });
        smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPresenter().getArticleCollection(++mPage);
            }
        });
    }

    @Override
    protected CollectionPresenter createPresenter() {
        return new CollectionPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_refresh_list;
    }

    @Override
    public void successGetArticleCollection(BaseListResponse<Collect> response) {
        smartrefreshlayout.finishRefresh();
        smartrefreshlayout.finishLoadMore();
        if (response.isOver()) {
            if (mIsRefresh) {
                smartrefreshlayout.finishRefreshWithNoMoreData();
            } else {
                smartrefreshlayout.finishLoadMoreWithNoMoreData();
            }
        }
        for (Collect data : response.datas) {
            data.setCollect(true);
        }
        if (mIsRefresh) {
            mAdapter.setList(response.datas);
        } else {
            mAdapter.addData(response.datas);
        }
        mIsRefresh = false;
    }

    @Override
    public void successGetWebSiteCollection(BaseListResponse<Collect> response) {

    }

    @Override
    public void successAddCollection() {

    }

    @Override
    public void successCancelCollection() {

    }
}