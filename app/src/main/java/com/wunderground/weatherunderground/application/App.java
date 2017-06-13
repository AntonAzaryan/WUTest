package com.wunderground.weatherunderground.application;

import android.app.Application;

import com.wunderground.weatherunderground.wuapi.WUWeatherDataProvider;
import com.wunderground.weatherunderground.wuapi.WeatherProviderFactory;
import com.wunderground.weatherunderground.wuapi.autocomplete.AutoCompleteProviderFactory;
import com.wunderground.weatherunderground.wuapi.autocomplete.WUAutoCompleteProvider;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by Anton Azaryan on 30.05.2017.
 */

public class App extends Application {

    private static App instance;

    static { //TODO find best practice to swallow rxJava2 exceptions ?
        RxJavaPlugins.setErrorHandler(throwable -> {
        });
    }

    private WUWeatherDataProvider weatherDataProvider;
    private WUAutoCompleteProvider autoCompleteProvider;
    private CurrentLocationManager currentLocationManager;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        weatherDataProvider = new WeatherProviderFactory().create();
        autoCompleteProvider = new AutoCompleteProviderFactory().create();
        currentLocationManager = new CurrentLocationManager(this);
    }

    public WUWeatherDataProvider getWeatherDataProvider() {
        return weatherDataProvider;
    }

    public WUAutoCompleteProvider getAutoCompleteProvider() {
        return autoCompleteProvider;
    }

    public CurrentLocationManager getCurrentLocationManager() {
        return currentLocationManager;
    }
}
