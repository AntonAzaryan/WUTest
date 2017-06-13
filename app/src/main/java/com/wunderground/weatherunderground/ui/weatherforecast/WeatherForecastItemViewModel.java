package com.wunderground.weatherunderground.ui.weatherforecast;

import android.databinding.Bindable;

import com.wunderground.weatherunderground.BR;
import com.wunderground.weatherunderground.archi.mvvm.BaseItemViewModel;
import com.wunderground.weatherunderground.archi.mvvm.MvvmItemView;
import com.wunderground.weatherunderground.weather.ForecastDayModel;

/**
 * Created by Anton Azaryan on 12.06.2017.
 */

public class WeatherForecastItemViewModel extends BaseItemViewModel<
        MvvmItemView,
        ForecastDayModel> {

    String day;
    String temperature;
    String weatherIconUrl;

    @Override
    protected void onBindDataModel(int position, ForecastDayModel data) {
        setDay(data.getPeriod());
        setTemperature(data.getForecastText());
        setWeatherIconUrl(data.getIconUrl());
    }

    @Bindable
    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
        notifyPropertyChanged(BR.weatherIconUrl);
    }

    @Bindable
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
        notifyPropertyChanged(BR.day);
    }

    @Bindable
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
        notifyPropertyChanged(BR.temperature);
    }
}
