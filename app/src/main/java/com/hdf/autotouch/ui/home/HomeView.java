package com.hdf.autotouch.ui.home;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.entity.HomeData;
import com.hdf.autotouch.entity.Market;
import com.hdf.autotouch.entity.MyAssets;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.entity.VersionCode;

import java.util.List;

public interface HomeView extends IView {
    void getHomeData(HomeData homeData);

    void getMarket(List<Market> list);

    void getMallPic(List<String> list);

    void getGoods(List<Goods> list);

    void getUser(User user);

    void getVersionCode(VersionCode versionCode);

    void getTransaction(MyAssets myAssets);
}
