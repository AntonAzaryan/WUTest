package com.wunderground.weatherunderground.wuapi;

import com.google.gson.GsonBuilder;
import com.wunderground.weatherunderground.BuildConfig;
import com.wunderground.weatherunderground.retrofit.ServiceGenerator;
import com.wunderground.weatherunderground.wuapi.WUWeatherDataProvider;
import com.wunderground.weatherunderground.wuapi.WeatherUndergroundApi;

import java.util.Locale;

import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anton Azaryan on 30.05.2017.
 */

public class WeatherProviderFactory {

    public WUWeatherDataProvider create() {
        return new WUWeatherDataProvider(
                new ServiceGenerator()
                        .baseUrl(BuildConfig.WEATHER_API_BASE_URL)
                        .converterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                        .interceptor(chain -> {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder();

                            requestBuilder
                                    .method(original.method(), original.body())
                                    .url(injectLanguage(original.url()));

                            return chain.proceed(requestBuilder.build());
                        })
                        .generate(WeatherUndergroundApi.class));
    }

    HttpUrl injectLanguage(HttpUrl originalHttpUrl) {
        String url = originalHttpUrl.toString();

        url = url.replace("settings", getSettingsSegments());

        return originalHttpUrl.newBuilder(url).build();
    }

    String getSettingsSegments() {
        return "lang:" + Locale.getDefault().getLanguage().toUpperCase();
    }

}
