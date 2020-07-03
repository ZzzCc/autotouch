package com.hdf.autotouch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MinePool implements Parcelable {


    /**
     * all : 0.000
     * allDate : 1234563456456
     * list : [{"deductAsString":"待挖矿","mac":"IPFS12745"},{"deductAsString":"待挖矿","mac":"IPFS12745"}]
     * today : 0.000
     * todayDate : 1234563456456
     */

    private String all;
    private long           allDate;
    private String         today;
    private long           todayDate;
    private List<ListBean> list;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public long getAllDate() {
        return allDate;
    }

    public void setAllDate(long allDate) {
        this.allDate = allDate;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public long getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(long todayDate) {
        this.todayDate = todayDate;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * deductAsString : 待挖矿
         * mac : IPFS12745
         */

        private String deductAsString;
        private String mac;

        public String getDeductAsString() {
            return deductAsString;
        }

        public void setDeductAsString(String deductAsString) {
            this.deductAsString = deductAsString;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.deductAsString);
            dest.writeString(this.mac);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.deductAsString = in.readString();
            this.mac = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.all);
        dest.writeLong(this.allDate);
        dest.writeString(this.today);
        dest.writeLong(this.todayDate);
        dest.writeList(this.list);
    }

    public MinePool() {
    }

    protected MinePool(Parcel in) {
        this.all = in.readString();
        this.allDate = in.readLong();
        this.today = in.readString();
        this.todayDate = in.readLong();
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MinePool> CREATOR = new Parcelable.Creator<MinePool>() {
        @Override
        public MinePool createFromParcel(Parcel source) {
            return new MinePool(source);
        }

        @Override
        public MinePool[] newArray(int size) {
            return new MinePool[size];
        }
    };
}
