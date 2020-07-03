package com.hdf.autotouch.ui.scan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.config.Constant;
import com.hdf.autotouch.entity.QRCodeData;
import com.hdf.autotouch.ui.transfercoin.TransferCoinActivity;
import com.hdf.autotouch.util.PermissionHelper;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2019/7/13
 *     desc  : 扫一扫
 * </pre>
 */
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {

    @BindView(R.id.zxing_view)
    ZXingView mZxingView;

    public static void start(Context context) {
        PermissionHelper.request(() -> {
            Intent intent = new Intent(context, ScanActivity.class);
            ActivityUtils.startActivity(intent);
        }, PermissionConstants.CAMERA);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZxingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZxingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
    }

    @Override
    protected void onStop() {
        mZxingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZxingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_scan;
    }

    @Override
    public void initView() {
        setTitleText(R.string.scan);
        mZxingView.setDelegate(this);
    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onViewClick(@NonNull View view) {
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (isMatchJson(result)) {
            QRCodeData qrCodeData = GsonUtils.fromJson(result, QRCodeData.class);
            if (!ObjectUtils.isEmpty(qrCodeData)) {
                if (qrCodeData.getType() == Constant.QRCODE_TYPE_COLLECT_COIN) {
                    if (isLogin()) {
                        TransferCoinActivity.start(this, qrCodeData.getData());
                        sendMessage(R.id.msg_scan_success, result);
                    }
                    finish();
                }
            } else {
                sendMessage(R.id.msg_scan_success, result);
                finish();
            }
        } else {
            sendMessage(R.id.msg_scan_success, result);
            finish();
        }
    }

    private boolean isMatchJson(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        return jsonElement.isJsonObject();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.showShort(R.string.scan_qrcode_open_camera_error);
    }
}
