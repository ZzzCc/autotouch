package com.hdf.autotouch.ui.exchange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.entity.Exchange;
import com.hdf.autotouch.entity.ExchangeRate;
import com.hdf.autotouch.ui.exchangerecord.ExchangeDetailActivity;
import com.hdf.autotouch.ui.exchangerecord.ExchangeRecordActivity;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.EncryptUtils;
import com.hdf.autotouch.util.FormatUtils;
import com.hdf.autotouch.widget.DecimalDigitsInputFilter;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import butterknife.BindView;

public class ExchangeActivity extends BaseActivity<ExchangePresenter> implements ExchangeView {

    @BindView(R.id.iv_coin_top)
    ImageView       mIvCoinTop;
    @BindView(R.id.tv_coin_top)
    TextView        mTvCoinTop;
    @BindView(R.id.et_transfer_number)
    EditText        mEtTransferNumber;
    @BindView(R.id.iv_coin_bottom)
    ImageView       mIvCoinBottom;
    @BindView(R.id.tv_coin_bottom)
    TextView        mTvCoinBottom;
    @BindView(R.id.et_receive_number)
    EditText        mEtReceiveNumber;
    @BindView(R.id.iv_exchange)
    ImageView       mIvExchange;
    @BindView(R.id.tv_exchange_ratio)
    TextView        mTvExchangeRatio;
    @BindView(R.id.tv_service_charge)
    TextView        mTvServiceCharge;
    @BindView(R.id.btn_exchange)
    QMUIRoundButton mBtnExchange;
    private ExchangeRate  mExchangeRate;
    private DecimalFormat mDecimalFormat = new DecimalFormat("0.0000");

