package com.hdf.autotouch.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
    private             String                      aId;
    private             String                      userName;
    private             String                      prefix;
    private             String                      receiptPhone;

    public Address() {
    }

    protected Address(Parcel in) {
        this.aId = in.readString();
        this.userName = in.readString();
        this.prefix = in.readString();
        this.receiptPhone = in.readString();
    }

    public String getAId() {
        return aId;
    }

    public void setAId(String aId) {
        this.aId = aId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getReceiptPhone() {
        return receiptPhone;
    }

    public void setReceiptPhone(String receiptPhone) {
        this.receiptPhone = receiptPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.aId);
        dest.writeString(this.userName);
        dest.writeString(this.prefix);
        dest.writeString(this.receiptPhone);
    }
}
