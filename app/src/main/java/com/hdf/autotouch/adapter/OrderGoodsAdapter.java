package com.hdf.autotouch.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.OrderGoods;
import com.hdf.autotouch.util.ImageLoaderHelper;

import java.util.List;

public class OrderGoodsAdapter extends SingleAdapter<OrderGoods> {

    private int mStatus;

    public OrderGoodsAdapter(List<OrderGoods> list, int status) {
        super(list, R.layout.item_order_goods);
        mStatus = status;
    }

    @Override
    protected void bind(BaseViewHolder holder, OrderGoods data) {
        ImageView ivGoods = holder.getView(R.id.iv_goods);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvBindStatus = holder.getView(R.id.tv_bind_status);
        TextView tvPrice = holder.getView(R.id.tv_price);
        TextView tvMultiply = holder.getView(R.id.tv_multiply);

        ImageLoaderHelper.loadImage(mContext, data.getIcon(), ivGoods);
        tvName.setText(data.getServerName());
//        if (mStatus == 0 || mStatus == 1) {
//            tvBindStatus.setVisibility(View.GONE);
//        } else {
//            tvBindStatus.setVisibility(View.VISIBLE);
//        }
//        if (data.getStatus() == 0) {
//            tvBindStatus.setText(StringUtils.getString(R.string.not_bound));
//            tvBindStatus.setTextColor(ColorUtils.getColor(R.color.orange));
//        } else if (data.getStatus() == 1) {
//            tvBindStatus.setText(StringUtils.getString(R.string.order_status3));
//            tvBindStatus.setTextColor(ColorUtils.getColor(R.color.green));
//        }
        tvBindStatus.setVisibility(View.GONE);
        tvPrice.setText(String.format(StringUtils.getString(R.string.usdt3), data.getValue()));
        tvMultiply.setText(String.format(StringUtils.getString(R.string.multiply), "1"));
    }
}
