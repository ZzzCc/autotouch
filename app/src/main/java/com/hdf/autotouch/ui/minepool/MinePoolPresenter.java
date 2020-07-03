package com.hdf.autotouch.ui.minepool;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.MinePool;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MinePoolPresenter extends BasePresenter<MinePoolView> {

    MinePoolPresenter(MinePoolView view) {
        super(view);
    }

    public void getMinePoolData(int page) {
        mCompositeSubscription.add(mHttpManager.getMinePoolData(page + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<MinePool>(this, false) {
                    @Override
                    public void onResponse(MinePool minePool) {
                        mView.getMinePoolData(minePool);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getMinePoolData(null);
                    }
                }));
    }
}
