package com.hdf.autotouch.http.download;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : ProgressModel
 * </pre>
 */
public class ProgressModel implements Parcelable {

    public static final Creator<ProgressModel> CREATOR = new Creator<ProgressModel>() {
        @Override
        public ProgressModel createFromParcel(Parcel source) {
            return new ProgressModel(source);
        }

        @Override
        public ProgressModel[] newArray(int size) {
            return new ProgressModel[size];
        }
    };
    private             long                   currentBytes;
    private             long                   contentLength;
    private             boolean                done    = false;

    public ProgressModel(long currentBytes, long contentLength, boolean done) {
        this.currentBytes = currentBytes;
        this.contentLength = contentLength;
        this.done = done;
    }

    public ProgressModel() {
    }

    protected ProgressModel(Parcel in) {
        this.currentBytes = in.readLong();
        this.contentLength = in.readLong();
        this.done = in.readByte() != 0;
    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.currentBytes);
        dest.writeLong(this.contentLength);
        dest.writeByte(this.done ? (byte) 1 : (byte) 0);
    }
}
