package com.hdf.autotouch.ui.exchangerecord;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Exchange;

import java.util.List;

public interface ExchangeRecordView extends IView {
    void getExchangeRecord(List<Exchange> list);
}
