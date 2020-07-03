package com.hdf.autotouch.entity;

import java.util.List;

public class MyAssets {

    private List<Transaction> list;
    /**
     * file : 3000
     * fileFreeze : 0.0000
     * usdt : 0
     * usdtFreeze : 0.0000
     * usdtValue : 14101.8
     */

    private String            file;
    private String fileFreeze;
    private String usdt;
    private String usdtFreeze;
    private String usdtValue;


    public List<Transaction> getList() {
        return list;
    }

    public void setList(List<Transaction> list) {
        this.list = list;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileFreeze() {
        return fileFreeze;
    }

    public void setFileFreeze(String fileFreeze) {
        this.fileFreeze = fileFreeze;
    }

    public String getUsdt() {
        return usdt;
    }

    public void setUsdt(String usdt) {
        this.usdt = usdt;
    }

    public String getUsdtFreeze() {
        return usdtFreeze;
    }

    public void setUsdtFreeze(String usdtFreeze) {
        this.usdtFreeze = usdtFreeze;
    }

    public String getUsdtValue() {
        return usdtValue;
    }

    public void setUsdtValue(String usdtValue) {
        this.usdtValue = usdtValue;
    }
}
