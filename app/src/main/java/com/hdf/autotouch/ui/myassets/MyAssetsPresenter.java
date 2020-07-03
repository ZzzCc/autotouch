package com.hdf.autotouch.ui.myassets;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.MyAssets;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyAssetsPresenter extends BasePresenter<MyAssetsView> {

    MyAssetsPresenter(MyAssetsView view) {
        super(view);
    }

    public void getTransaction(int page) {
        mCompositeSubscription.add(mHttpManager.getTransaction(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<MyAssets>(this, false) {
                    @Override
                    public void onResponse(MyAssets myAssets) {
                        mView.getTransaction(myAssets);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getTransaction(null);
                    }
                }));
    }
}
