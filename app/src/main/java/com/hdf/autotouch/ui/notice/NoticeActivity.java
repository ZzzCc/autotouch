package com.hdf.autotouch.ui.notice;

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
import com.hdf.autotouch.adapter.NoticeAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Notice;
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
 *     desc  : 公告
 * </pre>
 */
public class NoticeActivity extends BaseActivity<NoticePresenter> implements NoticeView {

    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private NoticeAdapter mAdapter;
    private int           mPage = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, NoticeActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_notice;
    }

    @Override
    public void initView() {
        mPresenter = new NoticePresenter(this);

        setTitleText(R.string.notice);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage = 1;
            getData();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPage += 1;
            getData();
        });

        mAdapter = new NoticeAdapter(new ArrayList<>());
        mAdapter.setHeaderView(new View(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mActivity, R.drawable.divider16)));
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void doBusiness() {
        getData();
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @Override
    public void getNotice(List<Notice> list) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        setData(list);
    }

    private void getData() {
        mPresenter.getNotice(mPage);
    }

    private void setData(List<Notice> list) {
        List<Notice> data = mAdapter.getData();
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
