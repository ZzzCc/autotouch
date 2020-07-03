package com.hdf.autotouch.ui.confirmorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.ui.payment.PaymentActivity;
import com.hdf.autotouch.util.ImageLoaderHelper;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 确认订单
 * </pre>
 */
public class ConfirmOrderActivity extends BaseActivity<ConfirmOrderPresenter> implements ConfirmOrderView {

    @BindView(R.id.iv_goods)
    ImageView       mIvGoods;
    @BindView(R.id.tv_name)
    TextView        mTvName;
    @BindView(R.id.tv_price)
    TextView        mTvPrice;
    @BindView(R.id.tv_multiply)
    TextView        mTvMultiply;
    @BindView(R.id.tv_free_warranty)
    TextView        mTvFreeWarranty;
    @BindView(R.id.tv_goods_quantity)
    TextView        mTvGoodsQuantity;
    @BindView(R.id.tv_amount)
    TextView        mTvAmount;
    @BindView(R.id.tv_total_amount)
    TextView        mTvTotalAmount;
    @BindView(R.id.tv_submit_order)
    QMUIRoundButton mTvSubmitOrder;
    private Goods  mGoods;
    private String mQuantity;

    public static void start(Context context, Goods goods, String quantity) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra("goods", goods);
        intent.putExtra("quantity", quantity);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mGoods = bundle.getParcelable("goods");
        mQuantity = bundle.getString("quantity");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initView() {
        mPresenter = new ConfirmOrderPresenter(this);

        setTitleText(R.string.confirm_order);

        applyClickListener(mTvSubmitOrder);

        ImageLoaderHelper.loadImage1(this, mGoods.getpLittlePic(), mIvGoods);
        mTvName.setText(mGoods.getpName());
        mTvPrice.setText(String.format(getString(R.string.usdt3), mGoods.getPrice()));
        mTvMultiply.setText(String.format(getString(R.string.multiply), mQuantity));
        mTvFreeWarranty.setText(String.format(getString(R.string.free_warranty), mGoods.getFixTime()));
        mTvGoodsQuantity.setText(String.format(getString(R.string.goods_quantity), mQuantity));
        mTvAmount.setText(String.format(getString(R.string.usdt3), String.valueOf(Double.valueOf(mGoods.getPrice()) * Integer.valueOf(mQuantity))));
        mTvTotalAmount.setText(String.format(getString(R.string.usdt3), String.valueOf(Double.valueOf(mGoods.getPrice()) * Integer.valueOf(mQuantity))));
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        if (view.getId() == R.id.tv_submit_order) {
            mPresenter.submitOrder(mGoods.getpId(),
                    "",
                    mQuantity,
                    String.valueOf(Double.valueOf(mGoods.getPrice()) * Integer.valueOf(mQuantity)),
                    "");
        }
    }

    @Override
    public void submitOrder(Order order) {
        PaymentActivity.start(this, order);
        finish();
    }
}
