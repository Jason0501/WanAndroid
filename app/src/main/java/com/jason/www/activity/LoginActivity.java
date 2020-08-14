package com.jason.www.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jason.www.R;
import com.jason.www.base.BaseActivity;
import com.jason.www.net.Urls;
import com.jason.www.net.response.Login;
import com.jason.www.net.response.base.BaseResponse;
import com.jason.www.utils.LogUtils;
import com.jason.www.utils.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edittext_username)
    EditText edittextUsername;
    @BindView(R.id.edittext_password)
    EditText edittextPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void initView() {
        edittextUsername.setText("不良少年");
        edittextPassword.setText("123456");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.btn_login, R.id.textview_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textview_register:
                register();
                break;
            case R.id.btn_login:
                String username = edittextUsername.getText().toString();
                String password = edittextPassword.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    showToast("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
//                login(username, password);
                testGson();
                testGson2();
                break;
        }
    }

    private void testGson() {
        BaseResponse response = new BaseResponse();
        response.errorCode = 200;
        response.errorMsg = "请求成功";
        Login login = new Login();
        login.admin = true;
        login.chapterTops = 99;
        login.coinCount = 51;
        login.collectIds = 10086;
        login.email = "1129847330@qq.com";
        login.icon = "beauty.jpg";
        login.id = 100;
        login.nickname = "和尚用飘柔";
        login.password = "123456789";
        login.publicName = "不良少年";
        login.token = "12sdaf46a4ds321f3a";
        login.type = 5;
        login.username = "扫黄大队";
        response.data = login;
        String json = new Gson().toJson(response);
        LogUtils.i("生成的Json：\r\n" + json);
        BaseResponse fromJson = new Gson().fromJson(json, new TypeToken<BaseResponse>() {
        }.getType());
        LogUtils.i("解析后的对象：\r\n" + fromJson.toString());
    }

    private void testGson2() {
        String jsonStr = "{\"a\":\"请求成功\",\"b\":200,\"c\":{\"a\":true,\"b\":51,\"c\":\"1129847330@qq.com\",\"d\":\"beauty.jpg\",\"e\":100,\"f\":\"和尚用飘柔\"," +
                "\"g\":\"123456789\",\"h\":\"不良少年\",\"i\":\"12sdaf46a4ds321f3a\",\"j\":5,\"k\":\"扫黄大队\",\"l\":99,\"m\":10086}}";
        BaseResponse response = new Gson().fromJson(jsonStr, new TypeToken<BaseResponse>() {
        }.getType());
        LogUtils.i("原Json串：\r\n" + jsonStr);
        LogUtils.i("解析后：\r\n" + response.toString());
    }

    private void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void login(String username, String password) {
        RequestParams requestParams = new RequestParams();
        requestParams.setSslSocketFactory(createSSLSocketFactory());
        requestParams.setUri(Urls.USER_LOGIN);
        requestParams.addBodyParameter("username", username);
        requestParams.addBodyParameter("password", password);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.i("onSuccess:\r\n" + result);
                BaseResponse response = new Gson().fromJson(result, new TypeToken<BaseResponse>() {
                }.getType());
                LogUtils.i(response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int errorCode = jsonObject.getInt("errorCode");
                    String errorMsg = jsonObject.getString("errorMsg");
                    JSONObject data = jsonObject.getJSONObject("data");
                    String nickname = data.getString("nickname");
                    int id = data.getInt("id");
                    LogUtils.i(errorMsg + "," + errorCode + "," + nickname + "," + id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.i("onError:\r\n" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {


        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {


        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtils.killMySelfProcess();
    }
}