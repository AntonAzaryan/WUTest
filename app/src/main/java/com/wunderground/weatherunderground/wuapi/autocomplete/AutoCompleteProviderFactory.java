package com.wunderground.weatherunderground.wuapi.autocomplete;

import com.google.gson.GsonBuilder;
import com.wunderground.weatherunderground.BuildConfig;
import com.wunderground.weatherunderground.retrofit.ServiceGenerator;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anton Azaryan on 05.06.2017.
 */

public class AutoCompleteProviderFactory {

    public WUAutoCompleteProvider create() {
        return new WUAutoCompleteProvider(
                new ServiceGenerator()
                        .baseUrl(BuildConfig.AUTOCOMPLETE_API_BASE_URL)
                        .converterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                        .generate(WUAutoCompleteApi.class));
    }

}
