package com.hdf.autotouch.ui.recommend;

import com.blankj.utilcode.util.SizeUtils;
import com.hdf.autotouch.base.BasePresenter;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecommendPresenter extends BasePresenter<RecommendView> {

    RecommendPresenter(RecommendView view) {
        super(view);
    }


    //生成二维码
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
