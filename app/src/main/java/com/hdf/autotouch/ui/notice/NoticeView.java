package com.hdf.autotouch.ui.notice;

import com.hdf.autotouch.base.IView;
import com.hdf.autotouch.entity.Notice;

import java.util.List;

public interface NoticeView extends IView {
    void getNotice(List<Notice> list);
}
