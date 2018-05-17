package com.dxs.stc.utils.http;

import com.dxs.stc.base.UrlConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by hl at 2018/5/14
 * com.naple.hldemo.mvp.util
 */
public class RetrofitUtil {

    static OkHttpClient client = new OkHttpClient.Builder().
            retryOnConnectionFailure(true).
            connectTimeout(5, TimeUnit.SECONDS).
            readTimeout(10, TimeUnit.SECONDS).
            writeTimeout(10, TimeUnit.SECONDS).
            addNetworkInterceptor(new StethoInterceptor()).
            build();

    public static <T> T create(final Class<T> cls) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlConfig.baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                //适配RxJava2.0,RxJava1.x则为RxJavaCallAdapterFactory.create()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(cls);
    }
}
