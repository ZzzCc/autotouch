package com.hdf.autotouch.ui.aboutus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.VersionCode;
import com.hdf.autotouch.ui.version.VersionUpdateActivity;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 关于我们
 * </pre>
 */
public class AboutUsActivity extends BaseActivity<AboutUsPresenter> implements AboutUsView {

    @BindView(R.id.tv_code)
    TextView        mTvCode;
    @BindView(R.id.btn_code)
    QMUIRoundButton mBtnCode;
    private VersionCode mVersionCode1;

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
        mPresenter = new AboutUsPresenter(this);
        applyClickListener(mBtnCode);

        setTitleText(R.string.about_us);
        mTvCode.setText("版本号：" + AppUtils.getAppVersionName());
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                mPresenter.getVersionCode();
                break;
            default:
                break;
        }
    }

    @Override
    public void getVersionCode(VersionCode versionCode) {
        mVersionCode1 = versionCode;
        //status  0,不强制 1，强制
        if (!AppUtils.getAppVersionName().equals(versionCode.getVersionNum())) {
            VersionUpdateActivity.start(this, versionCode);
        } else {
            ToastUtils.showShort("已是最新版本");
        }

//        DialogHelper.showVersionCodeDialog(mVersionCode1.getContent(), mVersionCode1.getStatus(), new DialogHelper.OnVersionListener() {
//            @Override
//            public void onCancel() {
//            }
//
//            @Override
//            public void onConfirm() {
//                try {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mVersionCode1.getVersionUrl()));
//                    startActivity(intent);
//                } catch (ActivityNotFoundException a) {
//                    a.getMessage();
//                }
//            }
//        });
    }
}
