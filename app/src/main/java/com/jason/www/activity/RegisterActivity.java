package com.jason.www.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.jason.www.R;
import com.jason.www.base.BaseActivity;
import com.jason.www.net.HttpRequestCallback;
import com.jason.www.net.RetrofitHelper;
import com.jason.www.net.response.Register;
import com.jason.www.net.response.base.BaseResponse;
import com.jason.www.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

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
        register(username, password, repassword);
    }

    private void register(String username, String password, String repassword) {
        RetrofitHelper.getInstance().enqueue(new HttpRequestCallback<BaseResponse<Register>>() {
            @Override
            public void success(BaseResponse<Register> response) {
                LogUtils.i(response.toString());
                if (response.isOk()) {
                    startActivity(new Intent(mContext, MainActivity.class));
                }
            }

            @Override
            public void fail(int code, String msg) {
                LogUtils.i(msg);
            }

            @Override
            public Call<ResponseBody> getApi() {
                return RetrofitHelper.getInstance().getRetrofitUrl().register(username, password, repassword);
            }
        }, new TypeToken<BaseResponse<Register>>() {
        }.getType());
    }
}