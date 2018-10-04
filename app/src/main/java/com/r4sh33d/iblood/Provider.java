package com.r4sh33d.iblood;

import retrofit2.Retrofit;

public class Provider {
    private static Retrofit authRetrofitInstance;
    private static Retrofit dataRetrofitInstance;

    public static Retrofit provideAuthRetrofitInstance() {
        if (authRetrofitInstance == null) {
            authRetrofitInstance = RetrofitClient.buildAuthRetrofit();
        }
        return authRetrofitInstance;
    }


    public static Retrofit provideDataRetrofitInstance() {
        if (dataRetrofitInstance == null) {
            dataRetrofitInstance = RetrofitClient.builDataRetrofit();
        }
        return dataRetrofitInstance;
    }



    public static <T> T provideRetrofitService(final Retrofit retrofit, Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
