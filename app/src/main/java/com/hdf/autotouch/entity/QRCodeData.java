package com.hdf.autotouch.entity;

public class QRCodeData {

    private int    type;
    private String data;

    public QRCodeData() {
    }

    public QRCodeData(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
