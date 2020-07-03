package com.hdf.autotouch.http.download;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : ProgressListener
 * </pre>
 */
public interface ProgressListener {

    void onProgress(long currentBytes, long contentLength, boolean done);

}
