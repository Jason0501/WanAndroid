package com.jason.www.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.jason.www.R;
import com.jason.www.base.BaseActivity;
import com.jason.www.net.BaseHttpCallback;
import com.jason.www.net.RetrofitHelper;
import com.jason.www.net.response.Login;
import com.jason.www.net.response.base.BaseResponse;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

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
                login(username, password);
                break;
        }
    }

    private void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void login(String username, String password) {
        RetrofitHelper.getInstance().enqueue(new BaseHttpCallback<Login>() {
            @Override
            public void success(BaseResponse<Login> response) {
                if (response.isOk()) {
                    startActivity(new Intent(mContext, MainActivity.class));
                    mActivity.finish();
                } else {
                    showToast(response.errorMsg);
                }
            }

            @Override
            public void fail(int code, String msg) {
                showToast(msg);
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getInstance().getRetrofitUrl().login(username, password);
            }
        }, new TypeToken<BaseResponse<Login>>() {
        }.getType());
    }
}