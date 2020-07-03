package com.hdf.autotouch.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.CollectTransferDetail;

import java.util.List;

public class CollectTransferDetailAdapter extends SingleAdapter<CollectTransferDetail> {

    public CollectTransferDetailAdapter(List<CollectTransferDetail> list) {
        super(list, R.layout.item_collect_transfer_detail);
    }

    @Override
    protected void bind(BaseViewHolder holder, CollectTransferDetail data) {
        ImageView tvType = holder.getView(R.id.tv_type);
        TextView tvTime = holder.getView(R.id.tv_time);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvValue = holder.getView(R.id.tv_value);

        if (data.getDifferent() == 1) {
            tvType.setBackgroundResource(R.drawable.ic_collect_coins);
//            tvType.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Utils.getApp(), R.drawable.ic_collect_coin_small), null, null, null);
//            tvType.setText(StringUtils.getString(R.string.collect_coin));
            tvValue.setText(String.format(StringUtils.getString(R.string.plus_gtse), data.getAmount() + " " + data.getCoin()));
            tvValue.setTextColor(ColorUtils.getColor(R.color.green));
            tvName.setText(data.getTxFrom());
        } else {
            tvType.setBackgroundResource(R.drawable.ic_turn_coins);
//            tvType.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Utils.getApp(), R.drawable.ic_transfer_coin_small), null, null, null);
//            tvType.setText(StringUtils.getString(R.string.transfer_coin));
            tvValue.setText(String.format(StringUtils.getString(R.string.subtract_gtse), data.getAmount() + " " + data.getCoin()));
            tvValue.setTextColor(ColorUtils.getColor(R.color.red));
            tvName.setText(data.getTxTo());
        }
        tvTime.setText(TimeUtils.millis2String(data.getTimeStamp()));
    }
}
