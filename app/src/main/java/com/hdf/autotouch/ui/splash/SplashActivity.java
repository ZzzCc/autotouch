package com.hdf.autotouch.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.ui.home.HomeActivity;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/12
 *     desc  : 欢迎页面
 * </pre>
 */
public class SplashActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
    }

    @Override
    public void doBusiness() {
        new Handler().postDelayed(() -> {
            HomeActivity.start(mActivity);
            finish();
        }, 1000);
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }
}
