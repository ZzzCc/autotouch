package com.hdf.autotouch.ui.areacode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.easysidebar.lib.EasySideBar;
import com.easysidebar.utils.PinyinUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.adapter.AreaCodeAdapter;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.entity.AreaCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/12
 *     desc  : 国家/地区
 * </pre>
 */
public class AreaCodeActivity extends BaseActivity<AreaCodePresenter> implements AreaCodeView {

    @BindView(R.id.rv_area_code)
    RecyclerView mRvAreaCode;
    @BindView(R.id.side_bar)
    EasySideBar  mSideBar;
    private LinearLayoutManager mManager;
    private AreaCodeAdapter     mAdapter;
    private List<AreaCode>      mList;
    private boolean             mIsMove;
    private int                 mPosition;

    public static void start(Context context) {
        Intent intent = new Intent(context, AreaCodeActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_area_code;
    }

    @Override
    public void initView() {
        mPresenter = new AreaCodePresenter(this);
        setTitleText(R.string.area_code);

        mList = new ArrayList<>();
        mAdapter = new AreaCodeAdapter(mList);
        mAdapter.setOnItemClickListener((view, position) -> {
            sendMessage(R.id.msg_item_click, mList.get(position));
            finish();
        });
        mRvAreaCode.setAdapter(mAdapter);
        mManager = new LinearLayoutManager(this);
        mRvAreaCode.setLayoutManager(mManager);
        mRvAreaCode.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mIsMove) {
                    mIsMove = false;
                    int n = mPosition - mManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRvAreaCode.getChildCount()) {
                        int top = mRvAreaCode.getChildAt(n).getTop();
                        mRvAreaCode.scrollBy(0, top);
                    }
                }
            }
        });

        mSideBar.setLazyRespond(true);
        mSideBar.setTextColor(getResources().getColor(R.color.light_black2));
        mSideBar.setOnSelectIndexItemListener((index, value) -> {
            mPosition = mAdapter.getPositionForSection(value.charAt(0));
            if (mPosition != -1) {
                moveToPosition(mPosition);
            } else {
                moveToPosition(0);
            }
        });
    }

    private void moveToPosition(int index) {
        //获取当前recycleView屏幕可见的第一项和最后一项的Position
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        //然后区分情况
        if (index <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            mRvAreaCode.scrollToPosition(index);
        } else if (index <= lastItem) {
            //当要置顶的项已经在屏幕上显示时，计算它离屏幕原点的距离
            int top = mRvAreaCode.getChildAt(index - firstItem).getTop();
            mRvAreaCode.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            mRvAreaCode.scrollToPosition(index);
            //记录当前需要在RecyclerView滚动监听里面继续第二次滚动
            mIsMove = true;
        }
    }

    @Override
    public void doBusiness() {
        mPresenter.loadData();
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @Override
    public void onParseData(List<AreaCode> list) {
        mList = list;
        mSideBar.setVisibility(View.VISIBLE);
        ArrayList<String> indexString = new ArrayList<>();
        boolean isGarbled = false;
        for (int i = 0; i < mList.size(); i++) {
            String pinyin = PinyinUtils.getPingYin(mList.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            } else {
                isGarbled = true;
            }
        }
        Collections.sort(indexString);
        if (isGarbled) {
            indexString.add("#");
        }
        mSideBar.setIndexItems(indexString.toArray(new String[0]));
        mAdapter.update(list);
    }
}
