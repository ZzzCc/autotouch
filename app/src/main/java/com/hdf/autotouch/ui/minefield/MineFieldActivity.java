package com.hdf.autotouch.ui.minefield;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.MineField;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.EncryptUtils;
import com.hdf.autotouch.util.SPManager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 矿场
 * </pre>
 */
public class MineFieldActivity extends BaseActivity<MineFieldPresenter> implements MineFieldView {

    @BindView(R.id.tv_percent)
    TextView        mTvPercent;
    @BindView(R.id.btn_purchase_now)
    QMUIRoundButton mBtnPurchaseNow;
    private MineField mMineField;

    public static void start(Context context) {
        Intent intent = new Intent(context, MineFieldActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mine_field;
    }

    @Override
    public void initView() {
        mPresenter = new MineFieldPresenter(this);

        mViewBgTopBar.setBackgroundColor(Color.TRANSPARENT);
        setTitleText(R.string.mine_field);
        applyLightMode(false);
        applyClickListener(mBtnPurchaseNow);

        mTvPercent.setText(String.format(getString(R.string.percent), "0"));
    }

    @Override
    public void doBusiness() {
        mPresenter.getMineField();
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_purchase_now: {
                if (!ObjectUtils.isEmpty(mMineField)) {
                    if (havePayPassword()) {
                        DialogHelper.showPurchaseMineFieldDialog(mMineField.getAmount(),
                                String.valueOf(Double.valueOf(mMineField.getMineral()) * 100),
                                () -> PayPasswordActivity.start(this, 0));
                    }
                }
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        if (msg.what == R.id.msg_input_pay_password) {
            String password = (String) msg.obj;
            mPresenter.purchase(EncryptUtils.encryptSha256(password));
        }
    }

    @Override
    public void getMineField(MineField mineField) {
        mMineField = mineField;
        mTvPercent.setText(String.format(getString(R.string.percent), String.valueOf(Double.valueOf(mMineField.getMineral()) * 100)));
        if (Integer.valueOf(SPManager.getLevel()) < Integer.valueOf(mMineField.getLevel())) {
            mBtnPurchaseNow.setEnabled(true);
        } else {
            mBtnPurchaseNow.setEnabled(false);
        }
    }

    @Override
    public void getUser(User user) {
        ToastUtils.showShort(getString(R.string.purchase_success));
        if (!ObjectUtils.isEmpty(user)
                && !StringUtils.equals(String.valueOf(user.getLevel()), SPManager.getLevel())) {
            SPManager.setLevel(String.valueOf(user.getLevel()));
            sendMessage(R.id.msg_update_mine, null);
        }
        if (Integer.valueOf(SPManager.getLevel()) < Integer.valueOf(mMineField.getLevel())) {
            mBtnPurchaseNow.setEnabled(true);
        } else {
            mBtnPurchaseNow.setEnabled(false);
        }
    }
}
