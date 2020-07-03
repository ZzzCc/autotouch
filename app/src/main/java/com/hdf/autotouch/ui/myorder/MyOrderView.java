package com.hdf.autotouch.ui.myorder;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Order;

import java.util.List;

public interface MyOrderView extends IView {
    void getOrder(List<Order> list, int index);

    void payOrder(Order order);

    void payOrderError();

    void cancelOrder();
    void getAllBinding(Integer num);
}
