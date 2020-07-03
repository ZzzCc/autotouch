package com.hdf.autotouch.ui.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.MarketAdapter;
import com.hdf.autotouch.base.BaseFragment;
import com.hdf.autotouch.entity.Banners;
import com.hdf.autotouch.entity.HomeData;
import com.hdf.autotouch.entity.Market;
import com.hdf.autotouch.ui.chargecoin.ChargeCoinActivity;
import com.hdf.autotouch.ui.collectcoin.CollectCoinActivity;
import com.hdf.autotouch.ui.home.HomeActivity;
import com.hdf.autotouch.ui.mine.MineActivity;
import com.hdf.autotouch.ui.minefield.MineFieldActivity;
import com.hdf.autotouch.ui.myassets.MyAssetsActivity;
import com.hdf.autotouch.ui.notice.NoticeActivity;
import com.hdf.autotouch.ui.scan.ScanActivity;
import com.hdf.autotouch.ui.transfercoin.TransferCoinActivity;
import com.hdf.autotouch.ui.withdrawcoin.WithdrawCoinActivity;
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
 *     desc  : 首页
 * </pre>
 */
public class Home0Fragment extends BaseFragment {

    @BindView(R.id.tv_title)
    ImageView          mTvTitle;
    @BindView(R.id.convenient_banner)
    ConvenientBanner   mConvenientBanner;
    @BindView(R.id.tv_scan)
    TextView           mTvScan;
    @BindView(R.id.tv_charge_coin)
    TextView           mTvChargeCoin;
    @BindView(R.id.tv_withdraw_coin)
    TextView           mTvWithdrawCoin;
    @BindView(R.id.iv_notice)
    ImageView          mIvNotice;
    @BindView(R.id.tv_exchange)
    TextView           mTvExchange;
    @BindView(R.id.tv_mine_field)
    TextView           mTvMineField;
    @BindView(R.id.tv_collect_coin)
    TextView           mTvCollectCoin;
    @BindView(R.id.tv_transfer_coin)
    TextView           mTvTransferCoin;
    @BindView(R.id.tv_my_assets)
    TextView           mTvMyAssets;
    @BindView(R.id.tv_no_data)
    TextView           mTvNoData;
    @BindView(R.id.recycler_view)
    RecyclerView       mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.view1)
    View               mView1;
    @BindView(R.id.iv_menu)
    ImageView          mIvMenu;
    @BindView(R.id.tv_mine_pool)
    TextView           mTvMinePool;
    private MarketAdapter  mAdapter;
    private int            mPage = 1;
    private CountDownTimer mCountDownTimer;
    private int            mType = 1;
    private List<Banners>  mList;

    public static Home0Fragment newInstance() {
        Bundle args = new Bundle();
        Home0Fragment fragment = new Home0Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home0;
    }

    @Override
    public void initView() {
        BarUtils.addMarginTopEqualStatusBarHeight(mTvTitle);

        applyClickListener(mIvMenu, mTvScan, mTvChargeCoin, mTvWithdrawCoin, mIvNotice,
                mTvExchange, mTvMineField, mTvCollectCoin, mTvTransferCoin, mTvMyAssets, mTvMinePool);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            mPage = 1;
            getData();
        });
