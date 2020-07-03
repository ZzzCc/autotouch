package com.hdf.autotouch.ui.myserve;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.MyServeAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.MyMac;
import com.hdf.autotouch.util.EncryptUtils;
import com.hdf.autotouch.util.FormatUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 我的服务器
 * </pre>
 */
public class MyServeActivity extends BaseActivity<MyServePresenter> implements MyServeView {

    @BindView(R.id.tv_how_many)
    TextView           mTvHowMany;
    @BindView(R.id.tv_new_identity)
    TextView           mTvNewIdentity;
    @BindView(R.id.tv_serve_rule)
    TextView           mTvServeRule;
    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private MyServeAdapter mAdapter;
    private int            mPage = 1;
    private String         mMac_address;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyServeActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_serve;
    }

    @Override
    public void initView() {
        mPresenter = new MyServePresenter(this);
        mViewBgTopBar.setBackgroundColor(Color.TRANSPARENT);
        setTitleText(R.string.my_serve);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage = 1;
            getData();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage += 1;
            getData();
        });

        mAdapter = new MyServeAdapter(new ArrayList<>());
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
        mPresenter.getmyMac(mPage);
    }

    private void setData(List<MyMac.MacModelsBean> list) {
        List<MyMac.MacModelsBean> data = mAdapter.getData();
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
    public void getmyMac(MyMac myMac) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mTvHowMany.setText(myMac.getMacAccount() + "台");
        mTvNewIdentity.setText("当前身份:  " + FormatUtils.getLevelType(myMac.getLevel() + ""));
        setData(myMac.getMacModels());
    }

    @Override
    public void getDeductMac() {
        ToastUtils.showShort("支付成功");
        mPage = 1;
        getData();
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_input_pay_password:
                String password = (String) msg.obj;
                if (!StringUtils.isEmpty(mMac_address)) {
                    mPresenter.getDeductMac(mMac_address, EncryptUtils.encryptSha256(password));
                }
                break;
            case R.id.msg_mac_address:
                mMac_address = (String) msg.obj;
                break;
            default:
                break;
        }
    }
}
