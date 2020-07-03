package com.hdf.autotouch.ui.autotouch;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.hdf.autotouch.base.BaseActivity;
import com.hdf.autotouch.myservice.MyAccessibilityService;
import com.hdf.autotouch.util.PermissionHelper;
import com.hdf.autotouch.util.ServiceUtils;

import java.security.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcAutoTouch extends BaseActivity<AutoTouchPresenter> {

    private static final int REQUEST_CODE_WRITE_SETTINGS = 1;
    @BindView(R.id.btn)
    Button btn;

    public static void start(Context context) {
        Intent intent = new Intent(context, AcAutoTouch.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.ac_auto_touch;
    }

    @Override
    public void initView() {
        applyClickListener(btn);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onViewClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btn:
                if (ServiceUtils.isAccessibilitySettingsOn(mActivity, MyAccessibilityService.class)) {
                    ToastUtils.showShort("无障碍服务已经启动");
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Settings.System.canWrite(this)) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                    Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
                        } else {
                            Settings.Secure.putString(getContentResolver(),
                                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                                    "com.hdf.autotouch/com.hdf.autotouch.myservice.MyAccessibilityService");
                            Settings.Secure.putInt(getContentResolver(),
                                    Settings.Secure.ACCESSIBILITY_ENABLED, 1);
                        }
                    }
                }else {
                    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
