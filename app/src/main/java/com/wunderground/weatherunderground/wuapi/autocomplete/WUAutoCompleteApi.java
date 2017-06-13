package com.wunderground.weatherunderground.wuapi.autocomplete;

import com.wunderground.weatherunderground.wuapi.model.autocomplete.AutoCompleteResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * See https://www.wunderground.com/weather/api/d/docs?d=autocomplete-api&MR=1
 */

public interface WUAutoCompleteApi {

    @GET("aq")
    Single<AutoCompleteResponse> autoComplete(@Query("query") String searchString);

}
