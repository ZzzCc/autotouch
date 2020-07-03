package com.hdf.autotouch.ui.goodsdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.ui.confirmorder.ConfirmOrderActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.FormatUtils;
import com.hdf.autotouch.util.ImageLoaderHelper;
import com.hdf.autotouch.util.SPManager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 商品详情
 * </pre>
 */
public class GoodsDetailActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailView {

    @BindView(R.id.convenient_banner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.tv_price)
    TextView         mTvPrice;
    @BindView(R.id.tv_title)
    TextView         mTvTitle;
    @BindView(R.id.tv_inventory)
    TextView         mTvInventory;
    @BindView(R.id.iv_subtract)
    ImageView        mIvSubtract;
    @BindView(R.id.tv_quantity)
    EditText         mTvQuantity;
    @BindView(R.id.iv_plus)
    ImageView        mIvPlus;
    @BindView(R.id.web_details)
    WebView          mWebDetails;
    @BindView(R.id.btn_purchase_now)
    QMUIRoundButton  mBtnPurchaseNow;
    private Goods mGoods;

    public static void start(Context context, Goods goods) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goods", goods);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mGoods = bundle.getParcelable("goods");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView() {
        mPresenter = new GoodsDetailPresenter(this);

        applyClickListener(mBtnPurchaseNow);

        if (!ObjectUtils.isEmpty(mGoods)) {
            setData();
        } else {
            mPresenter.getGoods();
        }

        mIvSubtract.setEnabled(false);
    }

    private void setData() {
        setTitleText(mGoods.getpTitle());

        SizeUtils.forceGetViewSize(mConvenientBanner, view -> mConvenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public ImageHolderView createHolder(View itemView) {
                        return new ImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_image1;
                    }
                }, mGoods.getSlideshowList()));

        mTvPrice.setText(String.format(getString(R.string.locker), String.format(getString(R.string.usdt3), mGoods.getPrice())));
        mTvTitle.setText(mGoods.getpName());
        mTvInventory.setText(String.format(getString(R.string.inventory), mGoods.getStock()));

        mWebDetails.loadData(mGoods.getDetail(), "text/html", "UTF-8");

        mTvQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!StringUtils.isEmpty(charSequence)) {
                    int buy = Integer.parseInt(charSequence.toString());
                    int max1 = Integer.parseInt(charSequence.toString()) < Integer.valueOf(mGoods.getStock()) ?
                            Integer.parseInt(charSequence.toString()) : Integer.valueOf(mGoods.getStock());
                    int max = max1 < 100 ? max1 : 100;

                    if (buy > max) {
                        mTvQuantity.setText(FormatUtils.formatEndZero(String.valueOf(max)));
                        mTvQuantity.setSelection(mTvQuantity.length());
                    }

                    if (buy <= 1) {
                        mIvSubtract.setEnabled(false);
                    } else {
                        mIvSubtract.setEnabled(true);
                    }

                    if (buy >= Integer.valueOf(mGoods.getStock())) {
                        mIvPlus.setEnabled(false);
                    } else {
                        mIvPlus.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void doBusiness() {
        if (!SPManager.getIsAgree()) {
            DialogHelper.showPurchaseAgreementDialog();
        }
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_purchase_now: {
                if (isLogin()) {
                    if (!ObjectUtils.isEmpty(mGoods)) {
                        if (!StringUtils.isEmpty(mTvQuantity.getText().toString())) {
                            if (!StringUtils.equals(mTvQuantity.getText().toString(), "0")) {
                                if (Integer.valueOf(mTvQuantity.getText().toString()) <= Integer.valueOf(mGoods.getStock())) {
                                    if (Integer.valueOf(mTvQuantity.getText().toString()) > 0 && Integer.valueOf(mTvQuantity.getText().toString()) <= 100) {
                                        ConfirmOrderActivity.start(this, mGoods, mTvQuantity.getText().toString());
                                    } else {
                                        ToastUtils.showShort(getString(R.string.inventorys));
                                    }
                                } else {
                                    ToastUtils.showShort(getString(R.string.inventory_no));
                                }
                            } else {
                                ToastUtils.showShort(getString(R.string.input_inventory_num));
                            }
                        } else {
                            ToastUtils.showShort(getString(R.string.input_inventory_num));
                        }
                    }
                }
            }
            break;
            default:
                break;
        }
    }

    @OnClick({R.id.iv_subtract, R.id.iv_plus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_subtract: {
                if (StringUtils.isEmpty(mTvQuantity.getText().toString())) {
                    mTvQuantity.setText("1");
                    mTvQuantity.setSelection(mTvQuantity.length());
                } else {
                    if (Integer.valueOf(mTvQuantity.getText().toString()) > 1) {
                        mTvQuantity.setText(String.valueOf(Integer.valueOf(mTvQuantity.getText().toString()) - 1));
                        mTvQuantity.setSelection(mTvQuantity.length());
                    }
                }
            }
            break;
            case R.id.iv_plus: {
                if (StringUtils.isEmpty(mTvQuantity.getText().toString())) {
                    mTvQuantity.setText("1");
                    mTvQuantity.setSelection(mTvQuantity.length());
                } else {
                    if (!ObjectUtils.isEmpty(mGoods)
                            && Integer.valueOf(mTvQuantity.getText().toString()) < Integer.valueOf(mGoods.getStock())) {
                        mTvQuantity.setText(String.valueOf(Integer.valueOf(mTvQuantity.getText().toString()) + 1));
                        mTvQuantity.setSelection(mTvQuantity.length());
                    }
                }

            }
            break;
            default:
                break;
        }
    }

    @Override
    public void getGoods(List<Goods> list) {
        if (!ObjectUtils.isEmpty(list)) {
            mGoods = list.get(0);
            setData();
        }
    }

    public class ImageHolderView extends Holder<String> {

        private ImageView mImageView;

        public ImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            mImageView = itemView.findViewById(R.id.image_view);
        }

        @Override
        public void updateUI(String data) {
            ImageLoaderHelper.loadImage1(mActivity, data, mImageView);
        }
    }
}
