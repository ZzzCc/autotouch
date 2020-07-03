package com.hdf.autotouch.ui.exchange;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.Exchange;
import com.hdf.autotouch.entity.ExchangeRate;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ExchangePresenter extends BasePresenter<ExchangeView> {

    ExchangePresenter(ExchangeView view) {
        super(view);
    }

    public void getExchangeRate(String inCoin, String outCoin) {
        mCompositeSubscription.add(mHttpManager.getExchangeRate(inCoin, outCoin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<ExchangeRate>(this) {
                    @Override
                    public void onResponse(ExchangeRate exchangeRate) {
                        mView.getExchangeRate(exchangeRate);
                    }
                }));
    }

    public void exchange(String inCoin, String outCoin, String amount, String payPassword) {
        mCompositeSubscription.add(mHttpManager.exchange(inCoin, outCoin, amount, payPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Exchange>(this) {
                    @Override
                    public void onResponse(Exchange exchange) {
                        mView.exchange(exchange);
                    }
                }));
    }
}
