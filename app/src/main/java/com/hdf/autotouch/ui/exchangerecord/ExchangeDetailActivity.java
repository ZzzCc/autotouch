package com.hdf.autotouch.ui.exchangerecord;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Exchange;

import butterknife.BindView;

public class ExchangeDetailActivity extends BaseActivity {

    @BindView(R.id.tv_from)
    TextView mTvFrom;
    @BindView(R.id.tv_to)
    TextView mTvTo;
    @BindView(R.id.tv_from_value)
    TextView mTvFromValue;
    @BindView(R.id.tv_to_value)
    TextView mTvToValue;
    @BindView(R.id.tv_exchange_miner_fee_value)
    TextView mTvExchangeMinerFeeValue;
    @BindView(R.id.tv_exchange_time_value)
    TextView mTvExchangeTimeValue;
    private Exchange mExchange;

    public static void start(Context context, Exchange exchange) {
        Intent intent = new Intent(context, ExchangeDetailActivity.class);
        intent.putExtra("exchange", exchange);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mExchange = bundle.getParcelable("exchange");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_exchange_detail;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        setTitleText(R.string.exchange);

        mTvFrom.setText(mExchange.getInCoin());
        mTvFromValue.setText(mExchange.getInAmount());
        mTvTo.setText(mExchange.getOutCoin());
        mTvToValue.setText(mExchange.getOutAmount());
        mTvExchangeMinerFeeValue.setText(mExchange.getFee() + mExchange.getOutCoin());
        mTvExchangeTimeValue.setText(TimeUtils.millis2String(mExchange.getTimeStamp()));
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }
}
