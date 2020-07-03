package com.hdf.autotouch.ui.paypassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.KeyboardAdapter;
import com.hdf.autotouch.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 请输入支付密码
 * </pre>
 */
public class PayPasswordActivity extends BaseActivity {

    @BindView(R.id.iv_close)
    ImageView    mIvClose;
    @BindViews({R.id.view_oval1, R.id.view_oval2, R.id.view_oval3, R.id.view_oval4, R.id.view_oval5, R.id.view_oval6})
    List<View>   mViewOvalList;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private String mStr = "";
    private int    mMode;

    public static void start(Context context, int mode) {
        Intent intent = new Intent(context, PayPasswordActivity.class);
        intent.putExtra("mode", mode);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        assert bundle != null;
        mMode = bundle.getInt("mode");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pay_password;
    }

    @Override
    public void initView() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.windowAnimations = R.style.AnimationFade;
        getWindow().setAttributes(params);

        applyClickListener(mIvClose);

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
                            if (mMode == 0) {
                                sendMessage(R.id.msg_input_pay_password, mStr);
                            } else {
                                sendMessage(R.id.msg_input_pay_password1, mStr);
                            }
                            finish();
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
        if (view.getId() == R.id.iv_close) {
            finish();
        }
    }
}
