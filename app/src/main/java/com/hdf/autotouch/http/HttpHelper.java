package com.hdf.autotouch.http;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.GsonBuilder;
import com.hdf.autotouch.config.HttpConfig;
import com.hdf.autotouch.util.EncryptUtils;
import com.hdf.autotouch.util.SPManager;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author: Zhongnan.Zhang
 *     e-mail: 2315136814@qq.com
 *     time  : 2018/7/3
 *     desc  : HttpHelper
 * </pre>
 */
public class HttpHelper {

    @SuppressLint("StaticFieldLeak")
    private static HttpHelper instance  = null;
    private        Retrofit   mRetrofit = null;

    private HttpHelper() {
        init();
    }

    public static HttpHelper getInstance() {
        if (instance == null) {
            instance = new HttpHelper();
        }
        return instance;
    }

    private void init() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(LogUtils::d);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(HttpConfig.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HttpConfig.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(HttpConfig.HTTP_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();

                    HttpUrl.Builder authorizedUrlBuilder = request.url()
                            .newBuilder();

                    String sign = getSign(request);
                    LogUtils.e(" sign = " + sign);

                    Request newRequest = request.newBuilder()
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("Access-Token", SPManager.getToken())
                            .addHeader("Access-Sign", sign)
                            .method(request.method(), request.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    return chain.proceed(newRequest);
                });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.HTTP_BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private String getSign(Request request) {
        RequestBody body = request.body();
        String sign = "";
        try {
            Map<String, String> map = new TreeMap<>(
                    String::compareTo);
            StringBuilder builder = new StringBuilder();
            builder.append(request.url().encodedPath()).append(":");
            if (body instanceof FormBody) {
                FormBody oldFormBody = (FormBody) body;
                for (int i = 0; i < oldFormBody.size(); i++) {
                    map.put(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                }
                Set<String> keySet = map.keySet();
                for (String key : keySet) {
                    builder.append(key).append(map.get(key));
                }
//                assert sign != null;
//                sign = sign.substring(0, sign.length() - 1);
            }
            builder.append("zhike_205");
            String s = EncodeUtils.urlDecode(builder.toString(), "UTF-8");
            LogUtils.e(s);
            sign = EncryptUtils.encryptSha256(s);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }

    public ApiService getServer() {
        return mRetrofit.create(ApiService.class);
    }
}
