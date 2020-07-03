package com.hdf.autotouch.adapter;

import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.Exchange;

import java.util.List;

public class ExchangeAdapter extends SingleAdapter<Exchange> {

    public ExchangeAdapter(List<Exchange> list) {
        super(list, R.layout.item_exchange);
    }

    @Override
    protected void bind(BaseViewHolder holder, Exchange data) {
        TextView tvFrom = holder.getView(R.id.tv_from);
        TextView tvFromValue = holder.getView(R.id.tv_from_value);
        TextView tvTo = holder.getView(R.id.tv_to);
        TextView tvToValue = holder.getView(R.id.tv_to_value);
        TextView tvTime = holder.getView(R.id.tv_time);

        tvFrom.setText(data.getInCoin());
        tvFromValue.setText(data.getInAmount());
        tvTo.setText(data.getOutCoin());
        tvToValue.setText(data.getOutAmount());
        tvTime.setText(TimeUtils.millis2String(data.getTimeStamp()));
    }
}
