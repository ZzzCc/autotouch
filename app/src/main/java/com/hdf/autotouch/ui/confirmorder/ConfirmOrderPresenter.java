package com.hdf.autotouch.ui.confirmorder;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConfirmOrderPresenter extends BasePresenter<ConfirmOrderView> {

    ConfirmOrderPresenter(ConfirmOrderView view) {
        super(view);
    }

    public void submitOrder(String goodsId, String addressId, String num, String amount, String message) {
        mCompositeSubscription.add(mHttpManager.submitOrder(goodsId, addressId, num, amount, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Order>(this) {
                    @Override
                    public void onResponse(Order order) {
                        mView.submitOrder(order);
                    }
                }));
    }
}
