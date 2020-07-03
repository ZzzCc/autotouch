package com.hdf.autotouch.ui.collectcoin;

import android.graphics.Bitmap;

import com.hdf.autotouch.base.IView;

public interface CollectCoinView extends IView {
    void createQrCode(Bitmap bitmap);
}
