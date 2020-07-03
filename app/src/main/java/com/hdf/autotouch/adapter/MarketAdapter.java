package com.hdf.autotouch.adapter;

import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.Market;
import com.hdf.autotouch.util.ImageLoaderHelper;

import java.text.DecimalFormat;
import java.util.List;

public class MarketAdapter extends SingleAdapter<Market> {

    public MarketAdapter(List<Market> list) {
        super(list, R.layout.item_market);
    }

    @Override
    protected void bind(BaseViewHolder holder, Market data) {
        ImageView ivCurrency = holder.getView(R.id.iv_currency);
        TextView tvCurrency = holder.getView(R.id.tv_currency);
        TextView tvPriceUsd = holder.getView(R.id.tv_price_usd);
        TextView tvPriceCny = holder.getView(R.id.tv_price_cny);
        TextView tvRange = holder.getView(R.id.tv_range);

        if (!StringUtils.isEmpty(data.getIcon())) {
            ImageLoaderHelper.loadCirclImage(mContext, data.getIcon(), ivCurrency);
        }

        tvCurrency.setText(data.getSymbol());

        tvPriceUsd.setText(String.format(StringUtils.getString(R.string.usd_symbol), data.getUsdtPrice()));

        tvPriceCny.setText(String.format(StringUtils.getString(R.string.cny_symbol), data.getCnyPrice()));

        DecimalFormat df = new DecimalFormat("#.##");
        String format = df.format(Double.valueOf(data.getChange_daily()));
        if (data.getChange_daily().contains("-")) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(SizeUtils.dp2px(15));
            drawable.setColor(ColorUtils.getColor(R.color.red));
            tvRange.setBackgroundDrawable(drawable);
            tvRange.setText(String.format(StringUtils.getString(R.string.percent), format));
        } else {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(SizeUtils.dp2px(15));
            drawable.setColor(ColorUtils.getColor(R.color.green));
            tvRange.setBackgroundDrawable(drawable);
            tvRange.setText(String.format(StringUtils.getString(R.string.plus_percent), format));
        }
    }
}
