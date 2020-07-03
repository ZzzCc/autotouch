package com.hdf.autotouch.util;

import com.blankj.utilcode.util.SPUtils;

public class SPManager {

    private static final SPUtils SP_UTILS = SPUtils.getInstance("frog");

    public static String getToken() {
        return SP_UTILS.getString("token", "");
    }

    public static void setToken(String token) {
        SP_UTILS.put("token", token);
    }

    public static String getId() {
        return SP_UTILS.getString("id", "");
    }

    public static void setId(String id) {
        SP_UTILS.put("id", id);
    }

    public static String getUserName() {
        return SP_UTILS.getString("userName", "");
    }

    public static void setUserName(String userName) {
        SP_UTILS.put("userName", userName);
    }

    public static String getAreaCode() {
        return SP_UTILS.getString("areaCode", "");
    }

    public static void setAreaCode(String areaCode) {
        SP_UTILS.put("areaCode", areaCode);
    }

    public static boolean getHavePayPassword() {
        return SP_UTILS.getBoolean("havePayPassword", false);
    }

    public static void setHavePayPassword(boolean havePayPassword) {
        SP_UTILS.put("havePayPassword", havePayPassword);
    }

    public static String getInviteCode() {
        return SP_UTILS.getString("inviteCode", "");
    }

    public static void setInviteCode(String inviteCode) {
        SP_UTILS.put("inviteCode", inviteCode);
    }

    /**
     * 用户的算力等级(0：暂无身份 1:服务器 2: 矿池 3:矿场 4:社区节点 5:城市节点 6:超级节点:7：全球节点)
     */
    public static String getLevel() {
        return SP_UTILS.getString("level", "");
    }

    public static void setLevel(String level) {
        SP_UTILS.put("level", level);
    }

    public static long getCreateTime() {
        return SP_UTILS.getLong("createTime", 0);
    }

    public static void setCreateTime(long createTime) {
        SP_UTILS.put("createTime", createTime);
    }

    public static boolean getIsAgree() {
        return SP_UTILS.getBoolean("isAgree", false);
    }

    public static void setIsAgree(boolean isAgree) {
        SP_UTILS.put("isAgree", isAgree);
    }

    public static void clear() {
        SP_UTILS.clear();
    }

    public static void setUser(String id, String userName, String areaCode, boolean havePayPassword, String inviteCode, String level, long createTime) {
        setId(id);
        setUserName(userName);
        setAreaCode(areaCode);
        setHavePayPassword(havePayPassword);
        setInviteCode(inviteCode);
        setLevel(level);
        setCreateTime(createTime);
    }

    public static void clearUser() {
        setToken("");
        setId("");
        setUserName("");
        setAreaCode("");
        setHavePayPassword(false);
        setInviteCode("");
        setLevel("");
        setCreateTime(0);
        setIsAgree(false);
    }
}
