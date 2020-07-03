package com.hdf.autotouch.http;

import android.os.Message;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.config.HttpConfig.ErrorCode;
import com.hdf.autotouch.entity.HttpResult;
import com.hdf.autotouch.ui.login.LoginActivity;
import com.hdf.autotouch.util.SPManager;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Response;
import rx.Subscriber;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : HttpCallback
 * </pre>
 */
public abstract class HttpCallback<T> extends Subscriber<Response<HttpResult<T>>> {

    private BasePresenter mPresenter;
    private boolean       mShowLoading = true;

    protected HttpCallback(BasePresenter presenter) {
        mPresenter = presenter;
    }

    protected HttpCallback(BasePresenter presenter, boolean showLoading) {
        mPresenter = presenter;
        mShowLoading = showLoading;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected()) {
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            onErrors(new ExceptionHandler.ResponseThrowable(null, ErrorCode.NETWORK_ERROR, StringUtils.getString(R.string.http_network_error)));
        } else {
            if (mShowLoading) {
                mPresenter.showLoading();
            }
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onNext(Response<HttpResult<T>> response) {
        if (mShowLoading) {
            mPresenter.dismissLoading();
        }
        if (response.isSuccessful()) {
            String token = response.headers().get("Access-Token");
            if (!StringUtils.isEmpty(token)) {
                SPManager.setToken(token);
            }
            try {
                HttpResult<T> httpResult = response.body();
                assert httpResult != null;
                int status = httpResult.getCode();
                if (status == ErrorCode.SUCCESS) {
                    onResponse(httpResult.getData());
                } else {
                    if (status == ErrorCode.LOGIN_ERROR) {
                        SPManager.clearUser();
                        Message msg = new Message();
                        msg.what = R.id.msg_update_mine;
                        EventBus.getDefault().post(msg);
                        LoginActivity.start(ActivityUtils.getTopActivity());
                    }
                    onErrors(new ExceptionHandler.ResponseThrowable(null, httpResult.getCode(), httpResult.getMessage()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                onErrors(new ExceptionHandler.ResponseThrowable(e, ErrorCode.PARSE_ERROR, StringUtils.getString(R.string.http_parse_error)));
            }
        } else {
            onErrors(new ExceptionHandler.ResponseThrowable(null, ErrorCode.FAILURE, String.valueOf(response.code())));
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onErrors(new ExceptionHandler().handleException(e));
    }

    public void onErrors(ExceptionHandler.ResponseThrowable throwable) {
        if (mShowLoading) {
            mPresenter.dismissLoading();
        }
        if (!TextUtils.isEmpty(throwable.message) && AppUtils.isAppForeground()) {
            ToastUtils.showShort(throwable.message);
        }
    }

    public abstract void onResponse(T response);
}
