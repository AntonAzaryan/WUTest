package com.wunderground.weatherunderground.ui;

import com.wunderground.weatherunderground.archi.mvvm.BaseActivityViewModel;

/**
 * Created by Anton Azaryan on 12.06.2017.
 */

public class MainViewModel extends BaseActivityViewModel<MainView> {

    public void onCurrentWeatherClick() {
        getView().showCurrentWeather();
    }

    public void onSearchWeatherClick() {
        getView().showSearchWeather();
    }

}