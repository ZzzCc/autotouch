package com.hdf.autotouch.ui.myorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.OrderAdapter;
import com.hdf.autotouch.adapter.ViewPagerAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.ui.orderdetail.OrderDetailActivity;
import com.hdf.autotouch.util.EncryptUtils;
import com.qmuiteam.qmui.widget.QMUITabSegment;
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
 *     desc  : 我的订单
 * </pre>
 */
public class MyOrderActivity extends BaseActivity<MyOrderPresenter> implements MyOrderView {

    public static final int SIZE = 3;
    @BindView(R.id.tab_segment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.View_pager)
    ViewPager      mViewPager;
    private List<View>         mViewList    = new ArrayList<>(SIZE);
    private List<Integer>      mPageList    = new ArrayList<>(SIZE);
    private List<OrderAdapter> mAdapterList = new ArrayList<>(SIZE);
    private Order              mCurrentOrder;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyOrderActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initView() {
        mPresenter = new MyOrderPresenter(this);

        setTitleText(R.string.my_order);

        initContentViews();
        ViewPagerAdapter adapter = new ViewPagerAdapter(mViewList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0, false);
        mTabSegment.setDefaultSelectedColor(ColorUtils.getColor(R.color.app_color));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.order_status0)));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.order_status1)));
        mTabSegment.addTab(new QMUITabSegment.Tab(getString(R.string.order_status2)));
        mTabSegment.setupWithViewPager(mViewPager, false);
        mTabSegment.setPadding(SizeUtils.sp2px(8), 0, SizeUtils.sp2px(8), 0);
//        mTabSegment.setOnTabClickListener(new QMUITabSegment.OnTabClickListener() {
//            @Override
//            public void onTabClick(int index) {
//                mPresenter.getOrder(index, 1);
//            }
//        });
    }

    private void initContentViews() {
        mViewList.clear();
        mPageList.clear();
        mAdapterList.clear();
        for (int i = 0; i < SIZE; i++) {
            int index = i;

            mPageList.add(1);

            View view = LayoutInflater.from(mViewPager.getContext()).inflate(R.layout.item_view_pager, mViewPager, false);
            SmartRefreshLayout refreshLayout = view.findViewById(R.id.refresh_layout);
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

            refreshLayout.setOnRefreshListener(layout -> {
                layout.finishRefresh();
                mPageList.set(index, 1);
                getData(index);
            });
            refreshLayout.setOnLoadMoreListener(layout -> {
                layout.finishLoadMore();
                mPageList.set(index, mPageList.get(index) + 1);
                getData(index);
            });

            OrderAdapter adapter = new OrderAdapter(new ArrayList<>());
            adapter.setOnItemClickListener((v, position) -> OrderDetailActivity.start(mActivity, adapter.getData().get(position)));
            mAdapterList.add(adapter);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
            DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
            divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mActivity, R.drawable.divider16)));
            recyclerView.addItemDecoration(divider);

            refreshLayout.autoRefresh();

            mViewList.add(view);
        }
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_update_order: {
                refresh();
            }
            break;
            case R.id.msg_click_payment: {
                mCurrentOrder = (Order) msg.obj;
            }
            break;
            case R.id.msg_cancel_order: {
                Order order = (Order) msg.obj;
                mPresenter.cancelOrder(order.getOrderId());
            }
            break;
            case R.id.msg_input_pay_password1: {
                String password = (String) msg.obj;
                if (!ObjectUtils.isEmpty(mCurrentOrder)) {
                    mPresenter.payOrder(mCurrentOrder.getOrderId(), EncryptUtils.encryptSha256(password));
                }
            }
            break;
            case R.id.msg_one_bind: {
                String orderId = (String) msg.obj;
                mPresenter.getAllBinding(orderId);
            }
            break;
            default:
                break;
        }
    }

    private void refresh() {
        mPageList.set(mViewPager.getCurrentItem(), 1);
        getData(mViewPager.getCurrentItem());
    }

    @Override
    public void getOrder(List<Order> list, int index) {
        setData(list, index);
    }

    @Override
    public void payOrder(Order order) {
        mCurrentOrder = null;
        refresh();
    }

    @Override
    public void payOrderError() {
        mCurrentOrder = null;
        refresh();
    }

    @Override
    public void cancelOrder() {
        refresh();
    }

    @Override
    public void getAllBinding(Integer num) {
        ToastUtils.showShort("已成功绑定" + num + "台");
        mPresenter.getOrder(2, 1);
    }

    private void getData(int index) {
        mPresenter.getOrder(index, mPageList.get(index));
    }

    @SuppressLint("CutPasteId")
    private void setData(List<Order> list, int index) {
        View view = mViewList.get(index);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        TextView tvNoData = view.findViewById(R.id.tv_no_data);
        List<Order> data = mAdapterList.get(index).getData();
        if (mPageList.get(index) == 1) {
            data = list;
            recyclerView.scrollToPosition(0);
        } else {
            if (ObjectUtils.isEmpty(list)) {
                mPageList.set(index, mPageList.get(index) - 1);
            } else {
                data.addAll(list);
            }
        }
        mAdapterList.get(index).update(data);
        if (ObjectUtils.isEmpty(data)) {
            tvNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
