package com.hdf.autotouch.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;
import com.hdf.autotouch.entity.AreaCode;

import java.util.List;

public class AreaCodeAdapter extends SingleAdapter<AreaCode> {

    public AreaCodeAdapter(List<AreaCode> list) {
        super(list, R.layout.item_area_code);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void bind(BaseViewHolder holder, AreaCode data) {
        TextView tvCategory = holder.getView(R.id.tv_letter);
        TextView tvName = holder.getView(R.id.tv_text);
        int position = getData().indexOf(data);
        int section = getData().get(position).getSortLetters().charAt(0);
        if (position == getPositionForSection(section)) {
            tvCategory.setVisibility(View.VISIBLE);
            tvCategory.setText(data.getSortLetters());
        } else {
            tvCategory.setVisibility(View.GONE);
        }
        tvName.setText(data.getName() + " " + String.format(Utils.getApp().getString(R.string.bracket), "+" + data.getAreacode()));
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getData().size(); i++) {
            String sortStr = getData().get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
