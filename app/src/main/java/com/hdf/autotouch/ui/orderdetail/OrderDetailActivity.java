package com.hdf.autotouch.ui.orderdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.ViewUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.OrderDetailGoodsAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.util.EncryptUtils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 订单详情
 * </pre>
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailView {

    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;
    @BindView(R.id.tv_order_status)
    TextView         mTvOrderStatus;
    @BindView(R.id.recycler_view)
    RecyclerView     mRecyclerView;
    @BindView(R.id.tv_goods_quantity)
    TextView         mTvGoodsQuantity;
    @BindView(R.id.tv_gtse)
    TextView         mTvGtse;
    @BindView(R.id.tv_order_number)
    TextView         mTvOrderNumber;
    @BindView(R.id.tv_create_time)
    TextView         mTvCreateTime;
    @BindView(R.id.tv_payment_time)
    TextView         mTvPaymentTime;
    @BindView(R.id.tv_alert)
    TextView         mTvAlert;
    @BindView(R.id.btn_cancel_order)
    QMUIRoundButton  mBtnCancelOrder;
    @BindView(R.id.btn_click_payment)
    QMUIRoundButton  mBtnClickPayment;
    private Order mOrder;

    public static void start(Context context, Order order) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("order", order);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mOrder = bundle.getParcelable("order");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        mPresenter = new OrderDetailPresenter(this);

        applyLightMode(false);
        setTitleText(R.string.order_detail);
        applyClickListener(mBtnCancelOrder, mBtnClickPayment);

        setData();
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        switch (mOrder.getStatus()) {
            case 0: {
                mTvOrderStatus.setText(StringUtils.getString(R.string.order_status0));
                mBtnClickPayment.setVisibility(View.VISIBLE);
                mBtnCancelOrder.setVisibility(View.VISIBLE);
                mTvPaymentTime.setVisibility(View.GONE);
                mTvAlert.setText("");
            }
            break;
            case 1: {
                mTvOrderStatus.setText(StringUtils.getString(R.string.order_status1));
                mBtnClickPayment.setVisibility(View.GONE);
                mBtnCancelOrder.setVisibility(View.GONE);
                mTvPaymentTime.setVisibility(View.GONE);
                mTvAlert.setText("");
            }
            break;
            case 2: {
                mTvOrderStatus.setText(StringUtils.getString(R.string.order_status2));
                mBtnClickPayment.setVisibility(View.GONE);
                mBtnCancelOrder.setVisibility(View.GONE);
                mTvAlert.setText("");
            }
            break;
            case 3: {
                mTvOrderStatus.setText(StringUtils.getString(R.string.order_status3));
                mBtnClickPayment.setVisibility(View.GONE);
                mBtnCancelOrder.setVisibility(View.GONE);
                mTvAlert.setText("");
            }
            break;
            default:
                break;
        }

        OrderDetailGoodsAdapter adapter = new OrderDetailGoodsAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter.setData(mOrder.getList());
        mTvGoodsQuantity.setText(String.format(getString(R.string.goods_quantity), String.valueOf(mOrder.getNum())));
        mTvGtse.setText(String.format(getString(R.string.usdt3), mOrder.getAmount()));
        mTvOrderNumber.setText(String.format(getString(R.string.order_number1), mOrder.getOrderId()));
        mTvCreateTime.setText(String.format(getString(R.string.create_time), TimeUtils.millis2String(mOrder.getCreatTime())));
        mTvPaymentTime.setText(String.format(getString(R.string.payment_time), TimeUtils.millis2String(mOrder.getPayTime())));

        ViewUtils.fixScrollViewTopping(mScrollView);
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_order: {
                mPresenter.cancelOrder(mOrder.getOrderId());
            }
            break;
            case R.id.btn_click_payment: {
                if (havePayPassword()) {
                    PayPasswordActivity.start(this, 0);
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
            case R.id.msg_update_order: {
                mPresenter.getOrderDetail(mOrder.getOrderId());
            }
            break;
            case R.id.msg_cancel_order: {
                Order order = (Order) msg.obj;
                mPresenter.cancelOrder(order.getOrderId());
            }
            break;
            case R.id.msg_input_pay_password: {
                String password = (String) msg.obj;
                mPresenter.payOrder(mOrder.getOrderId(), EncryptUtils.encryptSha256(password));
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void getOrderDetail(Order order) {
        mOrder = order;
        setData();
    }

    @Override
    public void payOrder(Order order) {
        sendMessage(R.id.msg_update_order, null);
    }

    @Override
    public void payOrderError() {
        sendMessage(R.id.msg_update_order, null);
    }

    @Override
    public void cancelOrder() {
        sendMessage(R.id.msg_update_order, null);
    }

    @Override
    public void getAllBinding(Integer num) {
        if (!StringUtils.equals(num + "", "-1")) {
            ToastUtils.showShort("已成功绑定" + num + "台");
            mPresenter.getOrderDetail(mOrder.getOrderId());
        } else {
            ToastUtils.showShort("绑定失败");
        }
    }
}
