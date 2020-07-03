package com.hdf.autotouch.ui.payment;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Order;

public interface PaymentView extends IView {
    void payOrder(Order order);

    void payOrderError();
}
