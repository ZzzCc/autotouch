package com.hdf.autotouch.ui.version;


import com.hdf.autotouch.base.IView;

public interface VersionUpdateView extends IView {
    void updateProgress(int progress);

    void downloadFinish();

    void downloadFailure();
}
