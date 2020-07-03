package com.hdf.autotouch.ui.forgetpassword;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordView> {

    ForgetPasswordPresenter(ForgetPasswordView view) {
        super(view);
    }

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

    public void forgetPassword(String areaCode, String phoneNumber, String verificationCode, String password) {
        mCompositeSubscription.add(mHttpManager.forgetPassword(areaCode, phoneNumber, verificationCode, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.forgetPassword();
                    }
                }));
    }
}
