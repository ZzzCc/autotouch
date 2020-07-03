package com.hdf.autotouch.ui.confirmorder;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Order;

public interface ConfirmOrderView extends IView {
    void submitOrder(Order order);
}
