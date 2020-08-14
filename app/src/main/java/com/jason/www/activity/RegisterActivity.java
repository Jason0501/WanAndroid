package com.jason.www.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jason.www.R;
import com.jason.www.base.BaseActivity;
import com.jason.www.net.RetrofitHelper;
import com.jason.www.net.response.base.BaseResponse;
import com.jason.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
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
        return R.layout.activity_register;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btn_regist)
    public void onViewClicked() {
        String userName = edittextUsername.getText().toString();
        String password = edittextPassword.getText().toString();
        String repassword = edittextRepassword.getText().toString();
        if (TextUtils.isEmpty(userName)) {
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
        RetrofitHelper.getInstance()
                .getRetrofitUrl()
                .register(userName, password, repassword)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        LogUtils.i("RegisterActivity:" + response.body() + "");
                        if (response.body().errorCode == BaseResponse.SUCCESS) {
                            showToast("注册成功");
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            showToast("注册失败：" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        showToast(t.getLocalizedMessage());
                    }
                });
    }
}