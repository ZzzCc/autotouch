package com.hdf.autotouch.ui.minefield;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.MineField;
import com.hdf.autotouch.entity.User;

public interface MineFieldView extends IView {
    void getMineField(MineField mineField);
    void getUser(User user);
}
