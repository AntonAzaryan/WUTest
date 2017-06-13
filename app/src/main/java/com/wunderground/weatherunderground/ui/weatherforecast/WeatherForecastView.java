package com.wunderground.weatherunderground.ui.weatherforecast;

import com.wunderground.weatherunderground.archi.mvvm.MvvmView;

/**
 * Created by Anton Azaryan on 11.06.2017.
 */

public interface WeatherForecastView extends MvvmView {

    void showError(Throwable e);

}
