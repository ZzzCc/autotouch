package com.hdf.autotouch.ui.collectcoin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.entity.QRCodeData;
import com.hdf.autotouch.ui.collecttransferdetail.CollectTransferDetailActivity;
import com.hdf.autotouch.util.SPManager;
import com.hdf.autotouch.util.ShareUtils;
import com.hdf.autotouch.widget.WindowInsetConstraintLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 收币
 * </pre>
 */
public class CollectCoinActivity extends BaseActivity<CollectCoinPresenter> implements CollectCoinView {

    @BindView(R.id.layout_root)
    WindowInsetConstraintLayout mLayoutRoot;
    @BindView(R.id.iv_qr_code)
    ImageView                   mIvQrCode;
    @BindView(R.id.btn_save_image)
    QMUIRoundButton             mBtnSaveImage;
    @BindView(R.id.tv_collect_transfer_detail)
    TextView                    mTvCollectTransferDetail;

    public static void start(Context context) {
        Intent intent = new Intent(context, CollectCoinActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_collect_coin;
    }

    @Override
    public void initView() {
        mPresenter = new CollectCoinPresenter(this);
        setTitleText(R.string.collect_coin);
        applyLightMode(false);
        applyClickListener(mBtnSaveImage, mTvCollectTransferDetail);
    }

    @Override
    public void doBusiness() {
        QRCodeData qrCodeData = new QRCodeData(Constant.QRCODE_TYPE_COLLECT_COIN, StringUtils.isEmpty(SPManager.getAreaCode()) ?
                SPManager.getUserName() : SPManager.getAreaCode() + " " + SPManager.getUserName());
        mPresenter.createQrCode(GsonUtils.toJson(qrCodeData));
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_save_image: {
                ShareUtils.save(this, mLayoutRoot);
            }
            break;
            case R.id.tv_collect_transfer_detail: {
                CollectTransferDetailActivity.start(this);
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void createQrCode(Bitmap bitmap) {
        mIvQrCode.setImageBitmap(bitmap);
    }
}
