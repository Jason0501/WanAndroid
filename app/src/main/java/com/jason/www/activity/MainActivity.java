package com.jason.www.activity;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.reflect.TypeToken;
import com.jason.www.R;
import com.jason.www.adapter.HomeAdapter;
import com.jason.www.base.BaseActivity;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.SmartHttpCallback;
import com.jason.www.http.response.HomeArticleBody;
import com.jason.www.http.response.WeChatPublicAccount;
import com.jason.www.http.response.base.BaseResponse;
import com.jason.www.utils.IntentUtils;
import com.jason.www.utils.SystemUtils;
import com.jason.www.utils.ViewUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;

@RuntimePermissions
public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.textview_title)
    TextView textviewTitle;
    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.textview_username)
    TextView textviewUsername;
    @BindView(R.id.textview_userinfo)
    TextView textviewUserinfo;
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
//        smartRefreshLayout.autoRefresh();
        requestHomeArticle();
//        requestWeChatPublicAccounts();
    }

    private void requestWeChatPublicAccounts() {
        RetrofitHelper.enqueue2(new SmartHttpCallback<BaseResponse<List<WeChatPublicAccount>>>() {
            @Override
            public void success(BaseResponse<List<WeChatPublicAccount>> response) {
                if (response.isOk()) {
                    showToast("success to get wechat public accounts");
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

    @OnClick(R.id.textview_title)
    public void onViewClicked() {
        MainActivityPermissionsDispatcher.onStoragePermissionGrantedWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStoragePermissionGranted() {
        Log.d("MainActivity", "onStoragePermissionGranted");
        CharSequence text = textviewUsername.getText();
        textviewUsername.setText("******");
        CharSequence title = textviewTitle.getText();
        textviewTitle.setText("************");
        ViewUtils.screenShot(getWindow().getDecorView());
        textviewUsername.setText(text);
        textviewTitle.setText(title);
        showToast("截图已保存至相册");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowStoragePermission(final PermissionRequest request) {
        Log.d("MainActivity", "onShowStoragePermission");
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStoragePermissionDenied() {
        Log.d("MainActivity", "onStoragePermissionDenied");
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onStorageNeverAskAgain() {
        Log.d("MainActivity", "onStorageNeverAskAgain");
    }
}