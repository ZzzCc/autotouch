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

public class OrderDetailGoodsAdapter extends SingleAdapter<OrderGoods> {

    public OrderDetailGoodsAdapter(List<OrderGoods> list) {
        super(list, R.layout.item_order_detail_goods);
    }

    @Override
    protected void bind(BaseViewHolder holder, OrderGoods data) {
        ImageView ivGoods = holder.getView(R.id.iv_goods);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvBindStatus = holder.getView(R.id.tv_bind_status);
        TextView tvPrice = holder.getView(R.id.tv_price);

        ImageLoaderHelper.loadImage(mContext, data.getIcon(), ivGoods);
        tvName.setText(data.getServerName());
//        if (data.getStatus() == 0) {
//            tvBindStatus.setText(StringUtils.getString(R.string.not_bound));
//            tvBindStatus.setTextColor(ColorUtils.getColor(R.color.red));
//        } else if (data.getStatus() == 1) {
//            tvBindStatus.setText(StringUtils.getString(R.string.order_status3));
//            tvBindStatus.setTextColor(ColorUtils.getColor(R.color.light_black1));
//        } else {
//            tvBindStatus.setText("");
//        }
        tvBindStatus.setVisibility(View.GONE);
        tvPrice.setText(String.format(StringUtils.getString(R.string.usdt3), data.getValue()));
    }
}
