package com.hdf.autotouch.ui.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.ui.aboutus.AboutUsActivity;
import com.hdf.autotouch.ui.bill.BillActivity;
import com.hdf.autotouch.ui.login.LoginActivity;
import com.hdf.autotouch.ui.myorder.MyOrderActivity;
import com.hdf.autotouch.ui.myteam.MyTeamActivity;
import com.hdf.autotouch.ui.recommend.RecommendActivity;
import com.hdf.autotouch.ui.settingcenter.SettingCenterActivity;
import com.hdf.autotouch.ui.web.WebActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.FormatUtils;
import com.hdf.autotouch.util.SPManager;

import butterknife.BindView;

public class MineActivity extends BaseActivity<MinePresenter> implements MineView {

    @BindView(R.id.tv_account)
    TextView  mTvAccount;
    @BindView(R.id.tv_level)
    TextView  mTvLevel;
    @BindView(R.id.tv_order_management)
    TextView  mTvOrderManagement;
    @BindView(R.id.tv_bill_management)
    TextView  mTvBillManagement;
    @BindView(R.id.tv_my_team)
    TextView  mTvMyTeam;
    @BindView(R.id.tv_about_us)
    TextView  mTvAboutUs;
    @BindView(R.id.tv_help_center)
    TextView  mTvHelpCenter;
    @BindView(R.id.iv_recommend)
    ImageView mIvRecommend;
    @BindView(R.id.tv_setting)
    TextView  mTvSetting;
    @BindView(R.id.tv_service)
    TextView  mTvService;
    @BindView(R.id.view_blank)
    View      mViewBlank;

    public static void start(Context context) {
        Intent intent = new Intent(context, MineActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine;
    }

    @Override
    public void initView() {
        mPresenter = new MinePresenter(this);
        applyClickListener(mTvAccount, mTvOrderManagement, mTvBillManagement, mTvMyTeam, mTvAboutUs,
                mTvHelpCenter, mIvRecommend, mTvSetting, mTvService, mViewBlank);

        updateMine();
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_account: {
                LoginActivity.start(this);
            }
            break;
            case R.id.tv_order_management: {
                if (isLogin()) {
                    MyOrderActivity.start(this);
                }
            }
            break;
            case R.id.tv_bill_management: {
                if (isLogin()) {
                    BillActivity.start(this);
                }
            }
            break;
            case R.id.tv_my_team: {
                if (isLogin()) {
                    MyTeamActivity.start(this);
                }
            }
            break;
            case R.id.tv_about_us: {
                AboutUsActivity.start(this);
            }
            break;
            case R.id.tv_help_center: {
                WebActivity.start(this, Constant.HELP_CENTER_URL);
            }
            break;
            case R.id.iv_recommend: {
                if (isLogin()) {
                    RecommendActivity.start(this);
                }
            }
            break;
            case R.id.tv_setting: {
                if (isLogin()) {
                    SettingCenterActivity.start(this);
                }
            }
            break;
            case R.id.tv_service: {
                mPresenter.getWechat();
            }
            break;
            case R.id.view_blank: {
                finish();
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        if (msg.what == R.id.msg_update_mine) {
            updateMine();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateMine() {
        if (!StringUtils.isEmpty(SPManager.getId())) {
            mTvAccount.setEnabled(false);
            mTvAccount.setText(SPManager.getAreaCode() + SPManager.getUserName());
            mTvLevel.setVisibility(View.VISIBLE);
            mTvLevel.setText(FormatUtils.getLevelType(SPManager.getLevel()));
        } else {
            mTvAccount.setEnabled(true);
            mTvAccount.setText(R.string.create_account_login);
            mTvLevel.setVisibility(View.GONE);
        }
    }

    @Override
    public void getWechat(String text) {
        DialogHelper.showCustomerServiceDialog(text);
    }
}
