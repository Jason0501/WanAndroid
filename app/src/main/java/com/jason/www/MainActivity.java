package com.jason.www;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.EditText;

import com.jason.www.base.BaseActivity;
import com.jason.www.net.RetrofitUrl;
import com.jason.www.utils.SystemUtils;

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
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {
    @BindView(R.id.edittext_username)
    EditText edittextUsername;
    @BindView(R.id.edittext_password)
    EditText edittextPassword;
    @BindView(R.id.edittext_repassword)
    EditText edittextRepassword;
    @BindView(R.id.btn_regist)
    Button btnRegist;

    @Override
    protected void initView() {
        edittextUsername.setText("不良少年");
        edittextPassword.setText("123456");
        edittextRepassword.setText(edittextPassword.getText());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemUtils.killMySelfProcess();
    }

    @OnClick(R.id.btn_regist)
    public void onViewClicked() {
        String userName = edittextUsername.getText().toString();
        String password = edittextPassword.getText().toString();
        String repassword = edittextRepassword.getText().toString();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
        builder.hostnameVerifier(new TrustAllHostnameVerifier());
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(RetrofitUrl.baseUrl)
                .build();
        RetrofitUrl personalProtocol = retrofit.create(RetrofitUrl.class);
        Call<ResponseBody> call = personalProtocol.register(userName, password, repassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                showToast(response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showLog(t.getMessage());
            }
        });

//        RetrofitHelper.getInstance().getRetrofitUrl().register(userName, password, repassword).enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                showToast(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                showLog(t.getMessage());
//            }
//        });
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
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
}