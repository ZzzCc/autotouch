package com.hdf.autotouch.ui.settingcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.ui.resetloginpassword.ResetLoginPasswordActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.SPManager;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 设置中心
 * </pre>
 */
public class SettingCenterActivity extends BaseActivity {

    @BindView(R.id.tv_reset_pay_password)
    TextView mTvResetPayPassword;
    @BindView(R.id.tv_reset_login_password)
    TextView mTvResetLoginPassword;
    @BindView(R.id.tv_exit_login)
    TextView mTvExitLogin;

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingCenterActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_setting_center;
    }

    @Override
    public void initView() {
        setTitleText(R.string.setting_center);
        applyClickListener(mTvResetPayPassword, mTvResetLoginPassword, mTvExitLogin);
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_reset_pay_password://重置支付密码
                ResetLoginPasswordActivity.start(this, 1);
                break;
            case R.id.tv_reset_login_password://重置登录密码
                ResetLoginPasswordActivity.start(this, 2);
                break;
            case R.id.tv_exit_login://退出登录
                DialogHelper.showConfirmDialog(R.string.exit_login, "", () -> {
                    SPManager.clearUser();
                    sendMessage(R.id.msg_update_mine, null);
                    finish();
                });
                break;
            default:
                break;
        }
    }
}
