package com.hdf.autotouch.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Exchange implements Parcelable {

    private String uid;
    private String exId;
    private String inCoin;
    private String outCoin;
    private String inAmount;
    private String outAmount;
    private String rate;
    private String fee;
    private String feeRate;
    private long   timeStamp;
    private String note;
    private int    status;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }

    public String getInCoin() {
        return inCoin;
    }

    public void setInCoin(String inCoin) {
        this.inCoin = inCoin;
    }

    public String getOutCoin() {
        return outCoin;
    }

    public void setOutCoin(String outCoin) {
        this.outCoin = outCoin;
    }

    public String getInAmount() {
        return inAmount;
    }

    public void setInAmount(String inAmount) {
        this.inAmount = inAmount;
    }

    public String getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(String outAmount) {
        this.outAmount = outAmount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.exId);
        dest.writeString(this.inCoin);
        dest.writeString(this.outCoin);
        dest.writeString(this.inAmount);
        dest.writeString(this.outAmount);
        dest.writeString(this.rate);
        dest.writeString(this.fee);
        dest.writeString(this.feeRate);
        dest.writeLong(this.timeStamp);
        dest.writeString(this.note);
        dest.writeInt(this.status);
    }

    public Exchange() {
    }

    protected Exchange(Parcel in) {
        this.uid = in.readString();
        this.exId = in.readString();
        this.inCoin = in.readString();
        this.outCoin = in.readString();
        this.inAmount = in.readString();
        this.outAmount = in.readString();
        this.rate = in.readString();
        this.fee = in.readString();
        this.feeRate = in.readString();
        this.timeStamp = in.readLong();
        this.note = in.readString();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<Exchange> CREATOR = new Parcelable.Creator<Exchange>() {
        @Override
        public Exchange createFromParcel(Parcel source) {
            return new Exchange(source);
        }

        @Override
        public Exchange[] newArray(int size) {
            return new Exchange[size];
        }
    };
}
