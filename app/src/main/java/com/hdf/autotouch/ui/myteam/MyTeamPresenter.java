package com.hdf.autotouch.ui.myteam;

import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.MyTeam;
import com.hdf.autotouch.http.HttpCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyTeamPresenter extends BasePresenter<MyTeamView> {

    MyTeamPresenter(MyTeamView view) {
        super(view);
    }

    public void myTeam() {
        mCompositeSubscription.add(mHttpManager.myTeam()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpCallback<MyTeam>(this) {
                    @Override
                    public void onResponse(MyTeam myTeam) {
                        mView.myTean(myTeam);
                    }
                }));
    }
}
