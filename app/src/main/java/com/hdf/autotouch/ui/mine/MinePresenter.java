package com.hdf.autotouch.ui.mine;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MinePresenter extends BasePresenter<MineView> {

    MinePresenter(MineView view) {
        super(view);
    }

    public void getWechat() {
        mCompositeSubscription.add(mHttpManager.getWechat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<String>(this) {
                    @Override
                    public void onResponse(String text) {
                        mView.getWechat(text);
                    }
                }));
    }
}
