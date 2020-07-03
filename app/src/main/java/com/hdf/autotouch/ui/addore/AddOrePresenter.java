package com.hdf.autotouch.ui.addore;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddOrePresenter extends BasePresenter<AddOreView> {

    AddOrePresenter(AddOreView view) {
        super(view);
    }

    //绑定矿机
    public void getWechat(String mac) {
        mCompositeSubscription.add(mHttpManager.getBinding(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.getBinding();
                    }
                }));
    }

    public void getUser() {
        mCompositeSubscription.add(mHttpManager.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<User>(this) {
                    @Override
                    public void onResponse(User user) {
                        mView.getUser(user);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getUser(null);
                    }
                }));
    }

}
