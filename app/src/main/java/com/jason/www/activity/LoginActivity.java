package com.jason.www.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jason.www.R;
import com.jason.www.base.BaseMvpActivity;
import com.jason.www.config.Accounts;
import com.jason.www.contract.LoginContract;
import com.jason.www.http.response.Login;
import com.jason.www.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.edittext_username)
    EditText edittextUsername;
    @BindView(R.id.edittext_password)
    EditText edittextPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

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
                getPresenter().login(username, password);
                break;
        }
    }

    private void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void successLogin(Login login) {
        Accounts.setIsLogin(true);
        startActivity(new Intent(mContext, MainActivity.class));
        mActivity.finish();
    }
}