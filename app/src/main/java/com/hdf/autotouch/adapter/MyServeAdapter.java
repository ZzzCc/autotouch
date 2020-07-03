package com.hdf.autotouch.adapter;

import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.MyMac;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.ui.resetloginpassword.ResetLoginPasswordActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.SPManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyServeAdapter extends SingleAdapter<MyMac.MacModelsBean> {


    public MyServeAdapter(List<MyMac.MacModelsBean> list) {
        super(list, R.layout.item_my_serve);
    }

    @Override
    protected void bind(BaseViewHolder holder, MyMac.MacModelsBean data) {
        TextView mTvMyServe = holder.getView(R.id.tv_my_serve);
        TextView mTvStatus = holder.getView(R.id.tv_status);
        ImageView mImgArrows = holder.getView(R.id.img_arrows);
        ConstraintLayout mViewConstraint = holder.getView(R.id.view_constraint);
        ConstraintLayout mViewConstraint1 = holder.getView(R.id.view_constraint1);
        TextView mTvMacAddress = holder.getView(R.id.tv_mac_address);
        TextView mTvTrusteeship = holder.getView(R.id.tv_trusteeship);
        TextView mTvPayState = holder.getView(R.id.tv_pay_state);
        TextView mTvPay = holder.getView(R.id.tv_pay);

        mTvMyServe.setText(data.getName());

        //是否缴纳托管费 1：交过 0：未交过
        if (data.getDeduct() == 1) {
            mTvStatus.setTextColor(mContext.getResources().getColor(R.color.green));
            mTvStatus.setText(R.string.normal);
            mTvPayState.setVisibility(View.GONE);
            mTvPay.setVisibility(View.GONE);
        } else if (data.getDeduct() == 0) {
            mTvStatus.setTextColor(mContext.getResources().getColor(R.color.red));
            mTvStatus.setText(R.string.close);
            mTvPayState.setVisibility(View.VISIBLE);
            mTvPay.setVisibility(View.VISIBLE);
        } else {
            mTvStatus.setText("");
        }

        mTvMacAddress.setText("MAC地址：" + data.getMac());
        mTvTrusteeship.setText("每日托管费： " + data.getFee() + "GTSE");

        //展开
        applyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewConstraint1.getVisibility() == View.VISIBLE) {
                    mViewConstraint1.setVisibility(View.GONE);
                    mImgArrows.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_arrows));
                } else {
                    mViewConstraint1.setVisibility(View.VISIBLE);
                    mImgArrows.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_up_arrows));
                }
            }
        }, mViewConstraint);
        //支付
        applyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SPManager.getHavePayPassword()) {
                    DialogHelper.showConfirmDialog(0, mContext.getString(R.string.setting_pay_password), new DialogHelper.OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            ResetLoginPasswordActivity.start(mContext, 1);
                        }
                    });

                } else {
                    Message msg = new Message();
                    msg.what = R.id.msg_mac_address;
                    msg.obj = data.getMac();
                    EventBus.getDefault().post(msg);
                    PayPasswordActivity.start(mContext, 0);
                }

            }
        }, mTvPay);

    }


}
