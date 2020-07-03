package com.hdf.autotouch.base;

import com.blankj.utilcode.util.ObjectUtils;
import com.hdf.autotouch.http.HttpManager;
import com.hdf.autotouch.util.ProgressDialogUtils;

import rx.subscriptions.CompositeSubscription;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : BasePresenter
 * </pre>
 */
public class BasePresenter<V extends IView> implements IPresenter {

    public    CompositeSubscription mCompositeSubscription;
    protected V                     mView;
    protected HttpManager           mHttpManager;
    private   ProgressDialogUtils   mProgressDialogUtils;

    public BasePresenter(V view) {
        mView = view;
        onCreate();
    }

    @Override
    public void onCreate() {
        mHttpManager = new HttpManager();
        mCompositeSubscription = new CompositeSubscription();
        mProgressDialogUtils = new ProgressDialogUtils();
    }

    public void showLoading() {
        mProgressDialogUtils.showProgressDialog();
    }

    public void showSuccess(String message) {
        mProgressDialogUtils.showProgressSuccess(message);
    }

    public void showFail(String message) {
        mProgressDialogUtils.showProgressFail(message);
    }

    public void dismissLoading() {
        mProgressDialogUtils.dismissProgressDialog();
    }

    @Override
    public void onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
        if (!ObjectUtils.isEmpty(mProgressDialogUtils)) {
            mProgressDialogUtils.dismissProgressDialog();
            mProgressDialogUtils = null;
        }
    }

}
