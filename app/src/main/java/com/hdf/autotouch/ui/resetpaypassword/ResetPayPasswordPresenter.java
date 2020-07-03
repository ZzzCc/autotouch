package com.hdf.autotouch.ui.resetpaypassword;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ResetPayPasswordPresenter extends BasePresenter<ResetPayPasswordView> {

    ResetPayPasswordPresenter(ResetPayPasswordView view) {
        super(view);
    }

    //设置支付密码
    public void setPayPassword(String payPassword) {
        mCompositeSubscription.add(mHttpManager.setPayPassword(payPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.setPayPassword();
                    }
                }));
    }
}
