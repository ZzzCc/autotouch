package com.hdf.autotouch.ui.resetloginpassword;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.ui.resetpaypassword.ResetPayPasswordActivity;
import com.hdf.autotouch.util.EncryptUtils;
import com.hdf.autotouch.util.SPManager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.tencent.captchasdk.TCaptchaDialog;

import org.json.JSONException;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 重置登录密码
 * </pre>
 */
public class ResetLoginPasswordActivity extends BaseActivity<ResetLoginPasswordPresenter> implements ResetLoginPasswordView {

    @BindView(R.id.group)
    Group           mGroup;
    @BindView(R.id.group_password)
    Group           mGroupPassword;
    @BindView(R.id.tv_area_code)
    TextView        mTvAreaCode;
    @BindView(R.id.view_divider)
    View            mViewDivider;
    @BindView(R.id.tv_phone)
    TextView        mTvPhone;
    @BindView(R.id.tv_email)
    TextView        mTvEmail;
    @BindView(R.id.send_verification_code)
    QMUIRoundButton mSendVerificationCode;
    @BindView(R.id.et_verification_code)
    EditText        mEtVerificationCode;
    @BindView(R.id.tv_confirm)
    TextView        mTvConfirm;
    @BindView(R.id.et_new_password)
    EditText        mEtNewPassword;
    @BindView(R.id.et_confirm_new_password)
    EditText        mEtConfirmNewPassword;
    private int            isIdentifying = 1;
    private int            intpassword;
    private CountDownTimer mCountDownTimer;

    //intpassword 1==重置支付密码  2==重置登录密码
    public static void start(Context context, int intpassword) {
        Intent intent = new Intent(context, ResetLoginPasswordActivity.class);
        intent.putExtra("intpassword", intpassword);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        intpassword = bundle.getInt("intpassword");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reset_login_password;
    }

    @Override
    public void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mPresenter = new ResetLoginPasswordPresenter(this);
        applyClickListener(mSendVerificationCode, mTvConfirm, mTvAreaCode);
        if (intpassword == 1) {
            if (SPManager.getHavePayPassword()) {
                setTitleText(R.string.reset_pay_password);
            } else {
                setTitleText(R.string.set_pay_password);
            }
        } else if (intpassword == 2) {
            setTitleText(R.string.reset_login_password);
        }
        mGroup.setVisibility(View.VISIBLE);
        mGroupPassword.setVisibility(View.GONE);

        mCountDownTimer = new CountDownTimer(Constant.SMS_WAIT_TIME * 1000, 1000) {
            @Override
            public void onTick(long millisLeft) {
                mSendVerificationCode.setEnabled(false);
                mSendVerificationCode.setFocusable(false);
                mSendVerificationCode.setText(String.format(getString(R.string.send_again), millisLeft / 1000));
            }

            @Override
            public void onFinish() {
                mSendVerificationCode.setEnabled(true);
                mSendVerificationCode.setFocusable(true);
                mSendVerificationCode.setText(getString(R.string.send_verification_code));
            }
        };

        if (StringUtils.isEmpty(SPManager.getAreaCode())) {
//            mTvAreaCode.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            mViewDivider.setBackgroundColor(Color.TRANSPARENT);
            mTvEmail.setText("当前账号: " + SPManager.getUserName());
        } else {
//            mTvAreaCode.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_arrow_down_light), null);
            mViewDivider.setBackgroundColor(ColorUtils.getColor(R.color.light_black3));
            mTvAreaCode.setText(SPManager.getAreaCode());
            mTvEmail.setText("当前账号: " + SPManager.getAreaCode() + " " + SPManager.getUserName());

        }
    }

    @Override
    public void doBusiness() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.send_verification_code: {
                TCaptchaDialog dialog = new TCaptchaDialog(this, Constant.T_CAPTCHA_ID, jsonObject -> {
                    try {
                        int ret = jsonObject.getInt("ret");
                        if (ret == 0) {
                            //验证成功回调
                            String appid = jsonObject.getString("appid");//appid
                            String randstr = jsonObject.getString("randstr");//随机串
                            String ticket = jsonObject.getString("ticket");//验证码票据
                            mCountDownTimer.start();
                            mPresenter.getCode(SPManager.getAreaCode(), SPManager.getUserName(), ticket, randstr);
                        } else if (ret == -1001) {
                            //验证码首个TCaptcha.js加载错误，业务可以根据需要重试
                            //jsonObject.getString("info")为错误信息
                        } else {
                            //验证失败回调，一般为用户关闭验证码弹框
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, null);
                dialog.show();
            }
            break;
            case R.id.tv_confirm: {
                String verificationCode = mEtVerificationCode.getText().toString();
                if (isIdentifying == 1) {
                    if (StringUtils.isSpace(verificationCode)) {
                        ToastUtils.showShort(mEtVerificationCode.getHint());
                    } else if (verificationCode.length() != 6) {
                        ToastUtils.showShort(R.string.verification_code_format_error);
                    } else {
                        mPresenter.getCheckVerifyCode(SPManager.getUserName(), SPManager.getAreaCode(), mEtVerificationCode.getText().toString());
                    }
                } else if (isIdentifying == 2) {
                    if (!StringUtils.isEmpty(mEtNewPassword.getText().toString()) && !StringUtils.isEmpty(mEtConfirmNewPassword.getText().toString())) {
                        if (!StringUtils.isSpace(mEtNewPassword.getText().toString()) && mEtNewPassword.getText().toString().length() >= 6) {
                            if (mEtNewPassword.getText().toString().equals(mEtConfirmNewPassword.getText().toString())) {
                                mPresenter.getChangePassword(EncryptUtils.encryptSha256(mEtNewPassword.getText().toString()));
                            } else {
                                ToastUtils.showShort(R.string.password_inconformity);
                            }
                        } else {
                            ToastUtils.showShort(R.string.input_password);
                        }
                    } else {
                        ToastUtils.showShort(R.string.input_password);
                    }
                }
            }
            break;
            default:
                break;
        }
    }

    //校验验证码
    @Override
    public void getCheckVerifyCode() {
        //1==重置支付密码  2==重置登录密码
        if (intpassword == 1) {
            ResetPayPasswordActivity.start(this);
            finish();
        } else if (intpassword == 2) {
            isIdentifying = 2;
            mGroup.setVisibility(View.GONE);
            mGroupPassword.setVisibility(View.VISIBLE);
        }
    }

    //修改成功
    @Override
    public void getChangePassword() {
        ToastUtils.showShort("修改成功");
        finish();
    }
}
