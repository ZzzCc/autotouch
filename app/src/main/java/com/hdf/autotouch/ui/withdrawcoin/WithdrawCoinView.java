package com.hdf.autotouch.ui.withdrawcoin;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.WithdrawAddress;

public interface WithdrawCoinView extends IView {
    void getBindingAddress(WithdrawAddress withdrawAddress);

    void getWithdrawCoin();
}
