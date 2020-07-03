package com.hdf.autotouch.ui.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseFragment;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.ui.aboutus.AboutUsActivity;
import com.hdf.autotouch.ui.bill.BillActivity;
import com.hdf.autotouch.ui.login.LoginActivity;
import com.hdf.autotouch.ui.macaddress.MacAddressActivity;
import com.hdf.autotouch.ui.myassets.MyAssetsActivity;
import com.hdf.autotouch.ui.myorder.MyOrderActivity;
import com.hdf.autotouch.ui.myserve.MyServeActivity;
import com.hdf.autotouch.ui.myteam.MyTeamActivity;
import com.hdf.autotouch.ui.recommend.RecommendActivity;
import com.hdf.autotouch.ui.settingcenter.SettingCenterActivity;
import com.hdf.autotouch.ui.web.WebActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.FormatUtils;
import com.hdf.autotouch.util.SPManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/12
 *     desc  : 快讯
 * </pre>
 */
public class Home3Fragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView  mTvTitle;
    @BindView(R.id.img_customer_service)
    ImageView mImgCustomerService;
    @BindView(R.id.img_launcher)
    ImageView mImgLauncher;
    @BindView(R.id.tv_id)
    TextView  mTvId;
    @BindView(R.id.tv_node)
    TextView  mTvNode;
    @BindView(R.id.tv_time)
    TextView  mTvTime;
    @BindView(R.id.tv_mine_asset)
    TextView  mTvMineAsset;
    @BindView(R.id.tv_mine_order_form)
    TextView  mTvMineOrderForm;
    @BindView(R.id.tv_mine_mac_address)
    TextView  mTvMineMacAddress;
    @BindView(R.id.tv_mine_bill)
    TextView  mTvMineBill;
    @BindView(R.id.tv_mine_team)
    TextView  mTvMineTeam;
    @BindView(R.id.tv_mine_recommend)
    TextView  mTvMineRecommend;
    @BindView(R.id.tv_mine_about_us)
    TextView  mTvMineAboutUs;
    @BindView(R.id.tv_mine_setting)
    TextView  mTvMineSetting;
    @BindView(R.id.tv_login)
    TextView  mTvLogin;
    @BindView(R.id.tv_mine_serve)
    TextView  mTvMineServe;
    @BindView(R.id.tv_mine_help)
    TextView  mTvMineHelp;
    @BindView(R.id.iv_recommend)
    ImageView mIvRecommend;

    public static Home3Fragment newInstance() {
        Bundle args = new Bundle();
        Home3Fragment fragment = new Home3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_home3;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        BarUtils.addMarginTopEqualStatusBarHeight(mTvTitle);
        applyClickListener(mImgCustomerService, mTvMineAsset, mTvLogin, mTvMineOrderForm,
                mTvMineMacAddress, mTvMineBill, mTvMineTeam, mTvMineRecommend, mTvMineAboutUs,
                mTvMineSetting, mTvMineServe, mTvMineHelp, mIvRecommend);

        updateMine();
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.tv_login://创建账号/登录
                LoginActivity.start(getContext());
                break;
            case R.id.tv_mine_asset://我的资产
                if (isLogin()) {
                    MyAssetsActivity.start(mActivity);
                }
                break;
            case R.id.tv_mine_order_form://订单
                if (isLogin()) {
                    MyOrderActivity.start(mActivity);
                }
                break;
            case R.id.tv_mine_mac_address://存力码
                if (isLogin()) {
                    MacAddressActivity.start(getContext());
                }
                break;
            case R.id.tv_mine_bill://账单
                if (isLogin()) {
                    BillActivity.start(getContext());
                }
                break;
            case R.id.tv_mine_serve://我的服务器
                if (isLogin()) {
                    MyServeActivity.start(getContext());
                }
                break;
            case R.id.tv_mine_team://我的团队
                if (isLogin()) {
                    MyTeamActivity.start(getContext());
                }
                break;
            case R.id.tv_mine_recommend://推荐有奖
                break;
            case R.id.iv_recommend:
                if (isLogin()) {
                    RecommendActivity.start(getContext());
                }
                break;
            case R.id.tv_mine_help://帮助中心
                WebActivity.start(getContext(), Constant.HELP_CENTER_URL);
                break;
            case R.id.tv_mine_about_us://关于我们
                AboutUsActivity.start(getContext());
                break;
            case R.id.tv_mine_setting://设置
                if (isLogin()) {
                    SettingCenterActivity.start(getContext());
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
            case R.id.msg_update_mine:
                updateMine();
                break;
            case R.id.msg_wechat:
                DialogHelper.showCustomerServiceDialog((String) msg.obj);
                break;
            default:
                break;
        }
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    private void updateMine() {
        if (SPManager.getId() != null && !"".equals(SPManager.getId())) {
            mTvId.setVisibility(View.VISIBLE);
            mTvNode.setVisibility(View.VISIBLE);
            mTvLogin.setVisibility(View.GONE);
            mTvId.setText(SPManager.getAreaCode() + SPManager.getUserName());
            mTvNode.setText(FormatUtils.getLevelType(SPManager.getLevel()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String format1 = format.format(new Date(SPManager.getCreateTime()));
            mTvTime.setText(getString(R.string.registration_date) + "  " + format1);
        } else {
            mTvId.setVisibility(View.GONE);
            mTvNode.setVisibility(View.GONE);
            mTvLogin.setVisibility(View.VISIBLE);
            mTvTime.setText("");
        }
    }


}
