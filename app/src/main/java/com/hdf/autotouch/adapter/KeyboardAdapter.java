package com.hdf.autotouch.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.Utils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.rv.BaseViewHolder;
import com.hdf.autotouch.base.rv.adapter.SingleAdapter;

import java.util.List;

public class KeyboardAdapter extends SingleAdapter<Integer> {

    public KeyboardAdapter(List<Integer> list) {
        super(list, R.layout.item_keyboard);
    }

    @Override
    protected void bind(BaseViewHolder holder, Integer data) {
        TextView mTvKeyboard = holder.getView(R.id.tv_keyboard);
        ImageView mIvKeyboardDelete = holder.getView(R.id.iv_keyboard_delete);
        if (getData().indexOf(data) == 9) {
            mTvKeyboard.setBackgroundColor(Utils.getApp().getResources().getColor(R.color.divider));
            mTvKeyboard.setText("");
            mTvKeyboard.setVisibility(View.VISIBLE);
            mIvKeyboardDelete.setVisibility(View.GONE);
        } else if (getData().indexOf(data) == 11) {
            mTvKeyboard.setVisibility(View.GONE);
            mIvKeyboardDelete.setVisibility(View.VISIBLE);
        } else {
            mTvKeyboard.setBackgroundResource(R.drawable.bg_keyboard);
            mTvKeyboard.setText(String.valueOf(data));
            mTvKeyboard.setVisibility(View.VISIBLE);
            mIvKeyboardDelete.setVisibility(View.GONE);
        }
    }
}
