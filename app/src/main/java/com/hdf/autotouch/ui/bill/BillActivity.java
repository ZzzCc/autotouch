package com.hdf.autotouch.ui.bill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.BillAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.MyBill;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 账单
 * </pre>
 */
public class BillActivity extends BaseActivity<BillPresenter> implements BillView {

    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private BillAdapter mAdapter;
    private int         mPage = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, BillActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bill;
    }

    @Override
    public void initView() {
        mPresenter = new BillPresenter(this);
        setTitleText(R.string.bill);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage = 1;
            getData();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage += 1;
            getData();
        });

        mAdapter = new BillAdapter(new ArrayList<>());
        mAdapter.setHeaderView(new View(mActivity));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    private void getData() {
        mPresenter.getBill(mPage);
    }

    private void setData(List<MyBill> list) {
        List<MyBill> data = mAdapter.getData();
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
    public void getBill(List<MyBill> billList) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        setData(billList);
    }
}
