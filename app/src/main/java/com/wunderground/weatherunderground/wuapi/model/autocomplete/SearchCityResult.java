package com.wunderground.weatherunderground.wuapi.model.autocomplete;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anton Azaryan on 05.06.2017.
 */

public class SearchCityResult {

    @SerializedName("name")
    public String name;

    @SerializedName("type")
    public String type;

    @SerializedName("c")
    public String c;

    @SerializedName("zmw")
    public String zmw;

    @SerializedName("tz")
    public String tz;

    @SerializedName("tzs")
    public String tzs;

    @SerializedName("l")
    public String l;

    @SerializedName("lat")
    public String lat;

    @SerializedName("lon")
    public String lon;
    
}
