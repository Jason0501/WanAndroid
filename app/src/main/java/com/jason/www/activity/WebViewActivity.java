package com.jason.www.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gyf.immersionbar.ImmersionBar;
import com.jason.www.R;
import com.jason.www.base.BaseActivity;
import com.jason.www.config.Constants;
import com.jason.www.utils.LogUtils;

import butterknife.BindView;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebViewActivity extends BaseActivity {
    private static final String TAG = "WebViewActivity";
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    private String mUrl;

    @Override
    protected void initView() {
        super.initView();
        initWebView();
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
        if (!TextUtils.isEmpty(mUrl)) {
            webView.loadUrl(mUrl);
        }
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setAllowContentAccess(true);
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setSupportZoom(true);  //支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setBuiltInZoomControls(true); //设置支持缩放
        webSettings.setDisplayZoomControls(false);//隐藏控件默认缩放按钮
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        webSettings.setDomStorageEnabled(true);
        initWebViewClient();
    }

    private void initWebViewClient() {
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebView.HitTestResult hit = view.getHitTestResult();
                //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
                    //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                    Log.d(TAG, "--------------------------------------------------");
                    Log.d(TAG, "getType()：" + hit.getType());
                    Log.d(TAG, "getExtra()：" + hit.getExtra());
                    Log.d(TAG, "getUrl()：" + view.getUrl());
                    Log.d(TAG, "getOriginalUrl()：" + view.getOriginalUrl());
                    Log.d(TAG, "URL：" + url);
                }
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    //加载的url是http/https协议地址
                    view.loadUrl(url);
                    //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
                    return false;
                } else {
                    //加载的url是自定义协议地址，启动系统浏览器打开
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        //进度监听
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                LogUtils.i("加载进度：" + newProgress + "%");
                if (progressBar == null) {
                    return;
                }
                progressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //显示网页标题
                if (!TextUtils.isEmpty(mUrl)) {
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            //清除网页访问留下的缓存
            //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearCache(true);
            //清除当前webview访问的历史记录
            //只会webview访问历史记录里的所有记录除了当前访问记录
            webView.clearHistory();
            webView.clearSslPreferences();
            webView.clearMatches();
            //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
            webView.clearFormData();
            LinearLayout parent = (LinearLayout) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.destroy();
            webView = null;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }
}