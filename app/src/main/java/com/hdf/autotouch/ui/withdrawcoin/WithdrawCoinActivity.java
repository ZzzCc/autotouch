package com.hdf.autotouch.ui.withdrawcoin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.entity.WithdrawAddress;
import com.hdf.autotouch.ui.bindaddress.BindAddressActivity;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.util.EncryptUtils;
import com.hdf.autotouch.widget.DecimalDigitsInputFilter;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 提币
 * </pre>
 */
public class WithdrawCoinActivity extends BaseActivity<WithdrawCoinPresenter> implements WithdrawCoinView {

    @BindView(R.id.tv_bind_address)
    TextView        mTvBindAddress;
    @BindView(R.id.tv_unbind_address)
    TextView        mTvUnbindAddress;
    @BindView(R.id.tv_current_balance)
    TextView        mTvCurrentBalance;
    @BindView(R.id.et_wallet_address)
    EditText        mEtWalletAddress;
    @BindView(R.id.et_withdraw_coin_amount)
    EditText        mEtWithdrawCoinAmount;
    @BindView(R.id.tv_service_charge)
    TextView        mTvServiceCharge;
    @BindView(R.id.et_remark)
    EditText        mEtRemark;
    @BindView(R.id.btn_submit)
    QMUIRoundButton mBtnSubmit;
    private String mBalance = "0";
    private String mAddress = "";
    private String mFee;

    public static void start(Context context) {
        Intent intent = new Intent(context, WithdrawCoinActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_withdraw_coin;
    }

    @Override
    public void initView() {
        mPresenter = new WithdrawCoinPresenter(this);

        setTitleText(R.string.withdraw_coin);

        applyClickListener(mTvBindAddress, mTvUnbindAddress, mBtnSubmit);

        mEtWithdrawCoinAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4)});
        mEtWithdrawCoinAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtWithdrawCoinAmount.hasFocus()) {
                    if (!StringUtils.isEmpty(s)) {
                        if (RegexUtils.isMatch(Constant.REGEX_4, s.toString())) {

                            String receive = new BigDecimal(s.toString())
                                    .multiply(new BigDecimal(mFee)
                                            .setScale(4, RoundingMode.HALF_UP)).toPlainString();
                            mTvServiceCharge.setText(String.format(getString(R.string.service_charge), receive));
                        }
                    } else {
                        mTvServiceCharge.setText(String.format(getString(R.string.service_charge), "0.0000"));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void doBusiness() {
        mPresenter.getBindingAddress();
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_submit: {
                String address = mEtWalletAddress.getText().toString();
                String amount = mEtWithdrawCoinAmount.getText().toString();
                if (StringUtils.isEmpty(address)) {
                    ToastUtils.showShort(mEtWalletAddress.getHint());
                } else if (!isEthAddress(address)) {
                    ToastUtils.showShort(R.string.wallet_address_format_error);
                } else if (StringUtils.isEmpty(amount)) {
                    ToastUtils.showShort(mEtWithdrawCoinAmount.getHint());
                } else if (!RegexUtils.isMatch(Constant.REGEX_18, amount)) {
                    ToastUtils.showShort(R.string.withdraw_coin_amount_format_error);
                } else if (Double.valueOf(mBalance) < Double.valueOf(amount)) {
                    ToastUtils.showShort(R.string.insufficient_current_balance);
                } else {
                    if (havePayPassword()) {
                        PayPasswordActivity.start(this, 0);
                    }
                }
            }
            break;
            case R.id.tv_bind_address:
            case R.id.tv_unbind_address: {
                BindAddressActivity.start(this, mAddress);
            }
            break;
            default:
                break;
        }
    }

    private boolean isEthAddress(String address) {
        return address.startsWith("0x") && address.length() == 42;
    }

    @Override
    public void getBindingAddress(WithdrawAddress withdrawAddress) {
        mAddress = withdrawAddress.getAddress();
        mBalance = withdrawAddress.getUsdtBalance();
        mFee = withdrawAddress.getFee();
        mTvCurrentBalance.setText(String.format(getString(R.string.current_balance), mBalance));
        mTvServiceCharge.setText(String.format(getString(R.string.service_charge), "0.0000"));
        updateView();
    }

    @Override
    public void getWithdrawCoin() {
        mPresenter.showSuccess(StringUtils.getString(R.string.submit_success));
        sendMessage(R.id.msg_assets,null);
        finish();
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_bind_address: {
                mAddress = (String) msg.obj;
                updateView();
            }
            break;
            case R.id.msg_input_pay_password: {
                String password = (String) msg.obj;
                mPresenter.withdrawCoin(mEtWalletAddress.getText().toString(),
                        mEtWithdrawCoinAmount.getText().toString(),
                        mEtRemark.getText().toString(),
                        EncryptUtils.encryptSha256(password));
            }
            break;
            default:
                break;
        }
    }

    private void updateView() {
        if (StringUtils.isEmpty(mAddress)) {
            mTvBindAddress.setVisibility(View.VISIBLE);
            mTvUnbindAddress.setVisibility(View.GONE);
            mEtWalletAddress.setText("");
            mEtWalletAddress.setFocusable(true);
            mEtWalletAddress.setEnabled(true);
        } else {
            mTvBindAddress.setVisibility(View.GONE);
            mTvUnbindAddress.setVisibility(View.VISIBLE);
            mEtWalletAddress.setText(mAddress);
            mEtWalletAddress.setFocusable(false);
            mEtWalletAddress.setEnabled(false);
        }
    }
}