    public static void start(Context context) {
        Intent intent = new Intent(context, ExchangeActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_exchange;
    }

    @Override
    public void initView() {
        mPresenter = new ExchangePresenter(this);

        setTitleText(R.string.exchange);
        applyClickListener(mIvExchange, mBtnExchange);

        mEtTransferNumber.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4)});
        mEtReceiveNumber.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4)});

        mEtTransferNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtTransferNumber.hasFocus() && !ObjectUtils.isEmpty(mExchangeRate)) {
                    if (!StringUtils.isEmpty(s)) {
                        if (RegexUtils.isMatch(Constant.REGEX_4, s.toString())) {
                            BigDecimal receive = new BigDecimal(s.toString())
                                    .multiply(new BigDecimal(mExchangeRate.getExchangeRate()));
                            mEtReceiveNumber.setText(FormatUtils.formatEndZero(mDecimalFormat.format(Double.valueOf(receive.toPlainString()))));
                            double fee = receive.multiply(new BigDecimal(mExchangeRate.getFeeRate())
                                    .setScale(4, RoundingMode.HALF_UP))
                                    .doubleValue();
                            mTvServiceCharge.setText(String.format(getString(R.string.service_charge1),
                                    FormatUtils.formatEndZero(mDecimalFormat.format(fee)) + mTvCoinBottom.getText().toString()));
                            mBtnExchange.setEnabled(true);
                        } else {
                            mBtnExchange.setEnabled(false);
                        }
                    } else {
                        mEtReceiveNumber.setText("");
                        mTvServiceCharge.setText(String.format(getString(R.string.service_charge1),
                                getString(R.string.zero) + mTvCoinBottom.getText().toString()));
                        mBtnExchange.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEtReceiveNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtReceiveNumber.hasFocus() && !ObjectUtils.isEmpty(mExchangeRate)) {
                    if (!StringUtils.isEmpty(s)) {
                        if (RegexUtils.isMatch(Constant.REGEX_4, s.toString())) {
                            BigDecimal transfer = new BigDecimal(s.toString())
                                    .divide(new BigDecimal(mExchangeRate.getExchangeRate()), 4, RoundingMode.HALF_UP);
                            mEtTransferNumber.setText(FormatUtils.formatEndZero(mDecimalFormat.format(Double.valueOf(transfer.toPlainString()))));
                            double fee = new BigDecimal(s.toString()).multiply(new BigDecimal(mExchangeRate.getFeeRate())
                                    .setScale(4, RoundingMode.HALF_UP))
                                    .doubleValue();
                            mTvServiceCharge.setText(String.format(getString(R.string.service_charge1),
                                    FormatUtils.formatEndZero(mDecimalFormat.format(fee)) + mTvCoinBottom.getText().toString()));
                            mBtnExchange.setEnabled(true);
                        } else {
                            mBtnExchange.setEnabled(false);
                        }
                    } else {
                        mEtTransferNumber.setText("");
                        mTvServiceCharge.setText(String.format(getString(R.string.service_charge1),
                                getString(R.string.zero) + mTvCoinBottom.getText().toString()));
                        mBtnExchange.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mPresenter.getExchangeRate(mTvCoinTop.getText().toString(), mTvCoinBottom.getText().toString());
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.iv_exchange: {
                String top = mTvCoinTop.getText().toString();
                if (StringUtils.equals(top, getString(R.string.gtse))) {
                    mIvCoinTop.setImageResource(R.drawable.ic_usdt_e);
                    mTvCoinTop.setText(R.string.usdt);
                    mIvCoinBottom.setImageResource(R.drawable.ic_gtse_e);
                    mTvCoinBottom.setText(R.string.gtse);
                } else {
                    mIvCoinTop.setImageResource(R.drawable.ic_gtse_e);
                    mTvCoinTop.setText(R.string.gtse);
                    mIvCoinBottom.setImageResource(R.drawable.ic_usdt_e);
                    mTvCoinBottom.setText(R.string.usdt);
                }
                mEtTransferNumber.requestFocus();
                mEtTransferNumber.setSelection(mEtTransferNumber.getText().length());
                mEtReceiveNumber.setText("");
                mPresenter.getExchangeRate(mTvCoinTop.getText().toString(), mTvCoinBottom.getText().toString());
            }
            break;
            case R.id.btn_exchange: {
                DialogHelper.showExchangeConfirmDialog(mEtTransferNumber.getText().toString() + mTvCoinTop.getText().toString(),
                        mEtReceiveNumber.getText().toString() + mTvCoinBottom.getText().toString(),
                        mTvServiceCharge.getText().toString().replace(getString(R.string.service_charge2), "").trim(),
                        () -> {
                            if (havePayPassword()) {
                                PayPasswordActivity.start(this, 0);
                            }
                        });
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        if (msg.what == R.id.msg_input_pay_password) {
            String password = (String) msg.obj;
            mPresenter.exchange(mTvCoinTop.getText().toString(),
                    mTvCoinBottom.getText().toString(),
                    mEtTransferNumber.getText().toString(),
                    EncryptUtils.encryptSha256(password));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        MenuItem menuSave = menu.findItem(R.id.menu_exchange_record);
        menuSave.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_exchange_record) {
            ExchangeRecordActivity.start(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getExchangeRate(ExchangeRate exchangeRate) {
        mExchangeRate = exchangeRate;
        mTvExchangeRatio.setText(String.format(getString(R.string.exchange_ratio),
                1 + mTvCoinTop.getText().toString(),
                exchangeRate.getExchangeRate() + mTvCoinBottom.getText().toString()));
        String number = mEtTransferNumber.getText().toString();
        if (RegexUtils.isMatch(Constant.REGEX_4, number)) {
            BigDecimal receive = new BigDecimal(number)
                    .multiply(new BigDecimal(exchangeRate.getExchangeRate()));
            mEtReceiveNumber.setText(FormatUtils.formatEndZero(mDecimalFormat.format(Double.valueOf(receive.toPlainString()))));
            double fee = receive.multiply(new BigDecimal(exchangeRate.getFeeRate())
                    .setScale(4, RoundingMode.HALF_UP))
                    .doubleValue();
            mTvServiceCharge.setText(String.format(getString(R.string.service_charge1),
                    FormatUtils.formatEndZero(mDecimalFormat.format(fee)) + mTvCoinBottom.getText().toString()));
        } else {
            mTvServiceCharge.setText(String.format(getString(R.string.service_charge1),
                    getString(R.string.zero) + mTvCoinBottom.getText().toString()));
        }
    }

    @Override
    public void exchange(Exchange exchange) {
        mEtTransferNumber.requestFocus();
        mEtTransferNumber.setText("");
        ToastUtils.showShort(R.string.exchange_success);
        ExchangeDetailActivity.start(this, exchange);
    }
}
