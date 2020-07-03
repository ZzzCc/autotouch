package com.hdf.autotouch.adapter;

import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.ui.orderdetail.OrderDetailActivity;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.ui.resetloginpassword.ResetLoginPasswordActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.SPManager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

public class OrderAdapter extends SingleAdapter<Order> {

    public OrderAdapter(List<Order> list) {
        super(list, R.layout.item_order);
    }

    @Override
    protected void bind(BaseViewHolder holder, Order data) {
        TextView tvOrderName = holder.getView(R.id.tv_order_name);
        TextView tvStatus = holder.getView(R.id.tv_status);
        RecyclerView rvOrderGoods = holder.getView(R.id.rv_order_goods);
        TextView tvGoodsQuantity = holder.getView(R.id.tv_goods_quantity);
        TextView tvGtse = holder.getView(R.id.tv_gtse);
        QMUIRoundButton btnClickPayment = holder.getView(R.id.btn_click_payment);
        QMUIRoundButton btnCancelOrder = holder.getView(R.id.btn_cancel_order);

        tvOrderName.setText(data.getProductTitle());

        switch (data.getStatus()) {
            case 0: {
                tvStatus.setText(StringUtils.getString(R.string.order_status0));
                tvStatus.setTextColor(ColorUtils.getColor(R.color.red));
                btnClickPayment.setVisibility(View.VISIBLE);
                btnCancelOrder.setVisibility(View.VISIBLE);
            }
            break;
            case 1: {
                tvStatus.setText(StringUtils.getString(R.string.order_status1));
                tvStatus.setTextColor(ColorUtils.getColor(R.color.text_color_2));
                btnClickPayment.setVisibility(View.GONE);
                btnCancelOrder.setVisibility(View.GONE);
            }
            break;
            case 2: {
                tvStatus.setText(StringUtils.getString(R.string.order_status2));
                tvStatus.setTextColor(ColorUtils.getColor(R.color.green));
                btnClickPayment.setVisibility(View.GONE);
                btnCancelOrder.setVisibility(View.GONE);
            }
            break;
            case 3: {
                tvStatus.setText(StringUtils.getString(R.string.order_status3));
                tvStatus.setTextColor(ColorUtils.getColor(R.color.green));
                btnClickPayment.setVisibility(View.GONE);
                btnCancelOrder.setVisibility(View.GONE);
            }
            break;
            default:
                break;
        }

        OrderGoodsAdapter goodsAdapter = new OrderGoodsAdapter(data.getList(), data.getStatus());
        goodsAdapter.setOnItemClickListener((v, position) -> OrderDetailActivity.start(mContext, data));
        rvOrderGoods.setAdapter(goodsAdapter);
        rvOrderGoods.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.divider1)));
        rvOrderGoods.addItemDecoration(divider);

        tvGoodsQuantity.setText(String.format(StringUtils.getString(R.string.goods_quantity), String.valueOf(data.getNum())));
        tvGtse.setText(String.format(StringUtils.getString(R.string.usdt3), data.getAmount()));

        applyClickListener(v -> {
            switch (v.getId()) {
                case R.id.btn_cancel_order: {
                    Message msg = new Message();
                    msg.what = R.id.msg_cancel_order;
                    msg.obj = data;
                    EventBus.getDefault().post(msg);
                }
                break;
                case R.id.btn_click_payment: {
                    if (!SPManager.getHavePayPassword()) {
                        DialogHelper.showConfirmDialog(0, mContext.getString(R.string.setting_pay_password), () -> ResetLoginPasswordActivity.start(mContext, 1));
                    } else {
                        PayPasswordActivity.start(mContext, 1);
                        Message msg = new Message();
                        msg.what = R.id.msg_click_payment;
                        msg.obj = data;
                        EventBus.getDefault().post(msg);
                    }
                }
                break;
                default:
                    break;
            }
        }, btnCancelOrder, btnClickPayment);
    }
}
