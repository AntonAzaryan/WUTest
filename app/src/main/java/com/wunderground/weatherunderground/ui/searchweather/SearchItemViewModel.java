package com.wunderground.weatherunderground.ui.searchweather;

import android.databinding.Bindable;

import com.wunderground.weatherunderground.BR;
import com.wunderground.weatherunderground.archi.mvvm.BaseItemViewModel;
import com.wunderground.weatherunderground.archi.mvvm.MvvmItemView;
import com.wunderground.weatherunderground.weather.AutoCompleteSuggestion;

/**
 * Created by Anton Azaryan on 11.06.2017.
 */

public class SearchItemViewModel extends BaseItemViewModel<MvvmItemView, AutoCompleteSuggestion> {

    String cityName;
    private OnCitySelectListener listener;

    @Override
    protected void onBindDataModel(int position, AutoCompleteSuggestion data) {
        setCityName(dataModel.getCityName());
    }

    @Bindable
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
        notifyPropertyChanged(BR.cityName);
    }

    public void onCitySelected() {
        if (listener != null) {
            listener.onCitySelected(this);
        }
    }

    public SearchItemViewModel setListener(OnCitySelectListener listener) {
        this.listener = listener;
        return this;
    }

    interface OnCitySelectListener {
        void onCitySelected(SearchItemViewModel viewModel);
    }
}
