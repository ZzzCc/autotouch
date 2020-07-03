package com.hdf.autotouch.adapter;

import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.MyMill;

import java.util.List;

public class MacAaddressAdapter extends SingleAdapter<MyMill> {

    public MacAaddressAdapter(List<MyMill> list) {
        super(list, R.layout.item_macaddress);
    }

    @Override
    protected void bind(BaseViewHolder holder, MyMill data) {
        TextView mTvMacAddress = holder.getView(R.id.tv_mac_address);
        TextView mTvBinding = holder.getView(R.id.tv_binding);

        mTvMacAddress.setText(String.format(StringUtils.getString(R.string.mac_address1), data.getMac()));

        //	是否绑定(1-绑定，0-未绑定)
        if (data.getStatus() == 0) {
            mTvBinding.setText("未绑定");
            mTvBinding.setTextColor(mContext.getResources().getColor(R.color.red));
        } else if (data.getStatus() == 1) {
            mTvBinding.setText("已绑定");
            mTvBinding.setTextColor(mContext.getResources().getColor(R.color.light_black2));
        } else {
            mTvBinding.setText("");
        }

    }


}
