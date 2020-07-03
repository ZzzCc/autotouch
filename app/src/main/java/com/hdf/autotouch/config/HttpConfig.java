package com.hdf.autotouch.config;

public class HttpConfig {

    public static final int HTTP_TIMEOUT = 30 * 1000;

    public static String HTTP_BASE_URL = "https://web.rrskcl.com:8080/";
    //Test
//    public static String HTTP_BASE_URL = "https://web.rrskcl.com:8888/";
    //CS
//    public static String HTTP_BASE_URL = "http://192.168.2.154:9999/";
    //YZH
//    public static String HTTP_BASE_URL = "http://192.168.2.8:8080/";

    public class ErrorCode {
        /**
         * 成功
         */
        public static final int SUCCESS       = 200;
        /**
         * 失败
         */
        public static final int FAILURE       = 400;
        /**
         * 未知错误
         */
        public static final int UNKNOWN_ERROR = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR   = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 证书错误
         */
        public static final int SSL_ERROR     = 1003;
        /**
         * 登录异常
         */
        public static final int LOGIN_ERROR   = 1111;
        /**
         * 支付超时异常
         */
        public static final int PAYMENT_ERROR = 1019;
    }

}
