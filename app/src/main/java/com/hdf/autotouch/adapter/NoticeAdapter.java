package com.hdf.autotouch.adapter;

import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.Notice;

import java.util.List;

public class NoticeAdapter extends SingleAdapter<Notice> {

    public NoticeAdapter(List<Notice> list) {
        super(list, R.layout.item_notice);
    }

    @Override
    protected void bind(BaseViewHolder holder, Notice data) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        TextView tvTime = holder.getView(R.id.tv_time);
        TextView tvContent = holder.getView(R.id.tv_content);

        tvTitle.setText(data.getNoticeTitle());
        tvTime.setText(TimeUtils.millis2String(data.getNoticeTime()));
        tvContent.setText(data.getNoticeContent());
    }
}
