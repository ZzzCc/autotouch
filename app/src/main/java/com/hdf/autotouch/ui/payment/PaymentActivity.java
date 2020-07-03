package com.hdf.autotouch.ui.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.ui.orderdetail.OrderDetailActivity;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.ui.web.WebActivity;
import com.hdf.autotouch.util.EncryptUtils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

public class PaymentActivity extends BaseActivity<PaymentPresenter> implements PaymentView {

    @BindView(R.id.tv_order_number_value)
    TextView        mTvOrderNumberValue;
    @BindView(R.id.tv_payment_method_value)
    TextView        mTvPaymentMethodValue;
    @BindView(R.id.tv_total_value)
    TextView        mTvTotalValue;
    @BindView(R.id.tv_escrow_agreement)
    TextView        mTvEscrowAgreement;
    @BindView(R.id.btn_pay_now)
    QMUIRoundButton mBtnPayNow;
    private Order mOrder;

    public static void start(Context context, Order order) {
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra("order", order);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mOrder = bundle.getParcelable("order");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_payment;
    }

    @Override
    public void initView() {
        mPresenter = new PaymentPresenter(this);

        setTitleText(R.string.payment);

        applyClickListener(mTvEscrowAgreement, mBtnPayNow);

        mTvOrderNumberValue.setText(mOrder.getOrderId());
        mTvPaymentMethodValue.setText(String.format(getString(R.string.balance), mOrder.getBalance()));
        mTvTotalValue.setText(String.format(getString(R.string.usdt3), mOrder.getAmount()));
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_escrow_agreement: {
                WebActivity.start(this, Constant.URL_ESCROW_AGREEMENT);
            }
            break;
            case R.id.btn_pay_now: {
                if (havePayPassword()) {
                    PayPasswordActivity.start(this, 0);
                }
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
            mPresenter.payOrder(mOrder.getOrderId(), EncryptUtils.encryptSha256(password));
        }
    }

    @Override
    public void payOrder(Order order) {
        OrderDetailActivity.start(this, order);
        finish();
    }

    @Override
    public void payOrderError() {
        finish();
    }
}
