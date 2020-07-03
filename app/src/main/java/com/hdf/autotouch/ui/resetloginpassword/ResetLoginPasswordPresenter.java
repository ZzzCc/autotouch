package com.hdf.autotouch.ui.resetloginpassword;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ResetLoginPasswordPresenter extends BasePresenter<ResetLoginPasswordView> {

    ResetLoginPasswordPresenter(ResetLoginPasswordView view) {
        super(view);
    }

    //发送验证码
    public void getCode(String areaCode, String phoneNumber, String ticket, String randomStr) {
        mCompositeSubscription.add(mHttpManager.getCode(areaCode, phoneNumber, ticket, randomStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                    }
                }));
    }

    //发送验证码
    public void getCheckVerifyCode(String account, String prefix, String verifyCode) {
        mCompositeSubscription.add(mHttpManager.getCheckVerifyCode(account, prefix, verifyCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.getCheckVerifyCode();
                    }
                }));
    }

    //发送验证码
    public void getChangePassword(String youngPassword) {
        mCompositeSubscription.add(mHttpManager.getChangePassword(youngPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.getChangePassword();
                    }
                }));
    }

}
