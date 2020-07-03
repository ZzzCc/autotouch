package com.hdf.autotouch.ui.goodsdetail;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.http.HttpCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailView> {

    GoodsDetailPresenter(GoodsDetailView view) {
        super(view);
    }

    public void getGoods() {
        mCompositeSubscription.add(mHttpManager.getGoods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<Goods>>(this) {
                    @Override
                    public void onResponse(List<Goods> list) {
                        mView.getGoods(list);
                    }
                }));
    }
}
