package com.hdf.autotouch.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdf.autotouch.R;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;

import java.io.File;

public class ShareUtils {

    public static void shareImage(Activity activity, View view) {
        PermissionHelper.request(() -> {
            Bitmap bitmap = QMUIDrawableHelper.createBitmapFromView(view);
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, null, null));
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent = Intent.createChooser(intent, "");
            activity.startActivity(intent);
        }, PermissionConstants.STORAGE);
    }

    public static void save(Activity activity, View view) {
        PermissionHelper.request(() -> {
            Bitmap bitmap = QMUIDrawableHelper.createBitmapFromView(view);
            String path = Environment.getExternalStorageDirectory().getPath() + "/HRZK/image_" + TimeUtils.getNowMills() + ".jpg";
            File file = new File(path);
            ImageUtils.save(bitmap, path, Bitmap.CompressFormat.JPEG);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            activity.sendBroadcast(intent);
            ToastUtils.showShort(R.string.save_success);
        }, PermissionConstants.STORAGE);
    }
}
