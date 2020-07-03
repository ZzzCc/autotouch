package com.hdf.autotouch.ui.register;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    RegisterPresenter(RegisterView view) {
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

    public void register(String areaCode, String phoneNumber, String verificationCode, String password, String invitationCode) {
        mCompositeSubscription.add(mHttpManager.register(areaCode, phoneNumber, verificationCode, password, invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<User>(this) {
                    @Override
                    public void onResponse(User user) {
                        mView.register(user);
                    }
                }));
    }
}
