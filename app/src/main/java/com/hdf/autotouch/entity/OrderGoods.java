package com.hdf.autotouch.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderGoods implements Parcelable {

    public static final Parcelable.Creator<OrderGoods> CREATOR = new Parcelable.Creator<OrderGoods>() {
        @Override
        public OrderGoods createFromParcel(Parcel source) {
            return new OrderGoods(source);
        }

        @Override
        public OrderGoods[] newArray(int size) {
            return new OrderGoods[size];
        }
    };
    private             String                         serverName;
    private             String                         macAddress;
    private             String                         value;
    private             String                         fee;
    private             String                         icon;
    /**
     * 0-未绑定 1-已绑定
     */
    private             int                            status;

    public OrderGoods() {
    }

    protected OrderGoods(Parcel in) {
        this.serverName = in.readString();
        this.macAddress = in.readString();
        this.value = in.readString();
        this.fee = in.readString();
        this.icon = in.readString();
        this.status = in.readInt();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
        dest.writeString(this.serverName);
        dest.writeString(this.macAddress);
        dest.writeString(this.value);
        dest.writeString(this.fee);
        dest.writeString(this.icon);
        dest.writeInt(this.status);
    }
}
