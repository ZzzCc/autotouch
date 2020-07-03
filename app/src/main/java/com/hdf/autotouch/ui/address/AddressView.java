package com.hdf.autotouch.ui.address;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Address;

import java.util.List;

public interface AddressView extends IView {
    void getAddress(List<Address> list);

    void updateAddress();
}
