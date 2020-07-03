package com.hdf.autotouch.util;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

import java.util.List;

public class PermissionHelper {

    public static void request(final OnPermissionGrantedListener grantedListener,
                               @PermissionConstants.Permission final String... permissions) {
        PermissionUtils.permission(permissions)
                .rationale(shouldRequest -> shouldRequest.again(true))
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        grantedListener.onPermissionGranted();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        request(grantedListener, permissions);
                    }
                })
                .request();
    }

    public static void request(final OnPermissionGrantedListener grantedListener,
                               final OnPermissionDeniedListener deniedListener,
                               @PermissionConstants.Permission final String... permissions) {
        PermissionUtils.permission(permissions)
                .rationale(shouldRequest -> shouldRequest.again(true))
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        grantedListener.onPermissionGranted();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        deniedListener.onPermissionDenied();
                        request(grantedListener, deniedListener, permissions);
                    }
                })
                .request();
    }

    public interface OnPermissionGrantedListener {
        void onPermissionGranted();
    }

    public interface OnPermissionDeniedListener {
        void onPermissionDenied();
    }
}
