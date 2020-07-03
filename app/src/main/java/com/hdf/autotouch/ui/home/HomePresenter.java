package com.hdf.autotouch.ui.home;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.entity.HomeData;
import com.hdf.autotouch.entity.Market;
import com.hdf.autotouch.entity.MyAssets;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.entity.VersionCode;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<HomeView> {

    HomePresenter(HomeView view) {
        super(view);
    }

    public void getHomeData() {
        mCompositeSubscription.add(mHttpManager.getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<HomeData>(this, false) {
                    @Override
                    public void onResponse(HomeData homeData) {
                        mView.getHomeData(homeData);
                    }
                }));
    }

    public void getMarket(int page) {
        mCompositeSubscription.add(mHttpManager.getMarket(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<Market>>(this, false) {
                    @Override
                    public void onResponse(List<Market> list) {
                        mView.getMarket(list);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getMarket(null);
                    }
                }));
    }

    public void getMallPic() {
        mCompositeSubscription.add(mHttpManager.getMallPic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<String>>(this, false) {
                    @Override
                    public void onResponse(List<String> list) {
                        mView.getMallPic(list);
                    }
                }));
    }

    public void getGoods() {
        mCompositeSubscription.add(mHttpManager.getGoods()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<Goods>>(this, false) {
                    @Override
                    public void onResponse(List<Goods> list) {
                        mView.getGoods(list);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getGoods(null);
                    }
                }));
    }

    public void getUser() {
        mCompositeSubscription.add(mHttpManager.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<User>(this, false) {
                    @Override
                    public void onResponse(User user) {
                        mView.getUser(user);
                    }
                }));
    }

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
