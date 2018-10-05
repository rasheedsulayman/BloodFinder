package com.r4sh33d.iblood.network;


import com.r4sh33d.iblood.BuildConfig;

import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    boolean isDebug = BuildConfig.DEBUG;

    public static final String API_KEY = "AIzaSyAvC5cvSGIvrxmqN0qIKvj-M6IqV6VkXcQ";
    private static final String AUTH_BASE_URL = "https://www.googleapis.com/";
    private static final String DATABASE_BASE_URL = "https://iblood-7253a.firebaseio.com/";


    public static Retrofit buildAuthRetrofit() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", API_KEY);
        return build(AUTH_BASE_URL, queryParams);
    }

    public static Retrofit builDataRetrofit() {
        HashMap<String, String> queryParams = new HashMap<>();
        //queryParams.put("key", API_KEY); TODO come back and add authentication to secure the API
        return build(DATABASE_BASE_URL, queryParams);
    }


    public static Retrofit build(String baseURL, HashMap<String, String> queryParams) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(getHttpClient(queryParams))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getHttpClient(final HashMap<String, String> params) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();
                    HttpUrl.Builder urlBuilder = originalHttpUrl.newBuilder();
                    for (String key : params.keySet()) {
                        urlBuilder.addQueryParameter(key, params.get(key));
                    }
                    HttpUrl url = urlBuilder.build();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }).build();
    }
}
