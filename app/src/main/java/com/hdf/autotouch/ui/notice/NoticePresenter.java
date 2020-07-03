package com.hdf.autotouch.ui.notice;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.Notice;
import com.hdf.autotouch.http.ExceptionHandler;
import com.hdf.autotouch.http.HttpCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NoticePresenter extends BasePresenter<NoticeView> {

    NoticePresenter(NoticeView view) {
        super(view);
    }

    public void getNotice(int page) {
        mCompositeSubscription.add(mHttpManager.getNotice(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<List<Notice>>(this, false) {
                    @Override
                    public void onResponse(List<Notice> list) {
                        mView.getNotice(list);
                    }

                    @Override
                    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
                        super.onErrors(throwable);
                        mView.getNotice(null);
                    }
                }));
    }
}
