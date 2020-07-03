package com.hdf.autotouch.ui.minefield;

import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.MineField;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MineFieldPresenter extends BasePresenter<MineFieldView> {

    MineFieldPresenter(MineFieldView view) {
        super(view);
    }

    public void getMineField() {
        mCompositeSubscription.add(mHttpManager.getMineField()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<MineField>(this) {
                    @Override
                    public void onResponse(MineField mineField) {
                        mView.getMineField(mineField);
                    }
                }));
    }

    public void purchase(String password) {
        mCompositeSubscription.add(mHttpManager.purchaseMineField(password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<Object>(this) {
                    @Override
                    public void onResponse(Object o) {
                        showSuccess(StringUtils.getString(R.string.purchase_success));
                        getUser();
                    }
                }));
    }

    public void getUser() {
        mCompositeSubscription.add(mHttpManager.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<User>(this) {
                    @Override
                    public void onResponse(User user) {
                        mView.getUser(user);
                    }
                }));
    }
}
