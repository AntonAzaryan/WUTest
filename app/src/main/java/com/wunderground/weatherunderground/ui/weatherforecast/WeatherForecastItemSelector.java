package com.wunderground.weatherunderground.ui.weatherforecast;

import com.android.databinding.library.baseAdapters.BR;
import com.wunderground.weatherunderground.R;
import com.wunderground.weatherunderground.archi.adapter.EmptyItemViewSelector;
import com.wunderground.weatherunderground.archi.adapter.ItemView;
import com.wunderground.weatherunderground.archi.mvvm.BaseItemViewModel;
import com.wunderground.weatherunderground.weather.ForecastDayModel;

/**
 * Created by Anton Azaryan on 11.06.2017.
 */

public class WeatherForecastItemSelector extends EmptyItemViewSelector<ForecastDayModel> {

    WeatherForecastViewModel viewModel;

    WeatherForecastItemSelector(WeatherForecastViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onSelectItemView(ItemView itemView, int position, ForecastDayModel item) {
        itemView.setBindingVariable(BR.viewModel);
        itemView.setLayoutRes(R.layout.item_weather_forecast_day);
    }

    @Override
    public BaseItemViewModel<?, ForecastDayModel> onCreateViewModel(int itemViewType) {
        return new WeatherForecastItemViewModel();
    }
}
