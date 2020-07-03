package com.hdf.autotouch.ui.areacode;

import android.content.res.AssetManager;

import com.blankj.utilcode.util.Utils;
import com.easysidebar.utils.PinyinComparator;
import com.easysidebar.utils.PinyinUtils;
import com.google.gson.Gson;
import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.entity.AreaCode;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AreaCodePresenter extends BasePresenter<AreaCodeView> {

    AreaCodePresenter(AreaCodeView view) {
        super(view);
    }

    public void loadData() {
        mCompositeSubscription.add(Observable.just("areacode.json")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(this::showLoading)
                .observeOn(Schedulers.io())
                .map(this::parseData)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(this::dismissLoading)
                .doOnError(throwable -> dismissLoading())
                .subscribe(list -> mView.onParseData(list)));
    }

    private List<AreaCode> parseData(String file) {
        List<AreaCode> list = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = Utils.getApp().getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray data = new JSONArray(stringBuilder.toString());
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AreaCode areaCode = gson.fromJson(data.optJSONObject(i).toString(), AreaCode.class);
                areaCode.setName(areaCode.getCnname());
                String pinyin = PinyinUtils.getPingYin(areaCode.getName());
                String sortString = pinyin.substring(0, 1).toUpperCase();
                if (sortString.matches("[A-Z]")) {
                    areaCode.setSortLetters(sortString.toUpperCase());
                } else {
                    areaCode.setSortLetters("#");
                }
                list.add(areaCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.sort(list, new PinyinComparator<>());
        return list;
    }
}
