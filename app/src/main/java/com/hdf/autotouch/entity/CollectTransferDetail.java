package com.hdf.autotouch.entity;

public class CollectTransferDetail {

    private String txId;
    private String txFrom;
    private String txTo;
    private String amount;
    private String contract;
    private String fee;
    private long   timeStamp;
    private String note;
    /**
     * 状态0- 失败 1-成功
     */
    private int    status;
    /**
     * 1- 转入 2-转出
     */
    private int    different;
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

    public int getDifferent() {
        return different;
    }

    public void setDifferent(int different) {
        this.different = different;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}
