package com.wunderground.weatherunderground.wuapi.model.autocomplete;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anton Azaryan on 05.06.2017.
 */

public class AutoCompleteResponse {

    @SerializedName("RESULTS")
    public List<SearchCityResult> results;

}
