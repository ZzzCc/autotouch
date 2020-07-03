package com.hdf.autotouch.http;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.JsonParseException;
import com.hdf.autotouch.R;
import com.hdf.autotouch.config.HttpConfig.ErrorCode;

import org.json.JSONException;

import java.net.ConnectException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : ExceptionHandler
 * </pre>
 */
public class ExceptionHandler {


    ExceptionHandler() {
    }

    public ResponseThrowable handleException(Throwable e) {
        ResponseThrowable throwable;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            throwable = new ResponseThrowable(e, httpException.code(), httpException.message());
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            throwable = new ResponseThrowable(resultException, resultException.code, resultException.message);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            throwable = new ResponseThrowable(e, ErrorCode.PARSE_ERROR, StringUtils.getString(R.string.http_parse_error));
        } else if (e instanceof ConnectException) {
            throwable = new ResponseThrowable(e, ErrorCode.NETWORK_ERROR, StringUtils.getString(R.string.http_network_error));
        } else if (e instanceof SSLHandshakeException) {
            throwable = new ResponseThrowable(e, ErrorCode.SSL_ERROR, StringUtils.getString(R.string.http_ssl_error));
        } else {
            throwable = new ResponseThrowable(e, ErrorCode.UNKNOWN_ERROR, StringUtils.getString(R.string.http_unknown_error));
        }
        return throwable;
    }

    public static class ResponseThrowable extends Exception {

        public int    code;
        public String message;

        ResponseThrowable(Throwable throwable, int code, String message) {
            super(throwable);
            this.code = code;
            this.message = message;
        }
    }

    class ServerException extends RuntimeException {
        int    code;
        String message;
    }
}
