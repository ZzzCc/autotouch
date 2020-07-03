package com.hdf.autotouch.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {

    private String txId;
    private String txFrom;
    private String txTo;
    private String amount;
    /**
     * 交易类型 5-提现 6-充币
     */
    private int    type;
    private String contract;
    private String fee;
    private long   timeStamp;
    private String note;
    /**
     * 状态 0-审核中 1-成功 -1-撤销
     */
    private int    status;
    private String coin;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getTxFrom() {
        return txFrom;
    }

    public void setTxFrom(String txFrom) {
        this.txFrom = txFrom;
    }

    public String getTxTo() {
        return txTo;
    }

    public void setTxTo(String txTo) {
        this.txTo = txTo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
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

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.txId);
        dest.writeString(this.txFrom);
        dest.writeString(this.txTo);
        dest.writeString(this.amount);
        dest.writeInt(this.type);
        dest.writeString(this.contract);
        dest.writeString(this.fee);
        dest.writeLong(this.timeStamp);
        dest.writeString(this.note);
        dest.writeInt(this.status);
        dest.writeString(this.coin);
    }

    public Transaction() {
    }

    protected Transaction(Parcel in) {
        this.txId = in.readString();
        this.txFrom = in.readString();
        this.txTo = in.readString();
        this.amount = in.readString();
        this.type = in.readInt();
        this.contract = in.readString();
        this.fee = in.readString();
        this.timeStamp = in.readLong();
        this.note = in.readString();
        this.status = in.readInt();
        this.coin = in.readString();
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
