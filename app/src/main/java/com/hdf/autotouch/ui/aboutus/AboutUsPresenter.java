package com.hdf.autotouch.ui.aboutus;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.VersionCode;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AboutUsPresenter extends BasePresenter<AboutUsView> {

    AboutUsPresenter(AboutUsView view) {
        super(view);
    }

    //版本号
    public void getVersionCode() {
        mCompositeSubscription.add(mHttpManager.getVersionCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<VersionCode>(this) {
                    @Override
                    public void onResponse(VersionCode versionCode) {
                        mView.getVersionCode(versionCode);
                    }
                }));
    }
}
