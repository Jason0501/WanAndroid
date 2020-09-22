package com.jason.www.fragment;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jason.www.R;
import com.jason.www.activity.LoginActivity;
import com.jason.www.base.BaseMvpFragment;
import com.jason.www.config.Accounts;
import com.jason.www.event.LoginEvent;
import com.jason.www.http.RetrofitHelper;
import com.jason.www.http.response.UserInfo;
import com.jason.www.mvp.contract.MineContract;
import com.jason.www.mvp.presenter.MinePresenter;
import com.jason.www.utils.GlideUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author：动脑学院-Zee老师
 * @date：2020/9/16 22:05
 * @email：575569745@qq.com
 * @description:
 */
public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.View {
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.civ_user_icon)
    ImageView civUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.tv_user_ranking)
    TextView tvUserRanking;
    @BindView(R.id.ll_user_level_ranking)
    LinearLayout llUserLevelRanking;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;
    @BindView(R.id.ll_coin)
    LinearLayout llCoin;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.ll_read_record)
    LinearLayout llReadRecord;
    @BindView(R.id.ll_about_me)
    LinearLayout llAboutMe;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.nsv)
    NestedScrollView nsv;

    @Override
    protected void initView() {
        super.initView();
        bindView();
    }

    private void bindView() {
        if (!Accounts.getIsLogin()) {
            tvUserName.setText(getString(R.string.go_to_login));
            tvUserId.setVisibility(View.GONE);
            tvUserLevel.setVisibility(View.GONE);
            tvUserRanking.setVisibility(View.GONE);
            tvLoginOut.setVisibility(View.GONE);
            tvCoin.setText("--");
        } else {
            tvLoginOut.setVisibility(View.VISIBLE);
            tvUserId.setVisibility(View.VISIBLE);
            tvUserLevel.setVisibility(View.VISIBLE);
            tvUserRanking.setVisibility(View.VISIBLE);
            tvUserName.setText("--");
            tvUserId.setText("--");
            tvUserLevel.setText("--");
            tvUserRanking.setText("--");
            tvCoin.setText("--");
        }
    }

    @Override
    protected void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
        if (isFirstVisible) {
            loadData();
        }
    }

    private void loadData() {
        if (Accounts.getIsLogin()) {
            getPresenter().getUserInfo();
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEvent(LoginEvent loginEvent) {
        if (isDetached()) {
            return;
        }
        if (loginEvent.isLogin()) {
            bindView();
            loadData();
        }
    }

    @OnClick({R.id.civ_user_icon, R.id.ll_coin, R.id.ll_share, R.id.ll_collect, R.id.ll_read_record,
            R.id.ll_about_me, R.id.ll_setting, R.id.tv_login_out, R.id.tv_user_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_user_icon:
                break;
            case R.id.tv_user_name:
                if (Accounts.getIsLogin()) {
                    return;
                }
                startActivity(LoginActivity.class);
                break;
            case R.id.ll_coin:
                break;
            case R.id.ll_share:
                break;
            case R.id.ll_collect:
                break;
            case R.id.ll_read_record:
                break;
            case R.id.ll_about_me:
                break;
            case R.id.ll_setting:
                break;
            case R.id.tv_login_out:
                showLoginOutDialog();
                break;
        }
    }

    private void showLoginOutDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.dialog_title_login_out))
                .setMessage(getString(R.string.dialog_message_login_out))
                .setPositiveButton(getString(R.string.dialog_positive_button_login_out), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Accounts.loginOut();
                        RetrofitHelper.clearCookie();
                        bindView();
                    }
                }).create().show();
    }

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public void successGetUserInfo(UserInfo userInfo) {
        tvCoin.setText(String.valueOf(userInfo.getCoinCount()));
        tvUserId.setText(getString(R.string.format_user_id, userInfo.getUserId()));
        tvUserLevel.setText(getString(R.string.format_user_level, userInfo.getLevel()));
        tvUserName.setText(userInfo.getUsername());
        tvUserRanking.setText(getString(R.string.format_user_rank, userInfo.getRank()));
        GlideUtils.loadAvatar(civUserIcon, Accounts.getUserAvatar());
    }

    @Override
    public void failLoad(String msg) {
        super.failLoad(msg);
        tvUserName.setText(getString(R.string.go_to_login));
        tvUserId.setText("--");
        tvUserLevel.setText("--");
        tvUserRanking.setText("--");
        tvCoin.setText("--");
    }
}