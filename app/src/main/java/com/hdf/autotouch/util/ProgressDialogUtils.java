package com.hdf.autotouch.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : ProgressDialogUtils
 * </pre>
 */
public class ProgressDialogUtils {

    private static final long        TIME_DISMISS_DEFAULT = 1000;
    private              Dialog      mDialog;
    private              View        mDialogContentView;
    private              TextView    mTvLoadText;
    private              ImageView   mIvLoadImage;
    private              ProgressBar mPbLoadProgress;

    public ProgressDialogUtils() {
        this(ActivityUtils.getTopActivity(), R.style.TransparentDialogStyle);
    }

    @SuppressLint("InflateParams")
    private ProgressDialogUtils(Context context, int style) {
        mDialog = new Dialog(context, style);
        mDialogContentView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        mTvLoadText = mDialogContentView.findViewById(R.id.tv_load_text);
        mIvLoadImage = mDialogContentView.findViewById(R.id.iv_load_image);
        mPbLoadProgress = mDialogContentView.findViewById(R.id.pb_load_progress);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.setContentView(mDialogContentView);
        Window window = mDialog.getWindow();
        if (null != window) {
            window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        }
    }

    /**
     * 显示加载的ProgressDialog
     */
    public void showProgressDialog() {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mPbLoadProgress.setVisibility(View.VISIBLE);
            mIvLoadImage.setVisibility(View.GONE);
            mTvLoadText.setVisibility(View.GONE);
            mDialog.show();
        }
    }

    /**
     * 显示有加载文字ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param text 需要显示的文字
     */
    public void showProgressDialogWithText(String text) {
        if (TextUtils.isEmpty(text)) {
            showProgressDialog();
        } else {
            if (mDialog != null) {
                if (mDialog.isShowing()) {
                    mTvLoadText.setText(text);
                } else {
                    mPbLoadProgress.setVisibility(View.VISIBLE);
                    mIvLoadImage.setVisibility(View.GONE);
                    mTvLoadText.setText(text);
                    mTvLoadText.setVisibility(View.VISIBLE);
                    mDialog.show();
                }
            }
        }
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载成功需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    public void showProgressSuccess(String message, long time) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mPbLoadProgress.setVisibility(View.GONE);
            mIvLoadImage.setBackgroundResource(R.drawable.ic_load_success);
            mIvLoadImage.setVisibility(View.VISIBLE);
            mTvLoadText.setText(message);
            mTvLoadText.setVisibility(View.VISIBLE);
            mDialog.show();
            mDialogContentView.postDelayed(() -> mDialog.dismiss(), time);
        }
    }

    /**
     * 显示加载成功的ProgressDialog，文字显示在ProgressDialog的下面
     * ProgressDialog默认消失时间为1秒(1000毫秒)
     *
     * @param message 加载成功需要显示的文字
     */
    public void showProgressSuccess(String message) {
        showProgressSuccess(message, TIME_DISMISS_DEFAULT);
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     *
     * @param message 加载失败需要显示的文字
     * @param time    需要显示的时间长度(以毫秒为单位)
     */
    public void showProgressFail(String message, long time) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mPbLoadProgress.setVisibility(View.GONE);
            mIvLoadImage.setBackgroundResource(R.drawable.ic_load_fail);
            mIvLoadImage.setVisibility(View.VISIBLE);
            mTvLoadText.setText(message);
            mTvLoadText.setVisibility(View.VISIBLE);
            mDialog.show();
            mDialogContentView.postDelayed(() -> mDialog.dismiss(), time);
        }
    }

    /**
     * 显示加载失败的ProgressDialog，文字显示在ProgressDialog的下面
     * ProgressDialog默认消失时间为1秒(1000毫秒)
     *
     * @param message 加载成功需要显示的文字
     */
    public void showProgressFail(String message) {
        showProgressFail(message, TIME_DISMISS_DEFAULT);
    }

    /**
     * 隐藏加载的ProgressDialog
     */
    public void dismissProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
