package com.hdf.autotouch.ui.myassets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.TransactionAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.MyAssets;
import com.hdf.autotouch.entity.Transaction;
import com.hdf.autotouch.ui.chargecoin.ChargeCoinActivity;
import com.hdf.autotouch.ui.transactiondetail.TransactionDetailActivity;
import com.hdf.autotouch.ui.withdrawcoin.WithdrawCoinActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 我的资产
 * </pre>
 */
public class MyAssetsActivity extends BaseActivity<MyAssetsPresenter> implements MyAssetsView {

    @BindView(R.id.tv_usdt_value)
    TextView           mTvUsdtValue;
    @BindView(R.id.tv_charge_coin)
    TextView           mTvChargeCoin;
    @BindView(R.id.tv_withdraw_coin)
    TextView           mTvWithdrawCoin;
    @BindView(R.id.tv_available_value)
    TextView           mTvAvailableValue;
    @BindView(R.id.tv_freeze_value)
    TextView           mTvFreezeValue;
    @BindView(R.id.tv_available1_value)
    TextView           mTvAvailable1Value;
    @BindView(R.id.tv_freeze1_value)
    TextView           mTvFreeze1Value;
    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private TransactionAdapter mAdapter;
    private int                mPage = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyAssetsActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_assets;
    }

    @Override
    public void initView() {
        mPresenter = new MyAssetsPresenter(this);

        setTitleText(R.string.my_assets);
        applyLightMode(false);
        applyClickListener(mTvChargeCoin, mTvWithdrawCoin);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mRefreshLayout.finishRefresh();
            mPage = 1;
            getData();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mRefreshLayout.finishLoadMore();
            mPage += 1;
            getData();
        });

        mAdapter = new TransactionAdapter(new ArrayList<>());
        mAdapter.setHeaderView(new View(mActivity));
        mAdapter.setOnItemClickListener((view, position) -> TransactionDetailActivity.start(this, mAdapter.getData().get(position)));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mActivity, R.drawable.divider8)));
        mRecyclerView.addItemDecoration(divider);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void doBusiness() {
        getData();
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_charge_coin: {
                ChargeCoinActivity.start(this);
            }
            break;
            case R.id.tv_withdraw_coin: {
                WithdrawCoinActivity.start(this);
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void getTransaction(MyAssets myAssets) {
        if (!ObjectUtils.isEmpty(myAssets)) {
            mTvUsdtValue.setText(myAssets.getUsdtValue());
            mTvAvailableValue.setText(myAssets.getFile());
            mTvFreezeValue.setText(myAssets.getFileFreeze());
            mTvAvailable1Value.setText(myAssets.getUsdt());
            mTvFreeze1Value.setText(myAssets.getUsdtFreeze());
            setData(myAssets.getList());
        }
    }

    private void getData() {
        mPresenter.getTransaction(mPage);
    }

    private void setData(List<Transaction> list) {
        List<Transaction> data = mAdapter.getData();
        if (mPage == 1) {
            data = list;
            mRecyclerView.scrollToPosition(0);
        } else {
            if (ObjectUtils.isEmpty(list)) {
                mPage -= 1;
            } else {
                data.addAll(list);
            }
        }
        mAdapter.update(data);
        if (ObjectUtils.isEmpty(data)) {
            mTvNoData.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mTvNoData.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_revocation:
            case R.id.msg_assets:
                getData();
                break;
            default:
                break;
        }
    }
}
