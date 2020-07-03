package com.hdf.autotouch.ui.bindaddress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.ui.scan.ScanActivity;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 绑定地址
 * </pre>
 */
public class BindAddressActivity extends BaseActivity<BindAddressPresenter> implements BindAddressView {

    @BindView(R.id.iv_scan)
    ImageView       mIvScan;
    @BindView(R.id.et_wallet_address)
    EditText        mEtWalletAddress;
    @BindView(R.id.btn_binding)
    QMUIRoundButton mBtnBinding;
    private String mAddress;

    public static void start(Context context, String address) {
        Intent intent = new Intent(context, BindAddressActivity.class);
        intent.putExtra("address", address);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mAddress = bundle.getString("address");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_bind_address;
    }

    @Override
    public void initView() {
        mPresenter = new BindAddressPresenter(this);

        applyClickListener(mIvScan, mBtnBinding);

        if (StringUtils.isEmpty(mAddress)) {
            setTitleText(R.string.bind_address);
            mIvScan.setVisibility(View.VISIBLE);
            mEtWalletAddress.setText("");
            mEtWalletAddress.setFocusable(true);
            mEtWalletAddress.setEnabled(true);
            mBtnBinding.setText(R.string.binding);
        } else {
            setTitleText(R.string.unbind_address);
            mIvScan.setVisibility(View.GONE);
            mEtWalletAddress.setText(mAddress);
            mEtWalletAddress.setFocusable(false);
            mEtWalletAddress.setEnabled(false);
            mBtnBinding.setText(R.string.remove_binding);
        }
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.iv_scan: {
                ScanActivity.start(this);
            }
            break;
            case R.id.btn_binding: {
                if (StringUtils.isEmpty(mAddress)) {
                    String address = mEtWalletAddress.getText().toString();
                    if (StringUtils.isEmpty(address)) {
                        ToastUtils.showShort(mEtWalletAddress.getHint());
                    } else if (!isEthAddress(address)) {
                        ToastUtils.showShort(R.string.wallet_address_format_error);
                    } else {
                        mPresenter.binding(address);
                    }
                } else {
                    mPresenter.removeBinding();
                }
            }
            break;
            default:
                break;
        }
    }

    private boolean isEthAddress(String address) {
        return address.startsWith("0x") && address.length() == 42;
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        if (msg.what == R.id.msg_scan_success) {
            mEtWalletAddress.setText((String) msg.obj);
        }
    }

    @Override
    public void binding() {
        sendMessage(R.id.msg_bind_address, mEtWalletAddress.getText().toString());
        finish();
    }

    @Override
    public void removeBinding() {
        sendMessage(R.id.msg_bind_address, "");
        finish();
    }
}
