package com.hdf.autotouch.config;

public class Constant {

    public static final String T_CAPTCHA_ID = "2099833942";

    public static final String URL_USER_AGREEMENT   = "https://web.rrskcl.com/web/h5/privacy.html";
    public static final String URL_ESCROW_AGREEMENT = "http://sa.gtse.fun:81/web/xmy/protocol.html"; //放弃
    public static final String URL_RECOMMEND        = "https://web.rrskcl.com/web/h5/invited.html?inviteCode=";
    public static final String HELP_CENTER_URL      = "https://web.rrskcl.com/web/h5/help.html";//正式
//    public static final String HELP_CENTER_URL      = "http://47.75.110.79/renren-h5/help.html";//测试

    public static final String REGEX_18           = "^(([1-9][0-9]*)|(([0]\\.\\d{1,18}|[1-9][0-9]*\\.\\d{1,18})))$";
    public static final String REGEX_4            = "^(([1-9][0-9]*)|(([0]\\.\\d{1,4}|[1-9][0-9]*\\.\\d{1,4})))$";
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[0-9])|(17[0,1,3,5-8])|(18[0-9])|(19[1,8,9]))\\d{8}$";

    public static final int SMS_WAIT_TIME  = 60;
    public static final int CLICK_INTERVAL = 666;

    public static final int QRCODE_TYPE_COLLECT_COIN = 1;
}