//        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
//            mPage += 1;
//            getData();
//        });

        mAdapter = new MarketAdapter(new ArrayList<>());
        mAdapter.setHeaderView(new View(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
//        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mActivity, R.drawable.divider1_3e4567)));
//        mRecyclerView.addItemDecoration(divider);
        mRefreshLayout.autoRefresh();

        mConvenientBanner.startTurning(3000);

        ((HomeActivity) mActivity).mPresenter.getHomeData();


    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.iv_menu: {
                MineActivity.start(mActivity);
                mActivity.overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_right);
            }
            break;
            case R.id.tv_scan: {
                ScanActivity.start(mActivity);
            }
            break;
            case R.id.tv_charge_coin: {
                if (isLogin()) {
                    ChargeCoinActivity.start(mActivity);
                }
            }
            break;
            case R.id.tv_withdraw_coin: {
                if (isLogin()) {
                    WithdrawCoinActivity.start(mActivity);
                }
            }
            break;
            case R.id.iv_notice: {
                NoticeActivity.start(mActivity);
            }
            break;
            case R.id.tv_exchange: {
                NoticeActivity.start(mActivity);
//                if (isLogin()) {
//                    ExchangeActivity.start(mActivity);
//                }
            }
            break;
            case R.id.tv_mine_field: {
                if (isLogin()) {
                    MineFieldActivity.start(mActivity);
                }
            }
            break;
            case R.id.tv_collect_coin: {
                if (isLogin()) {
                    CollectCoinActivity.start(mActivity);
                }
            }
            break;
            case R.id.tv_transfer_coin: {
                if (isLogin()) {
                    TransferCoinActivity.start(mActivity, "");
                }
            }
            break;
            case R.id.tv_my_assets: {
                if (isLogin()) {
                    MyAssetsActivity.start(mActivity);
                }
            }
            break;
            case R.id.tv_mine_pool: {
                if (isLogin()) {
                }
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_get_home_data: {
                HomeData homeData = (HomeData) msg.obj;
                mList = new ArrayList<>();
                for (int i = 0; i < homeData.getPics().size(); i++) {
                    Banners banners = new Banners();
                    banners.setStr(homeData.getPics().get(i));
                    banners.setPos(i);
                    banners.setType(homeData.getType());
                    banners.setTimeAsLong(homeData.getTimeAsLong());
                    mList.add(banners);
                }
                setBanner(mList);
            }
            break;
            case R.id.msg_get_market: {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                List<Market> list = (List<Market>) msg.obj;
                setData(list);
            }
            break;
            default:
                break;
        }
    }

    private void setBanner(List<Banners> list) {
        if (!ObjectUtils.isEmpty(list)) {
            mConvenientBanner.setPages(
                    new CBViewHolderCreator() {
                        @Override
                        public ImageHolderView createHolder(View itemView) {
                            return new ImageHolderView(itemView);
                        }

                        @Override
                        public int getLayoutId() {
                            return R.layout.item_image;
                        }
                    }, list)
                    .setPageIndicator(new int[]{R.drawable.block_uncheck, R.drawable.block_check});
        }
    }

    private void getData() {
        ((HomeActivity) mActivity).mPresenter.getMarket(mPage);
    }

    private void setData(List<Market> list) {
        List<Market> data = mAdapter.getData();
        if (mPage == 1) {
            data = list;
            mRecyclerView.scrollToPosition(0);
        } else {
            if (ObjectUtils.isEmpty(list)) {
                mPage -= 1;
            } else {
                data.addAll(list);
            }
        }
        mAdapter.update(data);
        if (ObjectUtils.isEmpty(data)) {
            mTvNoData.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mTvNoData.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    class ImageHolderView extends Holder<Banners> {

        private ImageView mImageView;
        private TextView  mTvTime;

        public ImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            mImageView = itemView.findViewById(R.id.image_view);
            mTvTime = itemView.findViewById(R.id.tv_time);
            long nowMills = TimeUtils.getNowMills();
            long endMills = 0;
            if (!ObjectUtils.isEmpty(mList) && !ObjectUtils.isEmpty(mList.get(0))) {
                endMills = mList.get(0).getTimeAsLong() * 1000;
            }
            if (nowMills < endMills) {
                long millis = endMills - nowMills;
                if (mList.get(0).getType() != -1 && !ObjectUtils.isEmpty(mList.get(0).getTimeAsLong())) {
                    mCountDownTimer = new CountDownTimer(millis, 1000) {
                        @Override
                        public void onTick(long millisLeft) {
                            if (!ObjectUtils.isEmpty(mCountDownTimer)) {
                                setTimeText(millisLeft);
                            }
                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                    mCountDownTimer.start();
                }
            }

        }

        @Override
        public void updateUI(Banners data) {
            ImageLoaderHelper.loadImage(mActivity, data.getStr(), mImageView);
            if (data.getPos() == data.getType()) {
                mTvTime.setVisibility(View.VISIBLE);
            } else {
                mTvTime.setVisibility(View.GONE);
            }
        }

        @SuppressLint("SetTextI18n")
        private void setTimeText(long millisLeft) {
            long day = 0;
            long hour = 0;
            long minute = 0;
            long second = 0;
            second = millisLeft / 1000;
            if (second > 60) {
                minute = second / 60;
                second = second % 60;
            }
            if (minute > 60) {
                hour = minute / 60;
                minute = minute % 60;
            }
            if (hour > 24) {
                day = hour / 24;
                hour = hour % 24;
            }

            String dayStr = String.valueOf(day);
            String hourStr = String.valueOf(hour);
            String minuteStr = String.valueOf(minute);
            String secondStr = String.valueOf(second);
            if (hourStr.length() == 1) {
                hourStr = "0" + hourStr;
            }
            if (minuteStr.length() == 1) {
                minuteStr = "0" + minuteStr;
            }
            if (secondStr.length() == 1) {
                secondStr = "0" + secondStr;
            }
            mTvTime.setText(dayStr + getString(R.string.day) + " " + hourStr + ":" + minuteStr + ":" + secondStr);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

}
