package com.hdf.autotouch.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.MyBill;
import com.hdf.autotouch.util.FormatUtils;

import java.util.List;

public class BillAdapter extends SingleAdapter<MyBill> {

    public BillAdapter(List<MyBill> list) {
        super(list, R.layout.item_bill);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(BaseViewHolder holder, MyBill data) {
        TextView mTvBillTime = holder.getView(R.id.tv_bill_time);
        TextView mTvBillContext = holder.getView(R.id.tv_bill_context);

        if (!"".equals(data.getTimeStamp()) && data.getTimeStamp() != 0) {
            mTvBillTime.setText(TimeUtils.millis2String(data.getTimeStamp()));
        } else {
            mTvBillTime.setText("");
        }

        //different	1-收入 2-支出
        if (data.getDifferent() == 1) {
            mTvBillContext.setTextColor(ColorUtils.getColor(R.color.app_color));
            mTvBillContext.setText(FormatUtils.getBillType(data.getType()) + "+" + data.getAmount() + " " + data.getCoin());
        } else if (data.getDifferent() == 2) {
            mTvBillContext.setTextColor(ColorUtils.getColor(R.color.red));
            mTvBillContext.setText(FormatUtils.getBillType(data.getType()) + "-" + data.getAmount() + " " + data.getCoin());
        } else {
            mTvBillContext.setText("");
        }
    }
}
