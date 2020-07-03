package com.hdf.autotouch.ui.version;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.blankj.utilcode.constant.PermissionConstants;
import com.hdf.autotouch.base.BasePresenter;
import com.hdf.autotouch.http.download.DownloadManager;
import com.hdf.autotouch.util.PermissionHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionUpdatePresenter extends BasePresenter<VersionUpdateView> {

    VersionUpdatePresenter(VersionUpdateView view) {
        super(view);
    }

    @SuppressLint("WrongConstant")
    public void downloadFile(final String url) {
        PermissionHelper.request(() -> DownloadManager.getInstance().downloadFileProgress(url,
                (currentBytes, contentLength, done) -> {
                    int progress = (int) (currentBytes * 100 / contentLength);
                    mView.updateProgress(progress);
                }, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //处理下载文件
                        try {
                            assert response.body() != null;
                            InputStream is = response.body().byteStream();
                            FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ipfs.apk"));
                            int len;
                            byte[] buffer = new byte[2048];
                            while (-1 != (len = is.read(buffer))) {
                                fos.write(buffer, 0, len);
                            }
                            fos.flush();
                            fos.close();
                            is.close();
                            mView.downloadFinish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mView.downloadFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mView.downloadFailure();
                    }
                }), PermissionConstants.STORAGE);
    }
}
