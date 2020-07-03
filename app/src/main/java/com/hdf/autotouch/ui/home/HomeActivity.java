package com.hdf.autotouch.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.Goods;
import com.hdf.autotouch.entity.HomeData;
import com.hdf.autotouch.entity.Market;
import com.hdf.autotouch.entity.MyAssets;
import com.hdf.autotouch.entity.User;
import com.hdf.autotouch.entity.VersionCode;
import com.hdf.autotouch.ui.goodsdetail.GoodsDetailActivity;
import com.hdf.autotouch.ui.home.fragment.Home0Fragment;
import com.hdf.autotouch.ui.home.fragment.Home2Fragment;
import com.hdf.autotouch.ui.mine.MineActivity;
import com.hdf.autotouch.ui.version.VersionUpdateActivity;
import com.hdf.autotouch.util.SPManager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/12
 *     desc  : 首页
 * </pre>
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeView {

    @BindView(R.id.rb_home0)
    RadioButton mRbHome0;
    @BindView(R.id.rb_home2)
    RadioButton mRbHome2;
    @BindView(R.id.tv_mall)
    TextView    mTvMall;
    private Fragment[] mFragments = new Fragment[2];
    private long       mExitTime  = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(SPManager.getId())) {
            mPresenter.getUser();
        }
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        mPresenter = new HomePresenter(this);

        applyClickListener(mTvMall);

        mFragments[0] = Home0Fragment.newInstance();
        mFragments[1] = Home2Fragment.newInstance();
        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.layout_container, 0);

        mRbHome0.setChecked(true);

        mPresenter.getVersionCode();
    }

    @OnCheckedChanged({R.id.rb_home0, R.id.rb_home2})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rb_home0: {
                    applyLightMode(true);
                    FragmentUtils.showHide(0, mFragments);
                }
                break;
                case R.id.rb_home2: {
                    applyLightMode(false);
                    FragmentUtils.showHide(1, mFragments);
                    isLogin();
                }
                break;
                default:
                    break;
            }
        }
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
        GoodsDetailActivity.start(this, null);
    }

    @Override
    public void getHomeData(HomeData homeData) {
        sendMessage(R.id.msg_get_home_data, homeData);
    }

    @Override
    public void onMessageEvent(Message msg) {
        super.onMessageEvent(msg);
        switch (msg.what) {
            case R.id.msg_to_mine:
                MineActivity.start(this);
                mActivity.overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_right);
                break;
            default:
                break;
        }
    }

    @Override
    public void getMarket(List<Market> list) {
        sendMessage(R.id.msg_get_market, list);
    }

    @Override
    public void getMallPic(List<String> list) {
        sendMessage(R.id.msg_pic_list_mall, list);
    }

    @Override
    public void getGoods(List<Goods> list) {
        sendMessage(R.id.msg_get_goods, list);
    }

    @Override
    public void getUser(User user) {
        if (!ObjectUtils.isEmpty(user)
                && !StringUtils.equals(String.valueOf(user.getLevel()), SPManager.getLevel())) {
            SPManager.setLevel(String.valueOf(user.getLevel()));
            sendMessage(R.id.msg_update_mine, null);
        }
    }

    @Override
    public void getVersionCode(VersionCode versionCode) {
        if (!AppUtils.getAppVersionName().equals(versionCode.getVersionNum())) {
            VersionUpdateActivity.start(this, versionCode);
//            DialogHelper.showVersionCodeDialog(versionCode.getContent(), versionCode.getStatus(), new DialogHelper.OnVersionListener() {
//                @Override
//                public void onCancel() {
//                    init();
//                }
//
//                @Override
//                public void onConfirm() {
//                    try {
//                        if (!StringUtils.isEmpty(versionCode.getVersionUrl())) {
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(versionCode.getVersionUrl()));
//                            startActivity(intent);
//                        } else {
//                            ToastUtils.showShort("更新链接失效");
//                        }
//                    } catch (ActivityNotFoundException a) {
//                        a.getMessage();
//                    }
//                    if (versionCode.getStatus() == 0) {
//                        init();
//                    }
//                }
//            });
        } else {
            init();
        }
    }

    @Override
    public void getTransaction(MyAssets myAssets) {
        sendMessage(R.id.msg_my_assets, myAssets);
    }

    private void init() {
        havePayPassword();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort(R.string.double_click_exit);
            mExitTime = System.currentTimeMillis();
        } else {
            AppUtils.exitApp();
        }
    }
}
