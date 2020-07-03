package com.hdf.autotouch.ui.transactiondetail;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransactionDetailPresenter extends BasePresenter<TransactionDetailView> {

    TransactionDetailPresenter(TransactionDetailView view) {
        super(view);
    }

    public void revocation(String id) {
        mCompositeSubscription.add(mHttpManager.revocation(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.revocation();
                    }
                }));
    }
}
