package com.wunderground.weatherunderground.wuapi;

import com.wunderground.weatherunderground.wuapi.model.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Anton Azaryan on 30.05.2017.
 * <p>
 * https://www.wunderground.com/weather/api/d/docs?d=data/index&MR=1
 * <p>
 * Path string "settings" will be replaced(using interceptor) on lang:XX based on device locale.
 */

public interface WeatherUndergroundApi {

    @GET("conditions/settings{query}.json")
    Single<BaseResponse> conditions(@Path(value = "query", encoded = true) String query);

    @GET("forecast10day/settings{query}.json")
    Single<BaseResponse> forecast10day(@Path(value = "query", encoded = true) String query);

    @GET("geolookup/settings/q/{latitude},{longitude}.json")
    Single<BaseResponse> geoLookup(@Path("latitude") double latitude,
                                   @Path("longitude") double longitude);

}
