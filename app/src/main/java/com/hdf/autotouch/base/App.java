package com.hdf.autotouch.base;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.hdf.autotouch.BuildConfig;
import com.hdf.autotouch.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.squareup.leakcanary.LeakCanary;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : App
 * </pre>
 */
public class App extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            layout.setEnableAutoLoadMore(true);
            layout.setEnableHeaderTranslationContent(false);
            layout.setEnableLoadMoreWhenContentNotFull(true);
            layout.setEnableOverScrollBounce(true);
            layout.setEnableOverScrollDrag(true);
            layout.setEnableScrollContentWhenRefreshed(false);
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new MaterialHeader(context));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        initLeakCanary();
        initLog();
        initCrash();
        ClassicsFooter.REFRESH_FOOTER_PULLING = StringUtils.getString(R.string.pulling);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = StringUtils.getString(R.string.release);
        ClassicsFooter.REFRESH_FOOTER_LOADING = StringUtils.getString(R.string.loading);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = StringUtils.getString(R.string.refreshing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = StringUtils.getString(R.string.finish);
        ClassicsFooter.REFRESH_FOOTER_FAILED = StringUtils.getString(R.string.failed);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = StringUtils.getString(R.string.nothing);
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

    private void initLog() {
        LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)
                .setConsoleSwitch(BuildConfig.DEBUG);
    }

    private void initCrash() {
        CrashUtils.init((crashInfo, e) -> {
            LogUtils.e(crashInfo);
            AppUtils.relaunchApp();
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
