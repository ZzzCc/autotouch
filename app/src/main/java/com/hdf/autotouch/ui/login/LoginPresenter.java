package com.hdf.autotouch.ui.login;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    LoginPresenter(LoginView view) {
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

    public void login(String areaCode, String phoneNumber, String verificationCode, String password) {
        mCompositeSubscription.add(mHttpManager.login(areaCode, phoneNumber, verificationCode, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<User>(this) {
                    @Override
                    public void onResponse(User user) {
                        mView.login(user);
                    }
                }));
    }
}
