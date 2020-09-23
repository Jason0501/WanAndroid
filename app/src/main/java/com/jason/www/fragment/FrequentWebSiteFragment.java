package com.jason.www.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.List;

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
public class FrequentWebSiteFragment extends BaseMvpFragment<FrequentWebSitePresenter> implements FrequentWebSiteContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.default_toolbar_textview_title)
    TextView textviewTitle;
    @BindView(R.id.default_toolbar_imageview_back)
    ImageView imageviewBack;
    private FrequentWebSiteAdapter mAdapter;

    private FrequentWebSiteFragment() {
    }

    public static Fragment getInstance() {
        return new FrequentWebSiteFragment();
    }

    @Override
    protected void initView() {
        super.initView();
        imageviewBack.setVisibility(View.GONE);
        textviewTitle.setText(getString(R.string.frequent_web_site));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(CommonItemDecoration.createVertical());
        mAdapter = new FrequentWebSiteAdapter();
        recyclerview.setAdapter(mAdapter);
        smartrefreshlayout.setEnablePureScrollMode(true);
    }

    @Override
    protected void initData() {
        super.initData();
        getPresenter().getFrequentWebSite();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
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
        return R.layout.layout_refresh_list_with_toolbar;
    }

    @Override
    public void successGetFrequentWebSite(List<FrequentWebSite> list) {
        mAdapter.setList(list);
    }
}