package com.hdf.autotouch.ui.macaddress;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.MyMill;

import java.util.List;

public interface MacAddressView extends IView {
    void getMill(List<MyMill> millList);
    void getAllBinding(Integer num);
}
