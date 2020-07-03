package com.hdf.autotouch.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.ui.addore.AddOreActivity;
import com.hdf.autotouch.ui.home.HomeActivity;
import com.hdf.autotouch.ui.login.LoginActivity;
import com.hdf.autotouch.ui.mine.MineActivity;
import com.hdf.autotouch.ui.paypassword.PayPasswordActivity;
import com.hdf.autotouch.ui.resetloginpassword.ResetLoginPasswordActivity;
import com.hdf.autotouch.ui.splash.SplashActivity;
import com.hdf.autotouch.ui.transactiondetail.TransactionDetailActivity;
import com.hdf.autotouch.util.DialogHelper;
import com.hdf.autotouch.util.SPManager;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity {

    public    P                    mPresenter;
    protected Activity             mActivity;
    protected View                 mViewBgTopBar;
    protected Toolbar              mToolbar;
    protected TextView             mTvToolbarTitle;
    private   long                 lastClick      = 0;
    private   View.OnClickListener mClickListener = v -> {
        if (!isFastClick()) {
            onViewClick(v);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (ScreenUtils.isPortrait()) {
            AdaptScreenUtils.adaptWidth(getResources(), 1080);
            AdaptScreenUtils.adaptHeight(getResources(), 1920);
        } else {
            AdaptScreenUtils.adaptWidth(getResources(), 1920);
            AdaptScreenUtils.adaptHeight(getResources(), 1080);
        }
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        setRootLayout(bindLayout());
        ButterKnife.bind(this);
        initView();
        doBusiness();
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @SuppressLint("ResourceType")
    @Override
    public void setRootLayout(@LayoutRes int layoutId) {
        if (layoutId > 0) {
            setContentView(layoutId);

            BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
            BarUtils.setStatusBarLightMode(this, true);

            mToolbar = findViewById(R.id.toolbar);
            if (mToolbar != null) {
                mViewBgTopBar = findViewById(R.id.view_bg_top_bar);
                mTvToolbarTitle = findViewById(R.id.tv_toolbar_title);
                BarUtils.addMarginTopEqualStatusBarHeight(mToolbar);
                setSupportActionBar(mToolbar);
                getToolbar().setDisplayShowTitleEnabled(false);
                if (!(ActivityUtils.getTopActivity() instanceof HomeActivity)) {
                    getToolbar().setDisplayHomeAsUpEnabled(true);
                }
            }

            initSlideBack();
        }
    }

    protected ActionBar getToolbar() {
        return getSupportActionBar();
    }

    private void initSlideBack() {
        if (!(ActivityUtils.getTopActivity() instanceof SplashActivity)
                && !(ActivityUtils.getTopActivity() instanceof HomeActivity)
                && !(ActivityUtils.getTopActivity() instanceof TransactionDetailActivity)
                && !(ActivityUtils.getTopActivity() instanceof AddOreActivity)
                && !(ActivityUtils.getTopActivity() instanceof PayPasswordActivity)
                && !(ActivityUtils.getTopActivity() instanceof MineActivity)) {
            SlidrConfig config = new SlidrConfig.Builder()
                    .edge(true)
                    .build();
            Slidr.attach(this, config);
        }
    }

    public void setTitleText(CharSequence text) {
        mTvToolbarTitle.setText(text);
    }

    public void setTitleText(@StringRes int resid) {
        mTvToolbarTitle.setText(resid);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        EventBus.getDefault().post(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Message msg) {
    }

    protected void applyLightMode(boolean isLightMode) {
        BarUtils.setStatusBarLightMode(this, isLightMode);
        if (mToolbar != null) {
            if (isLightMode) {
                mViewBgTopBar.setBackgroundResource(R.drawable.bg_top_bar);
                mToolbar.setNavigationIcon(R.drawable.ic_back);
                mTvToolbarTitle.setTextColor(ColorUtils.getColor(R.color.light_black1));
            } else {
                mViewBgTopBar.setBackgroundColor(Color.TRANSPARENT);
                mToolbar.setNavigationIcon(R.drawable.ic_back1);
                mTvToolbarTitle.setTextColor(ColorUtils.getColor(R.color.white));
            }
        }
    }

    /**
     * 申请点击监听
     *
     * @param views 视图
     */
    public void applyClickListener(View... views) {
        ClickUtils.applyGlobalDebouncing(views, mClickListener);
        ClickUtils.applyScale(views);
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= Constant.CLICK_INTERVAL) {
            lastClick = now;
            return false;
        }
        return true;
    }

    /**
     * 判断是否登录
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    protected boolean isLogin() {
        if (StringUtils.isEmpty(SPManager.getId())) {
            LoginActivity.start(ActivityUtils.getTopActivity());
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否设置支付密码
     *
     * @return
     */
    protected boolean havePayPassword() {
        if (!StringUtils.isEmpty(SPManager.getId()) && !SPManager.getHavePayPassword()) {
            DialogHelper.showConfirmDialog(0, getString(R.string.setting_pay_password), () -> ResetLoginPasswordActivity.start(mActivity, 1));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, event)) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                );
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * Return whether touch the view.
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
