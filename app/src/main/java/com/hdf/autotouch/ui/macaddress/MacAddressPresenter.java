package com.hdf.autotouch.ui.macaddress;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.MyMill;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MacAddressPresenter extends BasePresenter<MacAddressView> {

    MacAddressPresenter(MacAddressView view) {
        super(view);
    }

    //mac地址
    public void getMill(int pageNum,int status) {
        mCompositeSubscription.add(mHttpManager.getMill(pageNum + "",status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<MyMill>>(this, false) {
                    @Override
                    public void onResponse(List<MyMill> millList) {
                        mView.getMill(millList);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getMill(null);
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
                }));
    }
}
