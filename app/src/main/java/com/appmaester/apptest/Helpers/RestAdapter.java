package com.appmaester.apptest.Helpers;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nik on 4/23/2018.
 */

public class RestAdapter {

    static Retrofit retrofit;
    static OkHttpClient okHttpClient;

    public static API createAPI() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.cache(null);
        okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(API.class);
    }

    public static void cancel(){
        okHttpClient.dispatcher().cancelAll();
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }
}
