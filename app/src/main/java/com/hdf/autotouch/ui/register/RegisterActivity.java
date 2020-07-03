package com.hdf.autotouch.ui.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.ui.areacode.AreaCodeActivity;
import com.hdf.autotouch.ui.web.WebActivity;
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
 *     time  : 2019/7/12
 *     desc  : 注册
 * </pre>
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView {

    @BindView(R.id.tv_type)
    TextView        mTvType;
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
    @BindView(R.id.et_invitation_code)
    EditText        mEtInvitationCode;
    @BindView(R.id.btn_register)
    QMUIRoundButton mBtnRegister;
    @BindView(R.id.cb_read_agree)
    CheckBox        mCbReadAgree;
    @BindView(R.id.tv_user_agreement)
    TextView        mTvUserAgreement;
    private CountDownTimer mCountDownTimer;

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
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
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        mPresenter = new RegisterPresenter(this);
        applyLightMode(false);
        mViewBgTopBar.setBackgroundColor(Color.TRANSPARENT);

        applyClickListener(mTvType, mTvAreaCode, mBtnSend, mBtnRegister, mTvUserAgreement);

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
//        mEtInvitationCode.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                mViewBg4.setBackgroundResource(R.drawable.bg_stroke_blue_4);
//            } else {
//                mViewBg4.setBackgroundResource(R.drawable.bg_stroke_black3_4);
//
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
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_type: {
                String type = mTvType.getText().toString();
                if (StringUtils.equals(type, getString(R.string.email_register))) {
                    mTvType.setText(R.string.phone_register);
                    mTvAreaCode.setVisibility(View.INVISIBLE);
                    mViewDivider.setVisibility(View.INVISIBLE);
                    mEtPhoneNumber.setVisibility(View.INVISIBLE);
                    mEtEmail.setVisibility(View.VISIBLE);
                    mEtEmail.requestFocus();
                } else {
                    mTvType.setText(R.string.email_register);
                    mTvAreaCode.setVisibility(View.VISIBLE);
                    mViewDivider.setVisibility(View.VISIBLE);
                    mEtPhoneNumber.setVisibility(View.VISIBLE);
                    mEtEmail.setVisibility(View.INVISIBLE);
                    mEtPhoneNumber.requestFocus();
                }
            }
            break;
            case R.id.tv_area_code: {
                AreaCodeActivity.start(this);
            }
            break;
            case R.id.btn_send: {
                String type = mTvType.getText().toString();
                String areaCode = mTvAreaCode.getText().toString();
                String phoneNumber = mEtPhoneNumber.getText().toString();
                String email = mEtEmail.getText().toString();
                if (StringUtils.equals(type, getString(R.string.email_register))) {
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
            break;
            case R.id.btn_register: {
                String type = mTvType.getText().toString();
                String areaCode = mTvAreaCode.getText().toString();
                String phoneNumber = mEtPhoneNumber.getText().toString();
                String email = mEtEmail.getText().toString();
                String verificationCode = mEtVerificationCode.getText().toString();
                String password = mEtPassword.getText().toString();
                String invitationCode = mEtInvitationCode.getText().toString();
                if (StringUtils.equals(type, getString(R.string.email_register))) {
                    if (StringUtils.isSpace(phoneNumber)) {
                        ToastUtils.showShort(R.string.input_phone_number);
                    } else if (StringUtils.equals(StringUtils.getString(R.string.default_area_code), areaCode)
                            && !phoneNumber.matches(Constant.REGEX_MOBILE_EXACT)) {
                        ToastUtils.showShort(R.string.phone_number_format_error);
                    } else if (StringUtils.isSpace(verificationCode)) {
                        ToastUtils.showShort(mEtVerificationCode.getHint());
                    } else if (verificationCode.length() != 6) {
                        ToastUtils.showShort(R.string.verification_code_format_error);
                    } else if (StringUtils.isSpace(password)
                            || password.length() < 6) {
                        ToastUtils.showShort(mEtPassword.getHint());
                    } else if (StringUtils.isSpace(invitationCode)) {
                        ToastUtils.showShort(mEtInvitationCode.getHint());
                    } else if (!mCbReadAgree.isChecked()) {
                        ToastUtils.showShort(R.string.read_agree_user_agreement);
                    } else {
                        mPresenter.register(areaCode, phoneNumber, verificationCode, EncryptUtils.encryptSha256(password), invitationCode);
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
                    } else if (StringUtils.isSpace(invitationCode)) {
                        ToastUtils.showShort(mEtInvitationCode.getHint());
                    } else if (!mCbReadAgree.isChecked()) {
                        ToastUtils.showShort(R.string.read_agree_user_agreement);
                    } else {
                        mPresenter.register("", email, verificationCode, EncryptUtils.encryptSha256(password), invitationCode);
                    }
                }
            }
            break;
            case R.id.tv_user_agreement: {
                WebActivity.start(this, Constant.URL_USER_AGREEMENT);
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void register(User user) {
        SPManager.setUser(user.getUid(), user.getAccount(), user.getPrefix(), user.isWhetherPaypassword(), user.getInviteCode(), user.getLevel() + "", user.getCreateTime());
        sendMessage(R.id.msg_update_mine, null);
        finish();
    }
}
