package com.hdf.autotouch.ui.chargecoin;

import android.graphics.Bitmap;

import com.hdf.autotouch.base.IView;

public interface ChargeCoinView extends IView {
    void getAddress(String str);

    void createQrCode(Bitmap bitmap);
}
