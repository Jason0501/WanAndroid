package com.jason.www.fragment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jason.www.R;
import com.jason.www.adapter.QuestionAdapter;
import com.jason.www.base.BaseMvpFragment;
import com.jason.www.http.response.BaseListResponse;
import com.jason.www.http.response.Question;
import com.jason.www.mvp.contract.QuestionContract;
import com.jason.www.mvp.presenter.QuestionPresenter;
import com.jason.www.utils.IntentUtils;
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
public class QuestionFragment extends BaseMvpFragment<QuestionPresenter> implements QuestionContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    private QuestionAdapter mAdapter;
    private int mPage;
    private boolean mIsRefresh;

    private QuestionFragment() {
    }

    public static Fragment getInstance() {
        return new QuestionFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(CommonItemDecoration.createVertical());
        mAdapter = new QuestionAdapter();
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getPresenter().getQuestion(mPage);
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
                getPresenter().getQuestion(++mPage);
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
    protected QuestionPresenter createPresenter() {
        return new QuestionPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_refresh_list;
    }

    @Override
    public void successGetQuestion(BaseListResponse<Question> response) {
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
            mAdapter.setList(response.getDatas());
        } else {
            mAdapter.addData(response.getDatas());
        }
        mIsRefresh = false;
    }
}