package com.hdf.autotouch.ui.addore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.ui.scan.ScanActivity;
import com.hdf.autotouch.util.SPManager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 矿池添加
 * </pre>
 */
public class AddOreActivity extends BaseActivity<AddOrePresenter> implements AddOreView {

    @BindView(R.id.tv_title)
    TextView        mTvTitle;
    @BindView(R.id.tv_content)
    TextView        mTvContent;
    @BindView(R.id.ed_serve_binding)
    EditText        mEdServeBinding;
    @BindView(R.id.img_saoyisoa)
    ImageView       mImgSaoyisoa;
    @BindView(R.id.btn_cancel)
    QMUIRoundButton mBtnCancel;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton mBtnConfirm;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddOreActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_add_ore;
    }

    @Override
    public void initView() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.AnimationFade;
        getWindow().setAttributes(params);

        mPresenter = new AddOrePresenter(this);

        applyClickListener(mImgSaoyisoa, mBtnCancel, mBtnConfirm);
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.img_saoyisoa:
                ScanActivity.start(this);
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_confirm:
                mPresenter.getWechat(mEdServeBinding.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        if (msg.what == R.id.msg_scan_success) {
            mEdServeBinding.setText((String) msg.obj);
        }
    }

    @Override
    public void getBinding() {
        mPresenter.getUser();
    }

    @Override
    public void getUser(User user) {
        if (!ObjectUtils.isEmpty(user)
                && !StringUtils.equals(String.valueOf(user.getLevel()), SPManager.getLevel())) {
            SPManager.setLevel(String.valueOf(user.getLevel()));
            sendMessage(R.id.msg_update_mine, null);
        }
        sendMessage(R.id.msg_update, null);
        ToastUtils.showShort("添加成功");
        finish();
        sendMessage(R.id.msg_update_order, null);
    }
}
