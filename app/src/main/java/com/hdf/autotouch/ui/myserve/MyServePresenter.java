package com.hdf.autotouch.ui.myserve;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.MyMac;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyServePresenter extends BasePresenter<MyServeView> {

    MyServePresenter(MyServeView view) {
        super(view);
    }

    //我的服务器
    public void getmyMac(int pageNum) {
        mCompositeSubscription.add(mHttpManager.getmyMac(pageNum + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<MyMac>(this, false) {
                    @Override
                    public void onResponse(MyMac myMac) {
                        mView.getmyMac(myMac);
                    }
                }));
    }

    //补缴上月托管费
    public void getDeductMac(String mac, String payPassword) {
        mCompositeSubscription.add(mHttpManager.getDeductMac(mac, payPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        mView.getDeductMac();
                    }
                }));
    }
}
