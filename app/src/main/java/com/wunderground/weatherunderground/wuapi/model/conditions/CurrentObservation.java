package com.wunderground.weatherunderground.wuapi.model.conditions;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Anton Azaryan on 30.05.2017.
 */

public class CurrentObservation {

    @SerializedName("image")
    public Image image;

    @SerializedName("display_location")
    public DisplayLocation displayLocation;

    @SerializedName("observation_location")
    public ObservationLocation observationLocation;

    @SerializedName("estimated")
    public Estimated estimated;

    @SerializedName("station_id")
    public String stationId;

    @SerializedName("observation_time")
    public String observationTime;

    @SerializedName("observation_time_rfc822")
    public String observationTimeRfc822;

    @SerializedName("observation_epoch")
    public String observationEpoch;

    @SerializedName("local_time_rfc822")
    public String localTimeRfc822;

    @SerializedName("local_epoch")
    public String localEpoch;

    @SerializedName("local_tz_short")
    public String localTzShort;

    @SerializedName("local_tz_long")
    public String localTzLong;

    @SerializedName("local_tz_offset")
    public String localTzOffset;

    @SerializedName("weather")
    public String weather;

    @SerializedName("temperature_string")
    public String temperatureString;

    @SerializedName("temp_f")
    public double tempF;

    @SerializedName("temp_c")
    public double tempC;

    @SerializedName("relative_humidity")
    public String relativeHumidity;

    @SerializedName("wind_string")
    public String windString;

    @SerializedName("wind_dir")
    public String windDir;

    @SerializedName("wind_degrees")
    public int windDegrees;

    @SerializedName("wind_mph")
    public double windMph;

    @SerializedName("wind_gust_mph")
    public String windGustMph;

    @SerializedName("wind_kph")
    public double windKph;

    @SerializedName("wind_gust_kph")
    public String windGustKph;

    @SerializedName("pressure_mb")
    public String pressureMb;

    @SerializedName("pressure_in")
    public String pressureIn;

    @SerializedName("pressure_trend")
    public String pressureTrend;

    @SerializedName("dewpoint_string")
    public String dewpointString;

    @SerializedName("dewpoint_f")
    public int dewpointF;

    @SerializedName("dewpoint_c")
    public int dewpointC;

    @SerializedName("heat_index_string")
    public String heatIndexString;

    @SerializedName("heat_index_f")
    public String heatIndexF;

    @SerializedName("heat_index_c")
    public String heatIndexC;

    @SerializedName("windchill_string")
    public String windchillString;

    @SerializedName("windchill_f")
    public String windchillF;

    @SerializedName("windchill_c")
    public String windchillC;

    @SerializedName("feelslike_string")
    public String feelslikeString;

    @SerializedName("feelslike_f")
    public double feelslikeF;

    @SerializedName("feelslike_c")
    public float feelslikeC;

    @SerializedName("visibility_mi")
    public String visibilityMi;

    @SerializedName("visibility_km")
    public String visibilityKm;

    @SerializedName("solarradiation")
    public String solarradiation;

    @SerializedName("UV")
    public String uV;

    @SerializedName("precip_1hr_string")
    public String precip1hrString;

    @SerializedName("precip_1hr_in")
    public String precip1hrIn;

    @SerializedName("precip_1hr_metric")
    public String precip1hrMetric;

    @SerializedName("precip_today_string")
    public String precipTodayString;

    @SerializedName("precip_today_in")
    public String precipTodayIn;

    @SerializedName("precip_today_metric")
    public String precipTodayMetric;

    @SerializedName("icon")
    public String icon;

    @SerializedName("icon_url")
    public String iconUrl;

    @SerializedName("forecast_url")
    public String forecastUrl;

    @SerializedName("history_url")
    public String historyUrl;

    @SerializedName("ob_url")
    public String obUrl;

}