package com.wunderground.weatherunderground.ui;

import com.wunderground.weatherunderground.archi.mvvm.MvvmView;

/**
 * Created by Anton Azaryan on 12.06.2017.
 */

public interface MainView extends MvvmView {

    void showCurrentWeather();

    void showSearchWeather();

}
