package com.hdf.autotouch.entity;

public class User {

    /**
     * 用户手机号
     */
    private String  account;
    /**
     * 号码归属地前缀
     */
    private String  prefix;
    /**
     * 用户唯一标识
     */
    private String  uid;
    /**
     * 父类唯一标识
     */
    private String  preUid;
    /**
     * 创建时间
     */
    private long    createTime;
    /**
     * 修改时间
     */
    private long    updateTime;
    /**
     * 来源
     */
    private String  comeFrom;
    /**
     * 邀请码
     */
    private String  inviteCode;
    /**
     * 父类邀请码
     */
    private String  inviteYards;
    /**
     * 账户状态(1:正常 0:冻结)
     */
    private int     status;
    /**
     * 用户的算力等级(0:暂无身份 1:服务器 2:矿池 3:矿场 4:社区节点 5:城市节点 6:超级节点 7:全球节点)
     */
    private int     level;
    /**
     * 是否设置支付密码
     */
    private boolean whetherPaypassword;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPreUid() {
        return preUid;
    }

    public void setPreUid(String preUid) {
        this.preUid = preUid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteYards() {
        return inviteYards;
    }

    public void setInviteYards(String inviteYards) {
        this.inviteYards = inviteYards;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isWhetherPaypassword() {
        return whetherPaypassword;
    }

    public void setWhetherPaypassword(boolean whetherPaypassword) {
        this.whetherPaypassword = whetherPaypassword;
    }
}
