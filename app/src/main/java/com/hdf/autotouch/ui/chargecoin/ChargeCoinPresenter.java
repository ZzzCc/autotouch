package com.hdf.autotouch.ui.chargecoin;

import com.blankj.utilcode.util.SizeUtils;
import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.HttpCallback;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChargeCoinPresenter extends BasePresenter<ChargeCoinView> {

    ChargeCoinPresenter(ChargeCoinView view) {
        super(view);
    }

    public void getAddress() {
        mCompositeSubscription.add(mHttpManager.getChargeAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<String>(this) {
                    @Override
                    public void onResponse(String str) {
                        mView.getAddress(str);
                    }
                }));
    }

    public void createQrCode(String s) {
        mCompositeSubscription.add(Observable.just(s)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(this::showLoading)
                .observeOn(Schedulers.io())
                .map(s1 -> QRCodeEncoder.syncEncodeQRCode(s1, SizeUtils.dp2px(180)))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(this::dismissLoading)
                .doOnError(throwable -> dismissLoading())
                .subscribe(bitmap -> mView.createQrCode(bitmap)));
    }
}
