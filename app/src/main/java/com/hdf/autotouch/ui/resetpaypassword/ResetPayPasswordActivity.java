package com.hdf.autotouch.ui.resetpaypassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.KeyboardAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.util.EncryptUtils;
import com.hdf.autotouch.util.SPManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 重置支付密码
 * </pre>
 */
public class ResetPayPasswordActivity extends BaseActivity<ResetPayPasswordPresenter> implements ResetPayPasswordView {

    @BindViews({R.id.view_oval1, R.id.view_oval2, R.id.view_oval3, R.id.view_oval4, R.id.view_oval5, R.id.view_oval6})
    List<View>   mViewOvalList;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TextView     mTvTitle;
    private String mStr   = "";
    private String mStr1  = "";
    private int    intpay = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, ResetPayPasswordActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_reset_pay_password;
    }

    @Override
    public void initView() {
        mPresenter = new ResetPayPasswordPresenter(this);
        setTitleText(R.string.set_pay_password);

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            list.add(i);
        }
        list.add(10);
        list.add(0);
        list.add(11);
        KeyboardAdapter adapter = new KeyboardAdapter(list);
        adapter.setOnItemClickListener((view, position) -> {
            if (mStr.length() <= 6) {
                if (position < 11 && position != 9) {
                    if (mStr.length() < 6) {
                        mStr = mStr + list.get(position);
                        mViewOvalList.get(mStr.length() - 1).setVisibility(View.VISIBLE);
                        if (mStr.length() == 6) {
                            //支付密码输入完成
                            if (intpay == 1) {
                                mTvTitle.setText(R.string.reset_pay_verify1);
                                intpay = 2;
                                for (int i = 0; i < mStr.length(); i++) {
                                    mViewOvalList.get(i).setVisibility(View.GONE);
                                }
                                mStr1 = mStr;
                                mStr = "";
                            } else if (intpay == 2) {
                                if (mStr.equals(mStr1)) {
                                    //接口
                                    mPresenter.setPayPassword(EncryptUtils.encryptSha256(mStr));
                                } else {
                                    ToastUtils.showShort(R.string.password_inconformity);
                                    for (int i = 0; i < mStr.length(); i++) {
                                        mViewOvalList.get(i).setVisibility(View.GONE);
                                    }
                                    mStr = "";
                                }

                            }

                        }
                    }
                } else if (position == 11) {
                    if (mStr.length() > 0) {
                        mViewOvalList.get(mStr.length() - 1).setVisibility(View.GONE);
                        mStr = mStr.substring(0, mStr.length() - 1);
                    }
                }
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @Override
    public void setPayPassword() {
        ToastUtils.showShort("修改成功");
        SPManager.setHavePayPassword(true);
        finish();
    }
}
