package com.mojka.poisk.data.api;

import com.mojka.poisk.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIGenerator {
    private static final String TAG = "APIGenerator";
    public static final String API_HOST = "http://automoykionl.1gb.ru";
    private static final String API_BASE_URL = "http://automoykionl.1gb.ru/api/v1/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

    static {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.DEBUG)
            httpClient.addInterceptor(loggingInterceptor);
    }

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
//        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}