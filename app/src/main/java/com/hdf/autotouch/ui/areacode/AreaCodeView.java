package com.hdf.autotouch.ui.areacode;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.AreaCode;

import java.util.List;

public interface AreaCodeView extends IView {
    void onParseData(List<AreaCode> list);
}
