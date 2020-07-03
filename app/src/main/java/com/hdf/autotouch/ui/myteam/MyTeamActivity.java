package com.hdf.autotouch.ui.myteam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.MyTeam;
import com.hdf.autotouch.util.FormatUtils;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 我的团队
 * </pre>
 */
public class MyTeamActivity extends BaseActivity<MyTeamPresenter> implements MyTeamView {

    @BindView(R.id.tv_gtse)
    TextView mTvGtse;
    @BindView(R.id.tv_usdt)
    TextView mTvUsdt;
    @BindView(R.id.tv_new_identity)
    TextView mTvNewIdentity;
    @BindView(R.id.tv_team_num)
    TextView mTvTeamNum;
    @BindView(R.id.tv_team_performance)
    TextView mTvTeamPerformance;
    @BindView(R.id.tv_team_yesterday_award)
    TextView mTvTeamYesterdayAward;
    @BindView(R.id.tv_performance_award)
    TextView mTvPerformanceAward;
    @BindView(R.id.tv_trustee_yesterday_earnings)
    TextView mTvTrusteeYesterdayEarnings;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyTeamActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_team;
    }

    @Override
    public void initView() {
        mPresenter = new MyTeamPresenter(this);
        mPresenter.myTeam();
        mViewBgTopBar.setBackgroundColor(Color.TRANSPARENT);
        setTitleText(R.string.my_team);
        applyLightMode(false);
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void myTean(MyTeam myTeam) {
        mTvGtse.setText(myTeam.getAllFile());
        mTvUsdt.setText(myTeam.getAllUsdt());
        mTvNewIdentity.setText(String.format(getString(R.string.current_status), FormatUtils.getLevelType(myTeam.getLevel() + "")));
        mTvTeamNum.setText(String.format(getString(R.string.person), myTeam.getTeamPerson() + ""));
        mTvTeamPerformance.setText(String.format(getString(R.string.locker_serve), myTeam.getPerformance() + ""));
        mTvPerformanceAward.setText(String.format(getString(R.string.usdt3), myTeam.getYesterdayUsdt()));
        mTvTrusteeYesterdayEarnings.setText(myTeam.getTrusteeFee() + "%");
    }
}
