package com.hdf.autotouch.ui.login;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.User;

public interface LoginView extends IView {
    void login(User user);
}
