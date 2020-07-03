package com.hdf.autotouch.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.Transaction;

import java.util.List;

public class TransactionAdapter extends SingleAdapter<Transaction> {

    public TransactionAdapter(List<Transaction> list) {
        super(list, R.layout.item_transaction);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(BaseViewHolder holder, Transaction data) {
        TextView tvType = holder.getView(R.id.tv_type);
        TextView tvQuantityValue = holder.getView(R.id.tv_quantity_value);
        TextView tvStatusValue = holder.getView(R.id.tv_status_value);
        TextView tvTimeValue = holder.getView(R.id.tv_time_value);

        if (data.getType() == 5) {
            tvType.setText(R.string.withdraw_coin);
        } else if (data.getType() == 6) {
            tvType.setText(R.string.charge_coin);
        } else {
            tvType.setText("");
        }

        tvQuantityValue.setText(data.getAmount() + " " + data.getCoin());

        if (data.getStatus() == 0) {
            tvStatusValue.setText(R.string.in_review);
            tvStatusValue.setTextColor(ColorUtils.getColor(R.color.yellow));
        } else if (data.getStatus() == 1) {
            tvStatusValue.setText(R.string.success);
            tvStatusValue.setTextColor(ColorUtils.getColor(R.color.green));
        } else if (data.getStatus() == -1) {
            tvStatusValue.setText(R.string.fail);
            tvStatusValue.setTextColor(ColorUtils.getColor(R.color.red));
        } else if (data.getStatus() == -2) {
            tvStatusValue.setText(R.string.reject);
            tvStatusValue.setTextColor(ColorUtils.getColor(R.color.red));
        }

        tvTimeValue.setText(TimeUtils.millis2String(data.getTimeStamp()));
    }
}
