
package com.wunderground.weatherunderground.wuapi.model.forecast;


import com.google.gson.annotations.SerializedName;

public class Forecastday {

    @SerializedName("period")
    public int period;

    @SerializedName("icon")
    public String icon;

    @SerializedName("icon_url")
    public String iconUrl;

    @SerializedName("pop")
    public String pop;

    @SerializedName("title")
    public String title;

    @SerializedName("fcttext")
    public String fcttext;

    @SerializedName("fcttext_metric")
    public String fcttextMetric;

}
