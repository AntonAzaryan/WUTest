package com.wunderground.weatherunderground.wuapi;

import android.location.Location;

import com.wunderground.weatherunderground.wuapi.model.conditions.CurrentObservation;
import com.wunderground.weatherunderground.wuapi.model.forecast.Forecast;
import com.wunderground.weatherunderground.wuapi.model.geolookup.WULocation;

import io.reactivex.Single;

/**
 * Created by Anton Azaryan on 30.05.2017.
 */

public class WUWeatherDataProvider {

    private final WeatherUndergroundApi wuApi;

    public WUWeatherDataProvider(WeatherUndergroundApi api) {
        wuApi = api;
    }

    public Single<CurrentObservation> currentWeather(String locationQuery) {
        return wuApi.conditions(locationQuery)
                .map(baseResponse -> baseResponse.currentObservation);
    }

    public Single<Forecast> forecast10day(String locationQuery) {
        return wuApi.forecast10day(locationQuery)
                .map(baseResponse -> baseResponse.forecast);
    }

    public Single<WULocation> geoLookup(Location location) {
        if (location == null) {
            return Single.just(new WULocation());
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        return wuApi.geoLookup(latitude, longitude)
                .map(baseResponse -> baseResponse.location);
    }
}
