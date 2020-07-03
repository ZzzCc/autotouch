package com.hdf.autotouch.ui.transfercoin;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransferCoinPresenter extends BasePresenter<TransferCoinView> {

    TransferCoinPresenter(TransferCoinView view) {
        super(view);
    }

    public void transferCoin(String areaCode, String phoneNumber, String coin, String amount, String password) {
        mCompositeSubscription.add(mHttpManager.transfer(areaCode, phoneNumber, coin, amount, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.transferCoin();
                    }
                }));
    }
}
