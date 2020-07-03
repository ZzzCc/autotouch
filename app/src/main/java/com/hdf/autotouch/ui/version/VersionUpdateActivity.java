package com.hdf.autotouch.ui.version;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.VersionCode;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class VersionUpdateActivity extends BaseActivity<VersionUpdatePresenter> implements VersionUpdateView {


    @BindView(R.id.tv_title)
    TextView        mTvTitle;
    @BindView(R.id.tv_content)
    TextView        mTvContent;
    @BindView(R.id.view_divider)
    View            mViewDivider;
    @BindView(R.id.btn_cancel)
    QMUIRoundButton mBtnCancel;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton mBtnConfirm;
    @BindView(R.id.btn_constraint_confirm)
    QMUIRoundButton mBtnConstraintConfirm;
    @BindView(R.id.tv_downloading)
    TextView        mTvDownloading;
    @BindView(R.id.progress_bar)
    ProgressBar     mProgressBar;
    private VersionCode mVersion;

    public static void start(Context context, VersionCode version) {
        Intent intent = new Intent(context, VersionUpdateActivity.class);
        intent.putExtra("version", version);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mVersion = bundle.getParcelable("version");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_version_update;
    }

    @Override
    public void initView() {
        mPresenter = new VersionUpdatePresenter(this);

        mTvContent.setText(mVersion.getContent());

        if (StringUtils.equals(mVersion.getStatus() + "", "0")) {
            mBtnCancel.setVisibility(View.VISIBLE);
            mBtnConfirm.setVisibility(View.VISIBLE);
            mBtnConstraintConfirm.setVisibility(View.GONE);
        } else {
            mBtnCancel.setVisibility(View.GONE);
            mBtnConfirm.setVisibility(View.GONE);
            mBtnConstraintConfirm.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onViewClick(@NonNull View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @OnClick({R.id.btn_cancel, R.id.btn_confirm, R.id.btn_constraint_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel: {
                finish();
            }
            break;
            case R.id.btn_confirm:
            case R.id.btn_constraint_confirm: {
                mPresenter.downloadFile(mVersion.getVersionUrl());
                mBtnCancel.setVisibility(View.GONE);
                mBtnConfirm.setVisibility(View.GONE);
                mBtnConstraintConfirm.setVisibility(View.GONE);
                mTvDownloading.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void updateProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    @Override
    public void downloadFinish() {
        ToastUtils.showShort("下载完成");
        AppUtils.installApp(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ipfs.apk"));
    }

    @Override
    public void downloadFailure() {
        ToastUtils.showShort("下载失败");
        if (StringUtils.equals(mVersion.getStatus() + "", "0")) {
            mBtnCancel.setVisibility(View.VISIBLE);
            mBtnConfirm.setVisibility(View.VISIBLE);
            mBtnConstraintConfirm.setVisibility(View.GONE);
        } else {
            mBtnCancel.setVisibility(View.GONE);
            mBtnConfirm.setVisibility(View.GONE);
            mBtnConstraintConfirm.setVisibility(View.VISIBLE);
        }
        mTvDownloading.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }
}
