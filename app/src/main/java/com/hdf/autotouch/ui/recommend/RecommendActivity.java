package com.hdf.autotouch.ui.recommend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.util.ClipboardUtils;
import com.hdf.autotouch.util.SPManager;
import com.hdf.autotouch.util.ShareUtils;
import com.hdf.autotouch.widget.WindowInsetConstraintLayout;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 推荐有奖
 * </pre>
 */
public class RecommendActivity extends BaseActivity<RecommendPresenter> implements RecommendView {

    @BindView(R.id.tv_Invitation_code)
    TextView                    mTvInvitationCode;
    @BindView(R.id.tv_copy)
    TextView                    mTvCopy;
    @BindView(R.id.img_qr_code)
    ImageView                   mImgQrCode;
    @BindView(R.id.img_share)
    ImageView                   mImgShare;
    @BindView(R.id.win_layout)
    WindowInsetConstraintLayout mWinLayout;

    public static void start(Context context) {
        Intent intent = new Intent(context, RecommendActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_recommend;
    }

    @Override
    public void initView() {
        mPresenter = new RecommendPresenter(this);
        applyLightMode(false);
        mViewBgTopBar.setBackgroundColor(Color.TRANSPARENT);
        setTitleText(R.string.recommend);
        applyClickListener(mTvCopy, mImgShare);

        mTvInvitationCode.setText(SPManager.getInviteCode());

    }

    @Override
    public void doBusiness() {
        mPresenter.createQrCode(Constant.URL_RECOMMEND + SPManager.getInviteCode());
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.img_share://分享
                ShareUtils.shareImage(this, mWinLayout);
                break;
            case R.id.tv_copy://点击复制
                ClipboardUtils.copyText(mTvInvitationCode.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void createQrCode(Bitmap bitmap) {
        mImgQrCode.setImageBitmap(bitmap);
    }
}
