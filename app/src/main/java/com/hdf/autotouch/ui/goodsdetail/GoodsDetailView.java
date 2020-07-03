package com.hdf.autotouch.ui.goodsdetail;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Goods;

import java.util.List;

public interface GoodsDetailView extends IView {
    void getGoods(List<Goods> list);
}
