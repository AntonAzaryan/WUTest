package com.wunderground.weatherunderground.wuapi.model.geolookup;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anton Azaryan on 30.05.2017.
 */

public class WULocation {

    @SerializedName("type")
    public String type;

    @SerializedName("country")
    public String country;

    @SerializedName("country_iso3166")
    public String countryIso3166;

    @SerializedName("country_name")
    public String countryName;

    @SerializedName("state")
    public String state;

    @SerializedName("city")
    public String city;

    @SerializedName("tz_short")
    public String tzShort;

    @SerializedName("tz_long")
    public String tzLong;

    @SerializedName("lat")
    public String lat;

    @SerializedName("lon")
    public String lon;

    @SerializedName("zip")
    public String zip;

    @SerializedName("magic")
    public String magic;

    @SerializedName("wmo")
    public String wmo;

    @SerializedName("l")
    public String l;

    @SerializedName("requesturl")
    public String requesturl;

    @SerializedName("wuiurl")
    public String wuiurl;
}
