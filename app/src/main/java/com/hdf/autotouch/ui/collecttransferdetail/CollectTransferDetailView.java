package com.hdf.autotouch.ui.collecttransferdetail;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.CollectTransferDetail;

import java.util.List;

public interface CollectTransferDetailView extends IView {
    void getCollectTransferDetail(List<CollectTransferDetail> list);
}
