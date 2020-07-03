package com.hdf.autotouch.http.download;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : ProgressResponseBody
 * </pre>
 */
public class ProgressResponseBody extends ResponseBody {

    private static final int UPDATE = 0x01;

    private ResponseBody     responseBody;
    private ProgressListener mListener;
    private BufferedSource   bufferedSource;
    private Handler          myHandler;

    ProgressResponseBody(ResponseBody body, ProgressListener listener) {
        responseBody = body;
        mListener = listener;
        if (myHandler == null) {
            myHandler = new MyHandler();
        }
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {

        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(mySource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source mySource(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                Message msg = Message.obtain();
                msg.what = UPDATE;
                msg.obj = new ProgressModel(totalBytesRead, contentLength(), totalBytesRead == contentLength());
                myHandler.sendMessage(msg);
                return bytesRead;
            }
        };


    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler {

        MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    if (mListener != null) {
                        mListener.onProgress(progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                    }
                    break;
                default:
                    break;
            }
        }

    }
}
