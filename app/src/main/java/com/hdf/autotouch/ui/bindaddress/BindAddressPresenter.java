package com.hdf.autotouch.ui.bindaddress;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BindAddressPresenter extends BasePresenter<BindAddressView> {

    BindAddressPresenter(BindAddressView view) {
        super(view);
    }

    public void binding(String address) {
        mCompositeSubscription.add(mHttpManager.binding(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.binding();
                    }
                }));
    }

    public void removeBinding() {
        mCompositeSubscription.add(mHttpManager.removeBinding()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.removeBinding();
                    }
                }));
    }
}
