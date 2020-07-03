package com.hdf.autotouch.base.rv.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.blankj.utilcode.util.ClickUtils;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/22
 *     desc  :
 * </pre>
 */
public abstract class SingleAdapter<M> extends BaseAdapter<M> {

    private final int mLayoutId;

    public SingleAdapter(List<M> list, @LayoutRes int layoutId) {
        setData(list);
        mLayoutId = layoutId;
    }

    @Override
    protected int bindLayout(final int viewType) {
        return mLayoutId;
    }

    public void update(List<M> list) {
        setData(list);
        notifyDataSetChanged();
    }

    public void applyClickListener(View.OnClickListener listener, View... views) {
        ClickUtils.applyGlobalDebouncing(views, listener);
        ClickUtils.applyScale(views);
    }
}
