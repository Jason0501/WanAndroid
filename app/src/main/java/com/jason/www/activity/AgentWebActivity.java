package com.jason.www.activity;

import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.jason.www.R;
import com.jason.www.base.BaseActivity;
import com.jason.www.config.Constants;
import com.jason.www.utils.TextUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import androidx.annotation.RequiresApi;
import butterknife.BindView;

public class AgentWebActivity extends BaseActivity {
    private static final String TAG = "AgentWebActivity";
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private String mUrl;
    private AgentWeb mAgentWeb;
    public static final String SHARE_EXPRESS = "https://m.kuaidi100.com/app/query/";

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .statusBarView(R.id.statusbar)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constants.IntentKey.WEBVIEW_URL);
        // : 2020/11/23 调试
//        mUrl = SHARE_EXPRESS;
//        mUrl = "192.168.1.24:8080/pages/common/doc";
        if (!TextUtils.isEmpty(mUrl)) {
            if (!mUrl.startsWith("http")) {
                mUrl = "http://" + mUrl;
            }
        }
        initAgentWeb();
    }

    private void initAgentWeb() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(llRoot, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                //打开其他应用时，弹窗咨询用户是否前往其他应用
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                //拦截找不到相关页面的Scheme
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private com.just.agentweb.WebViewClient mWebViewClient = new WebViewClient() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
    };
    private com.just.agentweb.WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_agent_web;
    }
}