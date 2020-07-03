package com.hdf.autotouch.ui.exchange;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Exchange;
import com.hdf.autotouch.entity.ExchangeRate;

public interface ExchangeView extends IView {
    void getExchangeRate(ExchangeRate exchangeRate);

    void exchange(Exchange exchange);
}
