package com.hdf.autotouch.ui.payment;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.config.HttpConfig;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PaymentPresenter extends BasePresenter<PaymentView> {

    PaymentPresenter(PaymentView view) {
        super(view);
    }

    public void payOrder(String id, String password) {
        mCompositeSubscription.add(mHttpManager.payOrder(id, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Order>(this) {
                    @Override
                    public void onResponse(Order order) {
                        mView.payOrder(order);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        if (throwable.code == HttpConfig.ErrorCode.PAYMENT_ERROR) {
                            mView.payOrderError();
                        }
                    }
                }));
    }
}
