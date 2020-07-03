package com.hdf.autotouch.ui.orderdetail;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Order;

public interface OrderDetailView extends IView {
    void getOrderDetail(Order order);

    void payOrder(Order order);

    void payOrderError();

    void cancelOrder();
    void getAllBinding(Integer num);
}
