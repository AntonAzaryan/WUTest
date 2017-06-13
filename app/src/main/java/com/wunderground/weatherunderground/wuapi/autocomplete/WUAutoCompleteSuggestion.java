package com.wunderground.weatherunderground.wuapi.autocomplete;

import com.wunderground.weatherunderground.weather.AutoCompleteSuggestion;
import com.wunderground.weatherunderground.wuapi.model.autocomplete.SearchCityResult;

/**
 * Created by Anton Azaryan on 11.06.2017.
 */

public class WUAutoCompleteSuggestion implements AutoCompleteSuggestion {

    String cityName;
    String query;

    WUAutoCompleteSuggestion(SearchCityResult searchCityResult) {
        cityName = searchCityResult.name;
        query = searchCityResult.l;
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public String getCityQuery() {
        return query;
    }

}
