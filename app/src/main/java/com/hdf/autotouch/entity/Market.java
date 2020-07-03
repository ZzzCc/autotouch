package com.hdf.autotouch.entity;

public class Market {

    private String icon;
    private String symbol;
    private String cnyPrice;
    private String usdtPrice;
    private String change_daily;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCnyPrice() {
        return cnyPrice;
    }

    public void setCnyPrice(String cnyPrice) {
        this.cnyPrice = cnyPrice;
    }

    public String getUsdtPrice() {
        return usdtPrice;
    }

    public void setUsdtPrice(String usdtPrice) {
        this.usdtPrice = usdtPrice;
    }

    public String getChange_daily() {
        return change_daily;
    }

    public void setChange_daily(String change_daily) {
        this.change_daily = change_daily;
    }
}
