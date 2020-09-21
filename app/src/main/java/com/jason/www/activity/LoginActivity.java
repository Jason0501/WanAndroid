package com.jason.www.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jason.www.R;
import com.jason.www.base.BaseMvpActivity;
import com.jason.www.config.Accounts;
import com.jason.www.event.LoginEvent;
import com.jason.www.http.response.Login;
import com.jason.www.mvp.contract.LoginContract;
import com.jason.www.mvp.presenter.LoginPresenter;

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
    protected void initView(View decorView) {
        super.initView(decorView);
        edittextUsername.setText("不良少年");
        edittextPassword.setText("123456");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.btn_login, R.id.textview_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textview_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.btn_login:
                String username = edittextUsername.getText().toString();
                String password = edittextPassword.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    showToast(getString(R.string.please_input_username));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast(getString(R.string.please_input_password));
                    return;
                }
                getPresenter().login(username, password);
                break;
        }
    }

    @Override
    public void successLogin(Login login) {
        Accounts.setLoginInfo(login);
        new LoginEvent(true).post();
        mActivity.finish();
    }
}