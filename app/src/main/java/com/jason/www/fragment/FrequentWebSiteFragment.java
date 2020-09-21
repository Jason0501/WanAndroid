package com.jason.www.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jason.www.R;
import com.jason.www.adapter.FrequentWebSiteAdapter;
import com.jason.www.base.BaseMvpFragment;
import com.jason.www.http.response.FrequentWebSite;
import com.jason.www.mvp.contract.FrequentWebSiteContract;
import com.jason.www.mvp.presenter.FrequentWebSitePresenter;
import com.jason.www.utils.IntentUtils;
import com.jason.www.widget.CommonItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author：动脑学院-Zee老师
 * @date：2020/9/16 22:05
 * @email：575569745@qq.com
 * @description:
 */
public class FrequentWebSiteFragment extends BaseMvpFragment<FrequentWebSitePresenter> implements FrequentWebSiteContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    private FrequentWebSiteAdapter mAdapter;

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(CommonItemDecoration.createVertical());
        mAdapter = new FrequentWebSiteAdapter();
        recyclerview.setAdapter(mAdapter);
        smartrefreshlayout.setEnableLoadMore(false);
    }

    @Override
    protected void initData() {
        super.initData();
        getPresenter().getFrequentWebSite();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view,
                                    int position) {
                IntentUtils.goToWebViewActivity(mAdapter.getData().get(position).getLink());
            }
        });
    }

    @Override
    protected FrequentWebSitePresenter createPresenter() {
        return new FrequentWebSitePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_frequent_website;
    }

    @Override
    public void successGetFrequentWebSite(List<FrequentWebSite> list) {
        mAdapter.setList(list);
        smartrefreshlayout.finishRefresh();
    }
}