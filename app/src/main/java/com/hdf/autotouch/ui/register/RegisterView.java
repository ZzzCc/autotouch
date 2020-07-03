package com.hdf.autotouch.ui.register;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.User;

public interface RegisterView extends IView {
    void register(User user);
}
