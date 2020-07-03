package com.hdf.autotouch.ui.home.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.GoodsAdapter;
import com.hdf.autotouch.base.BaseFragment;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.ui.goodsdetail.GoodsDetailActivity;
import com.hdf.autotouch.ui.home.HomeActivity;
import com.hdf.autotouch.util.ImageLoaderHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/12
 *     desc  : 商城
 * </pre>
 */
public class Home1Fragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView           mTvTitle;
    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private GoodsAdapter mAdapter;

    public static Home1Fragment newInstance() {
        Bundle args = new Bundle();
        Home1Fragment fragment = new Home1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home1;
    }

    @Override
    public void initView() {
        BarUtils.addMarginTopEqualStatusBarHeight(mTvTitle);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> getData());
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new GoodsAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener((view, position) -> GoodsDetailActivity.start(mActivity, mAdapter.getData().get(position)));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
//        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
//        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mActivity, R.drawable.divider1_3e4567)));
//        mRecyclerView.addItemDecoration(divider);
        mRefreshLayout.autoRefresh();
        ((HomeActivity) mActivity).mPresenter.getMallPic();
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_get_goods: {
                mRefreshLayout.finishRefresh();
                List<Goods> list = (List<Goods>) msg.obj;
                setData(list);
            }
            break;
            default:
                break;
        }
    }

    private void getData() {
        ((HomeActivity) mActivity).mPresenter.getGoods();
    }

    private void setData(List<Goods> list) {
        mAdapter.update(list);
        if (ObjectUtils.isEmpty(list)) {
            mTvNoData.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mTvNoData.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public class ImageHolderView extends Holder<String> {

        private ImageView mImageView;

        public ImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            mImageView = itemView.findViewById(R.id.image_view);
        }

        @Override
        public void updateUI(String data) {
            ImageLoaderHelper.loadImage(mActivity, data, mImageView);
        }
    }
}
