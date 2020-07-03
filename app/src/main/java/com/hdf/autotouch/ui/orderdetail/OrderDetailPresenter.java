package com.hdf.autotouch.ui.orderdetail;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.config.HttpConfig;
import com.hdf.autotouch.entity.Order;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderDetailPresenter extends BasePresenter<OrderDetailView> {

    OrderDetailPresenter(OrderDetailView view) {
        super(view);
    }

    public void getOrderDetail(String orderId) {
        mCompositeSubscription.add(mHttpManager.getOrderDetail(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Order>(this) {
                    @Override
                    public void onResponse(Order order) {
                        mView.getOrderDetail(order);
                    }
                }));
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

    public void cancelOrder(String id) {
        mCompositeSubscription.add(mHttpManager.cancelOrder(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.cancelOrder();
                    }
                }));
    }

    //一键绑定
    public void getAllBinding(String orderId) {
        mCompositeSubscription.add(mHttpManager.getAllBinding(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Integer>(this) {
                    @Override
                    public void onResponse(Integer num) {
                        mView.getAllBinding(num);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getAllBinding(-1);
                    }
                }));
    }


}
