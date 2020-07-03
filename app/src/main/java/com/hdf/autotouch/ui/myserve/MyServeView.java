package com.hdf.autotouch.ui.myserve;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.MyMac;

public interface MyServeView extends IView {
    void getmyMac(MyMac myMac);

    void getDeductMac();
}
