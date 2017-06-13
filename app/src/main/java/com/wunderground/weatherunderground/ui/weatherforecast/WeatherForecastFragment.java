package com.wunderground.weatherunderground.ui.weatherforecast;

import android.databinding.OnRebindCallback;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.transition.TransitionManager;
import android.view.ViewGroup;

import com.wunderground.weatherunderground.R;
import com.wunderground.weatherunderground.archi.mvvm.BaseViewModelFragment;
import com.wunderground.weatherunderground.archi.mvvm.MvvmView;
import com.wunderground.weatherunderground.databinding.WeatherForecastBinding;

/**
 * Created by Anton Azaryan on 11.06.2017.
 */

public class WeatherForecastFragment extends BaseViewModelFragment<
        WeatherForecastBinding,
        WeatherForecastViewModel> implements WeatherForecastView {

    public static WeatherForecastFragment instance(String cityQuery, String locationName) {
        WeatherForecastFragment weatherForecastFragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putString("QUERY", cityQuery);
        args.putString("LOCATION", locationName);
        weatherForecastFragment.setArguments(args);
        return weatherForecastFragment;
    }

    @Override
    protected WeatherForecastViewModel onCreateViewModel() {
        return new WeatherForecastViewModel();
    }

    @Override
    protected MvvmView getMvvmViewContract() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_weather_forecast;
    }

    @Override
    protected void onBindingInflated(WeatherForecastBinding dataBinding) {
        super.onBindingInflated(dataBinding);

        dataBinding.addOnRebindCallback(new OnRebindCallback<WeatherForecastBinding>() {
            @Override
            public boolean onPreBind(WeatherForecastBinding binding) {
                TransitionManager.beginDelayedTransition(
                        (ViewGroup) binding.getRoot());
                return super.onPreBind(binding);
            }
        });
    }

    @Override
    public void showError(Throwable e) {
        Snackbar.make(
                getBinding().getRoot(),
                e.toString(),
                Snackbar.LENGTH_SHORT).show();
    }
}
