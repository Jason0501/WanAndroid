package com.jason.www.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jason.www.R;
import com.jason.www.base.BaseMvpActivity;
import com.jason.www.http.response.Register;
import com.jason.www.mvp.contract.RegisterContract;
import com.jason.www.mvp.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.edittext_username)
    EditText edittextUsername;
    @BindView(R.id.edittext_password)
    EditText edittextPassword;
    @BindView(R.id.edittext_repassword)
    EditText edittextRepassword;
    @BindView(R.id.btn_regist)
    Button btnRegist;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView(View decorView) {
        super.initView(decorView);
        edittextUsername.setText("不良少年");
        edittextPassword.setText("123456");
        edittextRepassword.setText(edittextPassword.getText());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.btn_regist)
    public void onViewClicked() {
        String username = edittextUsername.getText().toString();
        String password = edittextPassword.getText().toString();
        String repassword = edittextRepassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            showToast(getString(R.string.please_input_username));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.please_set_password_first));
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            showToast(getString(R.string.please_confirm_password_first));
            return;
        }
        if (!TextUtils.equals(password, repassword)) {
            showToast(getString(R.string.different_password));
            return;
        }
        getPresenter().register(username, password, repassword);
    }

    @Override
    public void successRegister(Register register) {
        showToast("注册成功");
        finish();
    }
}