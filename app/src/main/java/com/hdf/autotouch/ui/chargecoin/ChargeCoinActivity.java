package com.hdf.autotouch.ui.chargecoin;

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
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.util.ClipboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 充币
 * </pre>
 */
public class ChargeCoinActivity extends BaseActivity<ChargeCoinPresenter> implements ChargeCoinView {

    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_address)
    TextView  mTvAddress;
    @BindView(R.id.tv_address1)
    TextView  mTvAddress1;
    @BindView(R.id.view_bg_top_bar)
    View      setTitleText;
    @BindView(R.id.tv_toolbar_title)
    TextView  mTvToolbarTitle;

    public static void start(Context context) {
        Intent intent = new Intent(context, ChargeCoinActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_charge_coin;
    }

    @Override
    public void initView() {
        mPresenter = new ChargeCoinPresenter(this);
        setTitleText(R.string.charge_coin);
        applyLightMode(false);

        applyClickListener(mTvAddress1);
    }

    @Override
    public void doBusiness() {
        mPresenter.getAddress();
    }

    @Override
    public void onViewClick(@NonNull View view) {
        if (view.getId() == R.id.tv_address1) {
            ClipboardUtils.copyText(mTvAddress.getText().toString());
        }
    }

    @Override
    public void getAddress(String str) {
        mPresenter.createQrCode(str);
        mTvAddress.setText(str);
    }

    @Override
    public void createQrCode(Bitmap bitmap) {
        mIvQrCode.setImageBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
