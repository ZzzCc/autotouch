package com.hdf.autotouch.ui.minepool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.TeamAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.MinePool;
import com.hdf.autotouch.ui.minefield.MineFieldActivity;
import com.hdf.autotouch.util.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MinePoolActivity extends BaseActivity<MinePoolPresenter> implements MinePoolView {

    @BindView(R.id.img_field)
    ImageView          mImgField;
    @BindView(R.id.tv_fliecoin_num)
    TextView           mTvFliecoinNum;
    @BindView(R.id.tv_gtse_num)
    TextView           mTvGtseNum;
    @BindView(R.id.tv_fliecoin_time)
    TextView           mTvFliecoinTime;
    @BindView(R.id.tv_gtse_time)
    TextView           mTvGtseTime;
    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private TeamAdapter mAdapter;
    private int         mPage = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, MinePoolActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine_pool;
    }

    @Override
    public void initView() {
        mPresenter = new MinePoolPresenter(this);
        applyLightMode(false);
        setTitleText(R.string.mine_pool);
        applyClickListener(mImgField);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage = 1;
            if (!StringUtils.isEmpty(SPManager.getId())) {
                getData();
                mTvNoData.setVisibility(View.GONE);
            } else {
                mTvNoData.setVisibility(View.VISIBLE);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (!StringUtils.isEmpty(SPManager.getId())) {
                mPage += 1;
                getData();
                mTvNoData.setVisibility(View.GONE);
            } else {
                mTvNoData.setVisibility(View.VISIBLE);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
            }
        });

        mAdapter = new TeamAdapter(new ArrayList<>());
        mAdapter.setHeaderView(new View(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void doBusiness() {
        if (!StringUtils.isEmpty(SPManager.getId())) {
            mPresenter.getMinePoolData(1);
            mTvNoData.setVisibility(View.GONE);
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }

    private void getData() {
        if (!StringUtils.isEmpty(SPManager.getId())) {
            mPresenter.getMinePoolData(mPage);
        }
    }

    @Override
    public void onViewClick(@NonNull View view) {
        if (view.getId() == R.id.img_field) {
            if (isLogin()) {
                MineFieldActivity.start(mActivity);
            }
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_update_mine:
                if (!StringUtils.isEmpty(SPManager.getId())) {
                    mTvFliecoinNum.setText("0.00");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String format1 = format.format(new Date(SPManager.getCreateTime()));
                    mTvFliecoinTime.setText(format1);
                    mTvGtseTime.setText(format1);
                    mPage = 1;
                    getData();
                } else {
                    mTvFliecoinNum.setText("0.00");
                    mTvGtseNum.setText("0.00");
                    mTvFliecoinTime.setText("");
                    mTvGtseTime.setText("");
                    mAdapter.update(null);
                    mTvNoData.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.msg_update:
                getData();
                break;
            default:
                break;
        }
    }

    @Override
    public void getMinePoolData(MinePool minePool) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        if (!ObjectUtils.isEmpty(minePool)) {
            if (!StringUtils.isEmpty(minePool.getToday())) {
                mTvGtseNum.setText(minePool.getToday());
            } else {
                mTvGtseNum.setText("");
            }
            if (!StringUtils.isEmpty(minePool.getAll())) {
                mTvFliecoinNum.setText(minePool.getAll());
            } else {
                mTvFliecoinNum.setText("");
            }
            if (!StringUtils.isEmpty(SPManager.getId())) {
                mRefreshLayout.autoRefresh();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String format1 = format.format(new Date(minePool.getAllDate()));
                String format2 = format.format(new Date(minePool.getTodayDate()));
                mTvFliecoinTime.setText(format1);
                mTvGtseTime.setText(format2);
            }
            setData(minePool.getList());
        }
    }

    private void setData(List<MinePool.ListBean> list) {
        List<MinePool.ListBean> data = mAdapter.getData();
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
