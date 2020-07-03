package com.hdf.autotouch.ui.exchangerecord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.hdf.autotouch.adapter.ExchangeAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Exchange;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class ExchangeRecordActivity extends BaseActivity<ExchangeRecordPresenter> implements ExchangeRecordView {

    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private ExchangeAdapter mAdapter;
    private int             mPage = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExchangeRecordActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_exchange_record;
    }

    @Override
    public void initView() {
        mPresenter = new ExchangeRecordPresenter(this);

        setTitleText(R.string.exchange_record);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage = 1;
            getData();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage += 1;
            getData();
        });

        mAdapter = new ExchangeAdapter(new ArrayList<>());
        mAdapter.setHeaderView(new View(this));
        mAdapter.setOnItemClickListener((view, position) -> ExchangeDetailActivity.start(this, mAdapter.getData().get(position)));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider4)));
        mRecyclerView.addItemDecoration(divider);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @Override
    public void getExchangeRecord(List<Exchange> list) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        setData(list);
    }

    private void getData() {
        mPresenter.getExchangeRecord(mPage);
    }

    private void setData(List<Exchange> list) {
        List<Exchange> data = mAdapter.getData();
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
