package com.hdf.autotouch.ui.address;

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
import com.hdf.autotouch.adapter.AddressAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Address;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class AddressActivity extends BaseActivity<AddressPresenter> implements AddressView {

    @BindView(R.id.tv_add_new_address)
    TextView    mTvAddNewAddress;
    @BindView(R.id.btn_add_new_address)
    QMUIRoundButton    mBtnAddNewAddress;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    /**
     * 0:normal;1:select
     */
    private int            mMode;
    private AddressAdapter mAdapter;
    private int            mPage = 1;

    public static void start(Context context, int mode) {
        Intent intent = new Intent(context, AddressActivity.class);
        intent.putExtra("mode", mode);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mMode = bundle.getInt("mode");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_address;
    }

    @Override
    public void initView() {
        mPresenter = new AddressPresenter(this);

        setTitleText(R.string.shipping_address1);

        applyClickListener(mBtnAddNewAddress, mTvAddNewAddress);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage = 1;
            getData();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage += 1;
            getData();
        });

        mAdapter = new AddressAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener((view, position) -> {
            if (mMode == 1) {
                sendMessage(R.id.msg_select_address, mAdapter.getData().get(position));
                finish();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mActivity, R.drawable.divider8)));
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void doBusiness() {
        getData();
    }

    @Override
    public void onViewClick(@NonNull View view) {
        if (view.getId() == R.id.btn_add_new_address) {
            AddAddressActivity.start(this, null);
        } else if (view.getId() == R.id.tv_add_new_address) {
            AddAddressActivity.start(this, null);
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_delete_address: {
                mPresenter.deleteAddress(((Address) msg.obj).getAId());
            }
            break;
            case R.id.msg_update_address: {
                mPage = 1;
                getData();
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void getAddress(List<Address> list) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        setData(list);
    }

    @Override
    public void updateAddress() {
        mPage = 1;
        getData();
    }

    private void getData() {
        mPresenter.getAddress(mPage);
    }

    private void setData(List<Address> list) {
        List<Address> data = mAdapter.getData();
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
            mBtnAddNewAddress.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mTvAddNewAddress.setVisibility(View.GONE);
        } else {
            mBtnAddNewAddress.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mTvAddNewAddress.setVisibility(View.VISIBLE);
        }
    }
}
