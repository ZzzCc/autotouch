package com.hdf.autotouch.ui.bill;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.MyBill;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BillPresenter extends BasePresenter<BillView> {

    BillPresenter(BillView view) {
        super(view);
    }

    //分页展示自己的账单
    public void getBill(int mac) {
        mCompositeSubscription.add(mHttpManager.getBill(mac + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<MyBill>>(this, false) {
                    @Override
                    public void onResponse(List<MyBill> billList) {
                        mView.getBill(billList);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getBill(null);
                    }
                }));
    }
}
