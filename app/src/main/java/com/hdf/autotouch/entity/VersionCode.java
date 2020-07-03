package com.hdf.autotouch.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class VersionCode implements Parcelable {

    /**
     * versionNum : 2.0.1
     * createTime : 123456789
     * status : 1
     * content : 测试安卓版本
     * versionUrl : sdfsdfsd
     */

    private String versionNum;
    private long    createTime;
    private int    status;
    private String content;
    private String versionUrl;

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.versionNum);
        dest.writeLong(this.createTime);
        dest.writeInt(this.status);
        dest.writeString(this.content);
        dest.writeString(this.versionUrl);
    }

    public VersionCode() {
    }

    protected VersionCode(Parcel in) {
        this.versionNum = in.readString();
        this.createTime = in.readLong();
        this.status = in.readInt();
        this.content = in.readString();
        this.versionUrl = in.readString();
    }

    public static final Parcelable.Creator<VersionCode> CREATOR = new Parcelable.Creator<VersionCode>() {
        @Override
        public VersionCode createFromParcel(Parcel source) {
            return new VersionCode(source);
        }

        @Override
        public VersionCode[] newArray(int size) {
            return new VersionCode[size];
        }
    };
}
