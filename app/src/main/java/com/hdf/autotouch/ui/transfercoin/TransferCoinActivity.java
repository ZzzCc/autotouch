package com.hdf.autotouch.ui.transfercoin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.entity.AreaCode;
import com.hdf.autotouch.ui.areacode.AreaCodeActivity;
import com.hdf.autotouch.ui.collecttransferdetail.CollectTransferDetailActivity;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.ui.scan.ScanActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.EncryptUtils;
import com.hdf.autotouch.util.FormatUtils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 转币
 * </pre>
 */
public class TransferCoinActivity extends BaseActivity<TransferCoinPresenter> implements TransferCoinView {

    @BindView(R.id.tv_type)
    TextView         mTvType;
    @BindView(R.id.tv_area_code)
    TextView         mTvAreaCode;
    @BindView(R.id.view_divider)
    View             mViewDivider;
    @BindView(R.id.et_phone_number)
    EditText         mEtPhoneNumber;
    @BindView(R.id.et_email)
    EditText         mEtEmail;
    @BindView(R.id.layout_bg)
    ConstraintLayout mLayoutBg;
    @BindView(R.id.btn_next_step)
    QMUIRoundButton  mBtnNextStep;
    @BindView(R.id.tv_transfer_coin_alert)
    TextView         mTvTransferCoinAlert;
    @BindView(R.id.tv_account)
    TextView         mTvAccount;
    @BindView(R.id.tv_select_transfer_currency)
    TextView         mTvSelectTransferCurrency;
    @BindView(R.id.tv_value)
    TextView         mTvValue;
    @BindView(R.id.et_transfer_amount)
    EditText         mEtTransferAmount;
    @BindView(R.id.tv_transfer_currency)
    TextView         mTvTransferCurrency;
    @BindView(R.id.btn_next_step1)
    QMUIRoundButton  mBtnNextStep1;
    @BindView(R.id.layout_bg1)
    ConstraintLayout mLayoutBg1;
    @BindView(R.id.tv_collect_transfer_detail)
    TextView         mTvCollectTransferDetail;
    @BindView(R.id.img_saoyisao)
    ImageView        mImgSaoyisao;
    private String mAccount;
    private String mAreaCode;

    public static void start(Context context, String account) {
        Intent intent = new Intent(context, TransferCoinActivity.class);
        intent.putExtra("account", account);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mAccount = bundle.getString("account");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_transfer_coin;
    }

