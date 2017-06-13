package com.wunderground.weatherunderground.archi.mvvm;

/**
 * Created by Anton Azaryan on 11.01.2017.
 */

public interface ViewModel {

    void attachMvvmView(MvvmView view);

    void detachMvvmView();

}
