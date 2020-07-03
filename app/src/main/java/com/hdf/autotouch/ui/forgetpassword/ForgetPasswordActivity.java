package com.hdf.autotouch.ui.forgetpassword;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.ui.areacode.AreaCodeActivity;
import com.hdf.autotouch.util.EncryptUtils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.tencent.captchasdk.TCaptchaDialog;

import org.json.JSONException;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/12
 *     desc  : 忘记密码
 * </pre>
 */
public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements ForgetPasswordView {

    @BindView(R.id.view_bg1)
    View            mViewBg1;
    @BindView(R.id.tv_area_code)
    TextView        mTvAreaCode;
    @BindView(R.id.view_divider)
    View            mViewDivider;
    @BindView(R.id.et_phone_number)
    EditText        mEtPhoneNumber;
    @BindView(R.id.et_email)
    EditText        mEtEmail;
    @BindView(R.id.view_bg2)
    View            mViewBg2;
    @BindView(R.id.et_verification_code)
    EditText        mEtVerificationCode;
    @BindView(R.id.btn_send)
    QMUIRoundButton mBtnSend;
    @BindView(R.id.view_bg3)
    View            mViewBg3;
    @BindView(R.id.et_password)
    EditText        mEtPassword;
    @BindView(R.id.view_bg4)
    View            mViewBg4;
    @BindView(R.id.et_confirm_new_password)
    EditText        mEtConfirmNewPassword;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton mBtnConfirm;
    private CountDownTimer mCountDownTimer;
    private int            mType;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        intent.putExtra("type", type);
        ActivityUtils.startActivity(intent);
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
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mType = bundle.getInt("type");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView() {
        mPresenter = new ForgetPasswordPresenter(this);

        mViewBgTopBar.setBackgroundColor(Color.TRANSPARENT);

        applyClickListener(mTvAreaCode, mBtnSend, mBtnConfirm);

//        mEtPhoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                mViewBg1.setBackgroundResource(R.drawable.bg_stroke_blue_4);
//            } else {
//                mViewBg1.setBackgroundResource(R.drawable.bg_stroke_black3_4);
//            }
//        });
//        mEtEmail.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                mViewBg1.setBackgroundResource(R.drawable.bg_stroke_blue_4);
//            } else {
//                mViewBg1.setBackgroundResource(R.drawable.bg_stroke_black3_4);
//            }
//        });
//        mEtVerificationCode.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                mViewBg2.setBackgroundResource(R.drawable.bg_stroke_blue_4);
//            } else {
//                mViewBg2.setBackgroundResource(R.drawable.bg_stroke_black3_4);
//            }
//        });
//        mEtPassword.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                mViewBg3.setBackgroundResource(R.drawable.bg_stroke_blue_4);
//            } else {
//                mViewBg3.setBackgroundResource(R.drawable.bg_stroke_black3_4);
//            }
//        });
//        mEtConfirmNewPassword.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                mViewBg4.setBackgroundResource(R.drawable.bg_stroke_blue_4);
//            } else {
//                mViewBg4.setBackgroundResource(R.drawable.bg_stroke_black3_4);
//            }
//        });
        mCountDownTimer = new CountDownTimer(Constant.SMS_WAIT_TIME * 1000, 1000) {
            @Override
            public void onTick(long millisLeft) {
                mBtnSend.setEnabled(false);
                mBtnSend.setFocusable(false);
                mBtnSend.setText(String.format(getString(R.string.send_again), millisLeft / 1000));
            }

            @Override
            public void onFinish() {
                mBtnSend.setEnabled(true);
                mBtnSend.setFocusable(true);
                mBtnSend.setText(getString(R.string.send_verification_code));
            }
        };
        if (mType == 0) {
            mTvAreaCode.setVisibility(View.VISIBLE);
            mViewDivider.setVisibility(View.VISIBLE);
            mEtPhoneNumber.setVisibility(View.VISIBLE);
            mEtEmail.setVisibility(View.INVISIBLE);
            mEtPhoneNumber.requestFocus();
        } else {
            mTvAreaCode.setVisibility(View.INVISIBLE);
            mViewDivider.setVisibility(View.INVISIBLE);
            mEtPhoneNumber.setVisibility(View.INVISIBLE);
            mEtEmail.setVisibility(View.VISIBLE);
            mEtEmail.requestFocus();
        }
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_area_code: {
                AreaCodeActivity.start(this);
            }
            break;
            case R.id.btn_send: {
                String areaCode = mTvAreaCode.getText().toString();
                String phoneNumber = mEtPhoneNumber.getText().toString();
                String email = mEtEmail.getText().toString();
                if (mType == 0) {
                    if (StringUtils.isSpace(phoneNumber)) {
                        ToastUtils.showShort(mEtPhoneNumber.getHint());
                    } else if (StringUtils.equals("+86", areaCode) && !phoneNumber.matches(Constant.REGEX_MOBILE_EXACT)) {
                        ToastUtils.showShort(R.string.phone_number_format_error);
                    } else {
                        TCaptchaDialog dialog = new TCaptchaDialog(this, Constant.T_CAPTCHA_ID, jsonObject -> {
                            try {
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    //验证成功回调
                                    String appid = jsonObject.getString("appid");//appid
                                    String randstr = jsonObject.getString("randstr");//随机串
                                    String ticket = jsonObject.getString("ticket");//验证码票据
                                    mCountDownTimer.start();
                                    mPresenter.getCode(areaCode, phoneNumber, ticket, randstr);
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
                } else {
                    if (StringUtils.isSpace(email)) {
                        ToastUtils.showShort(mEtEmail.getHint());
                    } else if (!RegexUtils.isEmail(email)) {
                        ToastUtils.showShort(R.string.email_format_error);
                    } else {
                        TCaptchaDialog dialog = new TCaptchaDialog(this, Constant.T_CAPTCHA_ID, jsonObject -> {
                            try {
                                int ret = jsonObject.getInt("ret");
                                if (ret == 0) {
                                    //验证成功回调
                                    String appid = jsonObject.getString("appid");//appid
                                    String randstr = jsonObject.getString("randstr");//随机串
                                    String ticket = jsonObject.getString("ticket");//验证码票据
                                    mCountDownTimer.start();
                                    mPresenter.getCode("", email, ticket, randstr);
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
                }
            }
            break;
            case R.id.btn_confirm: {
                String areaCode = mTvAreaCode.getText().toString();
                String phoneNumber = mEtPhoneNumber.getText().toString();
                String email = mEtEmail.getText().toString();
                String verificationCode = mEtVerificationCode.getText().toString();
                String password = mEtPassword.getText().toString();
                String confirmNewPassword = mEtConfirmNewPassword.getText().toString();
                if (mType == 0) {
                    if (StringUtils.isSpace(phoneNumber)) {
                        ToastUtils.showShort(R.string.input_phone_number);
                    } else if (StringUtils.equals(StringUtils.getString(R.string.default_area_code), areaCode)
                            && !RegexUtils.isMobileExact(phoneNumber)) {
                        ToastUtils.showShort(R.string.phone_number_format_error);
                    } else if (StringUtils.isSpace(verificationCode)) {
                        ToastUtils.showShort(mEtVerificationCode.getHint());
                    } else if (verificationCode.length() != 6) {
                        ToastUtils.showShort(R.string.verification_code_format_error);
                    } else if (StringUtils.isSpace(password)
                            || password.length() < 6) {
                        ToastUtils.showShort(mEtPassword.getHint());
                    } else if (StringUtils.isSpace(confirmNewPassword)) {
                        ToastUtils.showShort(mEtConfirmNewPassword.getHint());
                    } else if (!StringUtils.equals(password, confirmNewPassword)) {
                        ToastUtils.showShort(R.string.password_inconformity);
                    } else {
                        mPresenter.forgetPassword(areaCode, phoneNumber, verificationCode, EncryptUtils.encryptSha256(password));
                    }
                } else {
                    if (StringUtils.isSpace(email)) {
                        ToastUtils.showShort(R.string.input_email);
                    } else if (!RegexUtils.isEmail(email)) {
                        ToastUtils.showShort(R.string.email_format_error);
                    } else if (StringUtils.isSpace(verificationCode)) {
                        ToastUtils.showShort(mEtVerificationCode.getHint());
                    } else if (verificationCode.length() != 6) {
                        ToastUtils.showShort(R.string.verification_code_format_error);
                    } else if (StringUtils.isSpace(password)
                            || password.length() < 6) {
                        ToastUtils.showShort(mEtPassword.getHint());
                    } else if (StringUtils.isSpace(confirmNewPassword)) {
                        ToastUtils.showShort(mEtConfirmNewPassword.getHint());
                    } else if (!StringUtils.equals(password, confirmNewPassword)) {
                        ToastUtils.showShort(R.string.password_inconformity);
                    } else {
                        mPresenter.forgetPassword("", email, verificationCode, EncryptUtils.encryptSha256(password));
                    }
                }
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void forgetPassword() {
        ToastUtils.showShort(R.string.reset_login_password_seccess);
        finish();
    }
}
