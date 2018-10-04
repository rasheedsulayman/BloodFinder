package com.r4sh33d.iblood;

import android.content.Context;

import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Retrofit;

public class Provider {
    private static Retrofit authRetrofitInstance;
    private static Retrofit dataRetrofitInstance;
    private  static PrefsUtils prefsUtils;

    public static PrefsUtils providePrefManager(Context context) {
        if (prefsUtils == null) {
            prefsUtils = new PrefsUtils(context.getApplicationContext());
        }
        return prefsUtils;
    }

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
