package com.hdf.autotouch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Order implements Parcelable {

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    private             String                    orderId;
    private             String                    pId;
    private             String                    uId;
    private             int                       num;
    private             String                    amount;
    private             String                    sendWay;
    private             String                    afterService;
    private             String                    buyerMessage;
    private             String                    trusteeFee;
    /**
     * 订单状态 0-未支付 1-已取消 2-已付款 3-已绑定
     */
    private             int                       status;
    private             long                      creatTime;
    private             long                      payTime;
    private             String                    productTitle;
    private             String                    balance;
    private             Address                   address;
    private             List<OrderGoods>          list;

    public Order() {
    }

    protected Order(Parcel in) {
        this.orderId = in.readString();
        this.pId = in.readString();
        this.uId = in.readString();
        this.num = in.readInt();
        this.amount = in.readString();
        this.sendWay = in.readString();
        this.afterService = in.readString();
        this.buyerMessage = in.readString();
        this.trusteeFee = in.readString();
        this.status = in.readInt();
        this.creatTime = in.readLong();
        this.payTime = in.readLong();
        this.productTitle = in.readString();
        this.balance = in.readString();
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.list = new ArrayList<OrderGoods>();
        in.readList(this.list, OrderGoods.class.getClassLoader());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSendWay() {
        return sendWay;
    }

    public void setSendWay(String sendWay) {
        this.sendWay = sendWay;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getTrusteeFee() {
        return trusteeFee;
    }

    public void setTrusteeFee(String trusteeFee) {
        this.trusteeFee = trusteeFee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(long creatTime) {
        this.creatTime = creatTime;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderGoods> getList() {
        return list;
    }

    public void setList(List<OrderGoods> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.pId);
        dest.writeString(this.uId);
        dest.writeInt(this.num);
        dest.writeString(this.amount);
        dest.writeString(this.sendWay);
        dest.writeString(this.afterService);
        dest.writeString(this.buyerMessage);
        dest.writeString(this.trusteeFee);
        dest.writeInt(this.status);
        dest.writeLong(this.creatTime);
        dest.writeLong(this.payTime);
        dest.writeString(this.productTitle);
        dest.writeString(this.balance);
        dest.writeParcelable(this.address, flags);
        dest.writeList(this.list);
    }
}
