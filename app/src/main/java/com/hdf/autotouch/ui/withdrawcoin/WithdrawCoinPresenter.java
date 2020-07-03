package com.hdf.autotouch.ui.withdrawcoin;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.WithdrawAddress;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WithdrawCoinPresenter extends BasePresenter<WithdrawCoinView> {

    WithdrawCoinPresenter(WithdrawCoinView view) {
        super(view);
    }

    public void getBindingAddress() {
        mCompositeSubscription.add(mHttpManager.getWithdrawAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<WithdrawAddress>(this) {
                    @Override
                    public void onResponse(WithdrawAddress withdrawAddress) {
                        mView.getBindingAddress(withdrawAddress);
                    }
                }));
    }

    public void withdrawCoin(String address, String amount, String remark, String password) {
        mCompositeSubscription.add(mHttpManager.withdraw(address, amount, remark, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.getWithdrawCoin();
                    }
                }));
    }
}
