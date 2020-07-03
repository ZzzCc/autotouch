package com.hdf.autotouch.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.util.ImageLoaderHelper;

import java.util.List;

import static com.blankj.utilcode.util.StringUtils.getString;

public class GoodsAdapter extends SingleAdapter<Goods> {

    public GoodsAdapter(List<Goods> list) {
        super(list, R.layout.item_goods);
    }

    @Override
    protected void bind(BaseViewHolder holder, Goods data) {
        ImageView ivGoods = holder.getView(R.id.iv_goods);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvPrice = holder.getView(R.id.tv_price);

        ImageLoaderHelper.loadImage(mContext, data.getpLittlePic(), ivGoods);
        tvName.setText(data.getpTitle());
        tvPrice.setText(String.format(getString(R.string.locker), String.format(getString(R.string.usdt3), data.getPrice())));

    }
}