    @Override
    public void initView() {
        mPresenter = new TransferCoinPresenter(this);

        setTitleText(R.string.transfer_coin);

        applyClickListener(mTvType, mTvAreaCode, mBtnNextStep, mTvSelectTransferCurrency,
                mBtnNextStep1, mTvCollectTransferDetail, mImgSaoyisao);

        if (!StringUtils.isEmpty(mAccount)) {
            mImgSaoyisao.setVisibility(View.GONE);
            if (mAccount.contains(" ")) {
                mAreaCode = mAccount.split(" ")[0];
                mAccount = mAccount.split(" ")[1];
            } else {
                mAreaCode = "";
            }
            nextStep();
        }
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_type: {
                if (StringUtils.equals(mTvType.getText().toString(), getString(R.string.to_email_account))) {
                    mTvType.setText(R.string.to_phone_account);
                    mTvAreaCode.setVisibility(View.INVISIBLE);
                    mViewDivider.setVisibility(View.INVISIBLE);
                    mEtPhoneNumber.setVisibility(View.INVISIBLE);
                    mEtEmail.setVisibility(View.VISIBLE);
                    mEtEmail.requestFocus();
                } else {
                    mTvType.setText(R.string.to_email_account);
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
            case R.id.img_saoyisao: {
                ScanActivity.start(mActivity);
            }
            break;
            case R.id.btn_next_step: {
                if (StringUtils.equals(mTvType.getText().toString(), getString(R.string.to_email_account))) {
                    mAreaCode = mTvAreaCode.getText().toString();
                    mAccount = mEtPhoneNumber.getText().toString();
                } else {
                    mAreaCode = "";
                    mAccount = mEtEmail.getText().toString();
                }
                nextStep();
            }
            break;
            case R.id.tv_select_transfer_currency: {
                DialogHelper.showSelectCurrencyDialog(currency -> {
                    mTvSelectTransferCurrency.setText(currency);
                    mTvTransferCurrency.setText(currency);
                });
            }
            break;
            case R.id.btn_next_step1: {
                String currency = mTvSelectTransferCurrency.getText().toString();
                String amount = mEtTransferAmount.getText().toString();
                if (StringUtils.isEmpty(currency)) {
                    ToastUtils.showShort(mTvSelectTransferCurrency.getHint());
                } else if (StringUtils.isEmpty(amount)) {
                    ToastUtils.showShort(mEtTransferAmount.getHint());
                } else if (!RegexUtils.isMatch(Constant.REGEX_18, amount)) {
                    ToastUtils.showShort(R.string.withdraw_coin_amount_format_error);
                } else {
                    if (havePayPassword()) {
                        PayPasswordActivity.start(this, 0);
                    }
                }
            }
            break;
            case R.id.tv_collect_transfer_detail: {
                CollectTransferDetailActivity.start(this);
            }
            break;
            default:
                break;
        }
    }

    private void nextStep() {
        if (!StringUtils.isEmpty(mAreaCode)) {
            if (StringUtils.isSpace(mAccount)) {
                ToastUtils.showShort(R.string.input_opposite_account);
            } else if (StringUtils.equals("+86", mAreaCode) && !RegexUtils.isMatch(Constant.REGEX_MOBILE_EXACT, mAccount)) {
                ToastUtils.showShort(R.string.phone_number_format_error);
            } else {
                mTvAccount.setText(String.format(getString(R.string.account), mAreaCode + " " + FormatUtils.invisibleUsername(mAccount)));
                mLayoutBg.setVisibility(View.GONE);
                mBtnNextStep.setVisibility(View.GONE);
                mTvTransferCoinAlert.setVisibility(View.GONE);
                mLayoutBg1.setVisibility(View.VISIBLE);
                mTvAccount.setVisibility(View.VISIBLE);
                mBtnNextStep1.setVisibility(View.VISIBLE);
                mTvCollectTransferDetail.setVisibility(View.VISIBLE);
                mImgSaoyisao.setVisibility(View.GONE);
            }
        } else {
            if (StringUtils.isSpace(mAccount)) {
                ToastUtils.showShort(R.string.input_opposite_account);
            } else if (!RegexUtils.isEmail(mAccount)) {
                ToastUtils.showShort(R.string.email_format_error);
            } else {
                mTvAccount.setText(String.format(getString(R.string.account), FormatUtils.invisibleUsername(mAccount)));
                mLayoutBg.setVisibility(View.GONE);
                mBtnNextStep.setVisibility(View.GONE);
                mTvTransferCoinAlert.setVisibility(View.GONE);
                mLayoutBg1.setVisibility(View.VISIBLE);
                mTvAccount.setVisibility(View.VISIBLE);
                mBtnNextStep1.setVisibility(View.VISIBLE);
                mTvCollectTransferDetail.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_item_click: {
                AreaCode areaCode = (AreaCode) msg.obj;
                mTvAreaCode.setText(String.format(getString(R.string.plus), areaCode.getAreacode()));
            }
            break;
            case R.id.msg_input_pay_password: {
                String password = (String) msg.obj;
                mPresenter.transferCoin(mAreaCode,
                        mAccount,
                        mTvSelectTransferCurrency.getText().toString(),
                        mEtTransferAmount.getText().toString(),
                        EncryptUtils.encryptSha256(password));
            }
            break;
            case R.id.msg_scan_success:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void transferCoin() {
        //收款成功 跳转收支明细
        ToastUtils.showShort(StringUtils.getString(R.string.transfer_coin_success));
        CollectTransferDetailActivity.start(this);
        finish();
    }
}
