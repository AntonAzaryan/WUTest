package com.wunderground.weatherunderground.wuapi;

import com.wunderground.weatherunderground.weather.ForecastDayModel;
import com.wunderground.weatherunderground.wuapi.model.forecast.Forecastday;

/**
 * Created by Anton Azaryan on 12.06.2017.
 */

public class WUForecastDayModel implements ForecastDayModel {

    final String period;
    final String iconUrl;
    final String forecastText;

    public WUForecastDayModel(Forecastday forecastday) {
        period = forecastday.title;
        iconUrl = forecastday.iconUrl;
        forecastText = forecastday.fcttextMetric;
    }

    @Override
    public String getPeriod() {
        return period;
    }

    @Override
    public String getIconUrl() {
        return iconUrl;
    }

    @Override
    public String getForecastText() {
        return forecastText;
    }
}
