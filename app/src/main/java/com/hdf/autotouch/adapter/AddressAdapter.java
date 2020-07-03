package com.hdf.autotouch.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.Address;
import com.hdf.autotouch.ui.address.AddAddressActivity;

import java.util.List;

public class AddressAdapter extends SingleAdapter<Address> {

    public AddressAdapter(List<Address> list) {
        super(list, R.layout.item_address);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(BaseViewHolder holder, Address data) {
        TextView tvConsignee = holder.getView(R.id.tv_consignee);
        TextView tvPhone = holder.getView(R.id.tv_phone);
        TextView tvShippingAddress = holder.getView(R.id.tv_shipping_address);
        TextView tvEdit = holder.getView(R.id.tv_edit);
        TextView tvDelete = holder.getView(R.id.tv_delete);

        tvConsignee.setText(String.format(StringUtils.getString(R.string.consignee), data.getUserName()));
        tvPhone.setText(data.getPrefix() + data.getReceiptPhone());
        tvShippingAddress.setText(String.format(StringUtils.getString(R.string.shipping_address), StringUtils.getString(R.string.deliver_explain_content)));

        applyClickListener(v -> {
            switch (v.getId()) {
                case R.id.tv_edit: {
                    AddAddressActivity.start(mContext, data);
                }
                break;
                case R.id.tv_delete: {
                   /* DialogHelper.showConfirmDialog(R.string.delete, "", () -> {
                        Message msg = new Message();
                        msg.what = R.id.msg_delete_address;
                        msg.obj = data;
                        EventBus.getDefault().post(msg);
                    });*/
                }
                break;
                default:
                    break;
            }
        }, tvEdit, tvDelete);
    }
}
