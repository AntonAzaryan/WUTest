package com.wunderground.weatherunderground.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ServiceGenerator {

    private static final int TIMEOUT_DEFAULT = 15;

    private Retrofit.Builder mBuilder = new Retrofit.Builder()
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create());

    private OkHttpClient.Builder mHttpClientBuilder =
            new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS);

    public ServiceGenerator baseUrl(String baseUrl) {
        mBuilder.baseUrl(baseUrl);
        return this;
    }

    public ServiceGenerator interceptor(Interceptor interceptor) {
        mHttpClientBuilder.addInterceptor(interceptor);
        return this;
    }

    public ServiceGenerator networkInterceptor(Interceptor interceptor) {
        mHttpClientBuilder.addNetworkInterceptor(interceptor);
        return this;
    }

    public ServiceGenerator converterFactory(Converter.Factory converterFactory) {
        mBuilder.addConverterFactory(converterFactory);
        return this;
    }

    public <S> S generate(Class<S> serviceClass) {
        Retrofit retrofit = mBuilder.client(mHttpClientBuilder
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .build();
        return retrofit.create(serviceClass);
    }

}
