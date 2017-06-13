package com.wunderground.weatherunderground.ui.searchweather;

import com.wunderground.weatherunderground.archi.mvvm.MvvmView;

import io.reactivex.Observable;

/**
 * Created by Anton Azaryan on 10.06.2017.
 */

public interface SearchView extends MvvmView {

    Observable<String> searchIntent();

    void showError(Throwable e);
}
