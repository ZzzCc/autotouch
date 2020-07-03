package com.hdf.autotouch.ui.bill;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.MyBill;

import java.util.List;

public interface BillView extends IView {

    void getBill(List<MyBill> billList);
}
