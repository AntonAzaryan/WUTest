
package com.wunderground.weatherunderground.wuapi.model.forecast;


import com.google.gson.annotations.SerializedName;

public class Date {

    @SerializedName("epoch")
    public String epoch;

    @SerializedName("pretty")
    public String pretty;

    @SerializedName("day")
    public int day;

    @SerializedName("month")
    public int month;

    @SerializedName("year")
    public int year;

    @SerializedName("yday")
    public int yday;

    @SerializedName("hour")
    public int hour;

    @SerializedName("min")
    public String min;

    @SerializedName("sec")
    public int sec;

    @SerializedName("isdst")
    public String isdst;

    @SerializedName("monthname")
    public String monthname;

    @SerializedName("weekday_short")
    public String weekdayShort;

    @SerializedName("weekday")
    public String weekday;

    @SerializedName("ampm")
    public String ampm;

    @SerializedName("tz_short")
    public String tzShort;

    @SerializedName("tz_long")
    public String tzLong;

}
