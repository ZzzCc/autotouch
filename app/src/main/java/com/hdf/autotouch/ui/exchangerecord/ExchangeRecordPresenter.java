package com.hdf.autotouch.ui.exchangerecord;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.Exchange;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ExchangeRecordPresenter extends BasePresenter<ExchangeRecordView> {

    ExchangeRecordPresenter(ExchangeRecordView view) {
        super(view);
    }

    public void getExchangeRecord(int pageNum) {
        mCompositeSubscription.add(mHttpManager.getExchangeRecord(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<Exchange>>(this, false) {
                    @Override
                    public void onResponse(List<Exchange> list) {
                        mView.getExchangeRecord(list);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getExchangeRecord(null);
                    }
                }));
    }
}
