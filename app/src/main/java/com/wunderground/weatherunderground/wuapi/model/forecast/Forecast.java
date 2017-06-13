package com.wunderground.weatherunderground.wuapi.model.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {

    @SerializedName("txt_forecast")
    public TxtForecast txtForecast;

    @SerializedName("simpleforecast")
    public Simpleforecast simpleforecast;

    public class TxtForecast {
        public String date;
        public List<Forecastday> forecastday;
    }

    public class Simpleforecast {
        public List<SimpleForecastday> forecastday;
    }

}