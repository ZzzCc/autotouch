package com.hdf.autotouch.ui.collecttransferdetail;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.CollectTransferDetail;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectTransferDetailPresenter extends BasePresenter<CollectTransferDetailView> {

    CollectTransferDetailPresenter(CollectTransferDetailView view) {
        super(view);
    }

    public void getCollectTransferDetail(int page) {
        mCompositeSubscription.add(mHttpManager.getCollectTransferDetail(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<CollectTransferDetail>>(this, false) {
                    @Override
                    public void onResponse(List<CollectTransferDetail> list) {
                        mView.getCollectTransferDetail(list);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getCollectTransferDetail(null);
                    }
                }));
    }
}
