package com.hdf.autotouch.ui.address;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.Address;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddressPresenter extends BasePresenter<AddressView> {

    AddressPresenter(AddressView view) {
        super(view);
    }

    public void getAddress(int page) {
        mCompositeSubscription.add(mHttpManager.getAddress(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<Address>>(this, false) {
                    @Override
                    public void onResponse(List<Address> list) {
                        mView.getAddress(list);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getAddress(null);
                    }
                }));
    }

    public void addAddress(String consignee, String areaCode, String phone) {
        mCompositeSubscription.add(mHttpManager.addAddress(consignee, areaCode, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.updateAddress();
                    }
                }));
    }

    public void editAddress(String id, String consignee, String areaCode, String phone) {
        mCompositeSubscription.add(mHttpManager.editAddress(id, consignee, areaCode, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.updateAddress();
                    }
                }));
    }

    public void deleteAddress(String id) {
        mCompositeSubscription.add(mHttpManager.deleteAddress(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.updateAddress();
                    }
                }));
    }
}
