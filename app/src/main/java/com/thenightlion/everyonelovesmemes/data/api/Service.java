package com.thenightlion.everyonelovesmemes.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Service mInstance;
    private final static String BASE_URL = "https://demo3161256.mockable.io/";
    private Retrofit mRetrofit;

    private Service() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build();
    }

    public static Service getInstance() {
        if (mInstance == null) {
            mInstance = new Service();
        }
        return mInstance;
    }

    public AuthApi getAuthApi() {
        return mRetrofit.create(AuthApi.class);
    }

    public MemesApi getMemesApi() {
        return mRetrofit.create(MemesApi.class);
    }

    public LogoutApi getLogoutApi() {
        return mRetrofit.create(LogoutApi.class);
    }
}
