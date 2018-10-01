package com.r4sh33d.iblood;


import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    boolean isDebug = BuildConfig.DEBUG;

    private static  final  String AUTH_BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/relyingparty";
    private static final String DATABASE_BASE_URL = "";




    Retrofit build(PrefsUtils prefsUtils, String baseURL) {
        //String accessToken = prefsUtils.getString(Constants.PREF_KEY_ACCESS_TOKEN, "");
        String accessToken = Constants.API_KEY;
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(getHttpClient(accessToken))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getHttpClient(final String accessToken) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();
                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("key", accessToken)
                            .build();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
    }
}
