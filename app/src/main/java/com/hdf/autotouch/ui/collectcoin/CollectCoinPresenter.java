package com.hdf.autotouch.ui.collectcoin;

import com.blankj.utilcode.util.SizeUtils;
import com.hdf.autotouch.base.BasePresenter;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectCoinPresenter extends BasePresenter<CollectCoinView> {

    CollectCoinPresenter(CollectCoinView view) {
        super(view);
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
