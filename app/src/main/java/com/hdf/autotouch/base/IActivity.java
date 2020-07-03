package com.hdf.autotouch.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : IActivity
 * </pre>
 */
interface IActivity {

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    void initData(@Nullable Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    int bindLayout();

    /**
     * 设置根布局
     *
     * @param layoutId 布局 id
     */
    void setRootLayout(@LayoutRes int layoutId);

    /**
     * 初始化 view
     */
    void initView();

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * 点击事件
     *
     * @param view 视图
     */
    void onViewClick(@NonNull View view);

}
