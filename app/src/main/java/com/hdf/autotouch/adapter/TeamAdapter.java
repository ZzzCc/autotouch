package com.hdf.autotouch.adapter;

import android.widget.TextView;

import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.MinePool;

import java.util.List;

public class TeamAdapter extends SingleAdapter<MinePool.ListBean> {


    public TeamAdapter(List<MinePool.ListBean> list) {
        super(list, R.layout.item_my_server_layout);
    }

    @Override
    protected void bind(BaseViewHolder holder, MinePool.ListBean data) {

        TextView mTvServerName = holder.getView(R.id.tv_server_name);
        TextView mTvState = holder.getView(R.id.tv_state);
        TextView mTvTodayOutput = holder.getView(R.id.tv_today_output);
        TextView mTvTotalOutput = holder.getView(R.id.tv_total_output);

        //服务器名称
        if (!"".equals(data.getMac()) && data.getMac() != null) {
            mTvServerName.setText(data.getMac());
        } else {
            mTvServerName.setText("");
        }

        mTvState.setText(data.getDeductAsString());
//        //状态  0-未缴费 关闭 1-缴费 正常
//        if (data.getDeductAsString() == 0) {
//            mTvState.setText(R.string.close);
//            mTvState.setTextColor(mContext.getResources().getColor(R.color.color_9B3F3E));
//        } else if (data.getDeductAsString() == 1) {
//            mTvState.setText(R.string.normal);
//            mTvState.setTextColor(mContext.getResources().getColor(R.color.green));
//        } else {
//            mTvState.setText("");
//        }


    }


}
