package com.hdf.autotouch.ui.transactiondetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Transaction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 交易详情
 * </pre>
 */
public class TransactionDetailActivity extends BaseActivity<TransactionDetailPresenter> implements TransactionDetailView {

    @BindView(R.id.iv_close)
    ImageView       mIvClose;
    @BindView(R.id.tv_collection_address_value)
    TextView        mTvCollectionAddressValue;
    @BindView(R.id.tv_withdraw_coin_sum)
    TextView        mTvWithdrawCoinSum;
    @BindView(R.id.tv_withdraw_coin_sum_value)
    TextView        mTvWithdrawCoinSumValue;
    @BindView(R.id.tv_withdraw_coin_status)
    TextView        mTvWithdrawCoinStatus;
    @BindView(R.id.tv_withdraw_coin_status_value)
    TextView        mTvWithdrawCoinStatusValue;
    @BindView(R.id.btn_revocation)
    QMUIRoundButton mBtnRevocation;
    private Transaction mTransaction;

    public static void start(Context context, Transaction transaction) {
        Intent intent = new Intent(context, TransactionDetailActivity.class);
        intent.putExtra("transaction", transaction);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mTransaction = bundle.getParcelable("transaction");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_transaction_detail;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.AnimationFade;
        getWindow().setAttributes(params);

        mPresenter = new TransactionDetailPresenter(this);

        applyClickListener(mIvClose, mBtnRevocation);

        mTvCollectionAddressValue.setText(mTransaction.getTxTo());

        if (mTransaction.getType() == 5) {
            mTvWithdrawCoinSum.setText(R.string.withdraw_coin_sum);
            mTvWithdrawCoinStatus.setText(R.string.withdraw_coin_status);
        } else if (mTransaction.getType() == 6) {
            mTvWithdrawCoinSum.setText(R.string.charge_coin_sum);
            mTvWithdrawCoinStatus.setText(R.string.charge_coin_status);
        }

        mTvWithdrawCoinSumValue.setText(mTransaction.getAmount() + mTransaction.getCoin());

        if (mTransaction.getStatus() == 0) {
            mTvWithdrawCoinStatusValue.setText(R.string.in_review);
            mTvWithdrawCoinStatusValue.setTextColor(ColorUtils.getColor(R.color.yellow));
            mBtnRevocation.setVisibility(View.VISIBLE);
        } else if (mTransaction.getStatus() == 1) {
            mTvWithdrawCoinStatusValue.setText(R.string.success);
            mTvWithdrawCoinStatusValue.setTextColor(ColorUtils.getColor(R.color.green));
            mBtnRevocation.setVisibility(View.GONE);
        } else if (mTransaction.getStatus() == -1) {
            mTvWithdrawCoinStatusValue.setText(R.string.fail);
            mTvWithdrawCoinStatusValue.setTextColor(ColorUtils.getColor(R.color.red));
            mBtnRevocation.setVisibility(View.GONE);
        } else if (mTransaction.getStatus() == -2) {
            mTvWithdrawCoinStatusValue.setText(R.string.reject);
            mTvWithdrawCoinStatusValue.setTextColor(ColorUtils.getColor(R.color.red));
            mBtnRevocation.setVisibility(View.GONE);
        }
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.iv_close: {
                finish();
            }
            break;
            case R.id.btn_revocation: {
                mPresenter.revocation(mTransaction.getTxId());
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void revocation() {
        finish();
        sendMessage(R.id.msg_revocation, null);
    }
}
