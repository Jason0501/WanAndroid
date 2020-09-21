package com.jason.www.fragment;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jason.www.R;
import com.jason.www.activity.LoginActivity;
import com.jason.www.base.BaseFragment;
import com.jason.www.config.Accounts;
import com.jason.www.utils.GlideUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

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
public class MineFragment extends BaseFragment {
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.civ_user_icon)
    ImageView civUserIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    @BindView(R.id.ll_user_id)
    LinearLayout llUserId;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.ll_user_level)
    LinearLayout llUserLevel;
    @BindView(R.id.tv_user_ranking)
    TextView tvUserRanking;
    @BindView(R.id.ll_user_ranking)
    LinearLayout llUserRanking;
    @BindView(R.id.ll_user_level_ranking)
    LinearLayout llUserLevelRanking;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.tv_coin)
    TextView tvCoin;
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
    protected void initView(View view) {
        super.initView(view);
        GlideUtils.loadAvatar(civUserIcon, Accounts.getUserAvatar());
        tvUserName.setText(Accounts.getUserName());
        tvUserId.setText(Accounts.getUserId() + "");
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.civ_user_icon, R.id.tv_user_name, R.id.tv_user_id, R.id.ll_user_id,
            R.id.tv_user_level, R.id.ll_user_level, R.id.tv_user_ranking, R.id.ll_user_ranking,
            R.id.ll_user_level_ranking, R.id.rl_user_info, R.id.tv_coin, R.id.ll_coin,
            R.id.ll_share, R.id.ll_collect, R.id.ll_read_record, R.id.ll_about_me,
            R.id.ll_setting, R.id.tv_login_out, R.id.tv_but_him_a_cup_of_coffee})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_user_icon:
                break;
            case R.id.tv_user_name:
                break;
            case R.id.tv_user_id:
                break;
            case R.id.ll_user_id:
                break;
            case R.id.tv_user_level:
                break;
            case R.id.ll_user_level:
                break;
            case R.id.tv_user_ranking:
                break;
            case R.id.ll_user_ranking:
                break;
            case R.id.ll_user_level_ranking:
                break;
            case R.id.rl_user_info:
                break;
            case R.id.tv_coin:
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
            case R.id.tv_but_him_a_cup_of_coffee:
                break;
        }
    }

    private void showLoginOutDialog() {
        new AlertDialog.Builder(mContext).setTitle("退出登录").setMessage("确认退出登录吗？").setPositiveButton("确定退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Accounts.loginOut();
                startActivity(LoginActivity.class);
                mActivity.finish();
            }
        }).create().show();
    }
}