package com.jason.www.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jason.www.R;
import com.jason.www.base.ActivityStackManager;
import com.jason.www.base.BaseMvpActivity;
import com.jason.www.mvp.contract.RegisterContract;
import com.jason.www.http.response.Register;
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
        return R.layout.activity_register;
    }

    @OnClick(R.id.btn_regist)
    public void onViewClicked() {
        String username = edittextUsername.getText().toString();
        String password = edittextPassword.getText().toString();
        String repassword = edittextRepassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            showToast("请先输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("请先设置密码");
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            showToast("请先确认密码");
            return;
        }
        if (!TextUtils.equals(password, repassword)) {
            showToast("两次密码输入不一样");
            return;
        }
        getPresenter().register(username, password, repassword);
    }

    @Override
    public void successRegister(Register register) {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
        ActivityStackManager.getInstance().finishActivity(LoginActivity.class);
    }
}