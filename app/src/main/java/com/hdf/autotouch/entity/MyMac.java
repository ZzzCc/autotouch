package com.hdf.autotouch.entity;

import java.util.List;

public class MyMac {

    /**
     * level : 6
     * macModels : [{"mac":"00e06f0eebfd","deduct":1,"name":"小蚂蚁10001","fee":"6","month":"07"},{"mac":"00e06f0E22uz","deduct":1,"name":"小蚂蚁10002","fee":"6","month":"07"},{"mac":"00e06f085idC","deduct":1,"name":"小蚂蚁10003","fee":"6","month":"07"},{"mac":"00e06f0p5A80","deduct":1,"name":"小蚂蚁10004","fee":"6","month":"07"},{"mac":"00e06f0k5ub1","deduct":1,"name":"小蚂蚁10005","fee":"6","month":"07"},{"mac":"00e06f032427","deduct":1,"name":"小蚂蚁10006","fee":"6","month":"07"}]
     * macAccount : 6
     */

    private int                 level;
    private int                 macAccount;
    private List<MacModelsBean> macModels;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMacAccount() {
        return macAccount;
    }

    public void setMacAccount(int macAccount) {
        this.macAccount = macAccount;
    }

    public List<MacModelsBean> getMacModels() {
        return macModels;
    }

    public void setMacModels(List<MacModelsBean> macModels) {
        this.macModels = macModels;
    }

    public static class MacModelsBean {
        /**
         * mac : 00e06f0eebfd
         * deduct : 1
         * name : 小蚂蚁10001
         * fee : 6
         * month : 07
         */

        private String mac;
        private int    deduct;
        private String name;
        private String fee;
        private String month;

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public int getDeduct() {
            return deduct;
        }

        public void setDeduct(int deduct) {
            this.deduct = deduct;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
