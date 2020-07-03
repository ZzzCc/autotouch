package com.hdf.autotouch.ui.macaddress;

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
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.MacAaddressAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.MyMill;
import com.hdf.autotouch.util.ClipboardUtils;
import com.hdf.autotouch.util.DialogHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : Mac地址
 * </pre>
 */
public class MacAddressActivity extends BaseActivity<MacAddressPresenter> implements MacAddressView {

    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_one_bind)
    TextView           mTvOneBind;
    @BindView(R.id.tab_segment)
    QMUITabSegment     mTabSegment;
    private MacAaddressAdapter mAdapter;
    private int                mPage   = 1;
    private int                isJudge = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, MacAddressActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mac_address;
    }

    @Override
    public void initView() {
        mPresenter = new MacAddressPresenter(this);
        setTitleText(R.string.ant_mac_address);

        mTabSegment.setDefaultNormalColor(getResources().getColor(R.color.light_black3));
        mTabSegment.setDefaultSelectedColor(getResources().getColor(R.color.light_black1));
        QMUITabSegment.Tab tab0 = new QMUITabSegment.Tab(null, null,
                getString(R.string.not_bound), false, false);
        QMUITabSegment.Tab tab1 = new QMUITabSegment.Tab(null, null,
                getString(R.string.order_status3), false, false);
        mTabSegment.addTab(tab0).addTab(tab1);
        mTabSegment.setPadding(0, 0, 0, 20);
        mTabSegment.selectTab(0);
        mTabSegment.setHasIndicator(true);  //是否需要显示indicator
        mTabSegment.setIndicatorPosition(false);//true 时表示 indicator 位置在 Tab 的上方, false 时表示在下方
        mTabSegment.setIndicatorWidthAdjustContent(true);//设置 indicator的宽度是否随内容宽度变化
        mTabSegment.setOnTabClickListener(new QMUITabSegment.OnTabClickListener() {
            @Override
            public void onTabClick(int index) {
                if (index == 0) {
                    isJudge = 0;
                    mTvOneBind.setVisibility(View.VISIBLE);
                } else if (index == 1) {
                    isJudge = 1;
                    mTvOneBind.setVisibility(View.GONE);
                }
                getData();
            }
        });


        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage = 1;
            getData();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage += 1;
            getData();
        });
        applyClickListener(mTvOneBind);
        mAdapter = new MacAaddressAdapter(new ArrayList<>());
        mAdapter.setHeaderView(new View(mActivity));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter.setOnItemClickListener((view, position) -> ClipboardUtils.copyText(mAdapter.getData().get(position).getMac()));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_one_bind: {
                DialogHelper.showNoeBinndDialog(getString(R.string.one_binding_context), new DialogHelper.OnLockedListener() {
                    @Override
                    public void onLocked() {
                        mPresenter.getAllBinding("");
                    }
                });
            }
            break;
            default:
                break;
        }
    }

    private void getData() {
        mPresenter.getMill(mPage, isJudge);
    }

    private void setData(List<MyMill> list) {
        List<MyMill> data = mAdapter.getData();
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
    public void getMill(List<MyMill> millList) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        setData(millList);
    }

    @Override
    public void getAllBinding(Integer num) {
        ToastUtils.showShort(String.format(getString(R.string.successful_binding), num + ""));
        mPage = 1;
        getData();

    }


}
