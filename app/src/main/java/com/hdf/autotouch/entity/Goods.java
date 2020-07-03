package com.hdf.autotouch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Goods implements Parcelable {

    public static final Parcelable.Creator<Goods> CREATOR = new Parcelable.Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };
    private             String                    pId;
    private             String                    pTitle;
    private             String                    pName;
    private             String                    pLittlePic;
    private             String                    price;
    private             String                    stock;
    private             List<String>              slideshowList;
    private             String                    detail;
    private             String                    fixTime;

    public Goods() {
    }

    protected Goods(Parcel in) {
        this.pId = in.readString();
        this.pTitle = in.readString();
        this.pName = in.readString();
        this.pLittlePic = in.readString();
        this.price = in.readString();
        this.stock = in.readString();
        this.slideshowList = in.createStringArrayList();
        this.detail = in.readString();
        this.fixTime = in.readString();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpLittlePic() {
        return pLittlePic;
    }

    public void setpLittlePic(String pLittlePic) {
        this.pLittlePic = pLittlePic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<String> getSlideshowList() {
        return slideshowList;
    }

    public void setSlideshowList(List<String> slideshowList) {
        this.slideshowList = slideshowList;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFixTime() {
        return fixTime;
    }

    public void setFixTime(String fixTime) {
        this.fixTime = fixTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pId);
        dest.writeString(this.pTitle);
        dest.writeString(this.pName);
        dest.writeString(this.pLittlePic);
        dest.writeString(this.price);
        dest.writeString(this.stock);
        dest.writeStringList(this.slideshowList);
        dest.writeString(this.detail);
        dest.writeString(this.fixTime);
    }
}
