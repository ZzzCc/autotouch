package com.hdf.autotouch.ui.home.fragment;

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

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.TransactionAdapter;
import com.hdf.autotouch.base.BaseFragment;
import com.hdf.autotouch.entity.MyAssets;
import com.hdf.autotouch.entity.Transaction;
import com.hdf.autotouch.ui.chargecoin.ChargeCoinActivity;
import com.hdf.autotouch.ui.home.HomeActivity;
import com.hdf.autotouch.ui.transactiondetail.TransactionDetailActivity;
import com.hdf.autotouch.ui.withdrawcoin.WithdrawCoinActivity;
import com.hdf.autotouch.util.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/12
 *     desc  : 矿石
 * </pre>
 */
public class Home2Fragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView           mTvTitle;
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

    public static Home2Fragment newInstance() {
        Bundle args = new Bundle();
        Home2Fragment fragment = new Home2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home2;
    }

    @Override
    public void initView() {
        BarUtils.addMarginTopEqualStatusBarHeight(mTvTitle);

        applyClickListener(mTvChargeCoin, mTvWithdrawCoin);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            if (!StringUtils.isEmpty(SPManager.getId())) {
                mPage = 1;
                getData();
            } else {
                mRefreshLayout.finishRefresh();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (!StringUtils.isEmpty(SPManager.getId())) {
                mPage += 1;
                getData();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        });

        mAdapter = new TransactionAdapter(new ArrayList<>());
        mAdapter.setHeaderView(new View(mActivity));
        mAdapter.setOnItemClickListener((view, position) -> TransactionDetailActivity.start(mActivity, mAdapter.getData().get(position)));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mActivity, R.drawable.divider8)));
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void doBusiness() {
        if (!StringUtils.isEmpty(SPManager.getId())) {
            mTvNoData.setVisibility(View.GONE);
            mRefreshLayout.autoRefresh();
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_charge_coin: {
                if (isLogin()) {
                    ChargeCoinActivity.start(mActivity);
                }
            }
            break;
            case R.id.tv_withdraw_coin: {
                if (isLogin()) {
                    WithdrawCoinActivity.start(mActivity);
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
        switch (msg.what) {
            case R.id.msg_my_assets:
                MyAssets myAssets = (MyAssets) msg.obj;
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (!ObjectUtils.isEmpty(myAssets)) {
                    mTvUsdtValue.setText(myAssets.getUsdtValue());
                    mTvAvailableValue.setText(myAssets.getFile());
                    mTvFreezeValue.setText(myAssets.getFileFreeze());
                    mTvAvailable1Value.setText(myAssets.getUsdt());
                    mTvFreeze1Value.setText(myAssets.getUsdtFreeze());
                    setData(myAssets.getList());
                }
                break;
            default:
                break;
        }
    }

    private void getData() {
        if (!StringUtils.isEmpty(SPManager.getId())) {
            ((HomeActivity) mActivity).mPresenter.getTransaction(mPage);
        }
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
}
