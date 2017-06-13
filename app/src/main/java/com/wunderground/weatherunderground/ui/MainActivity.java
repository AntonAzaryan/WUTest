package com.wunderground.weatherunderground.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.wunderground.weatherunderground.R;
import com.wunderground.weatherunderground.archi.mvvm.BaseViewModelActivity;
import com.wunderground.weatherunderground.databinding.ActivityMainBinding;
import com.wunderground.weatherunderground.ui.searchweather.SearchActivity;
import com.wunderground.weatherunderground.ui.weatherforecast.WeatherForecastFragment;

public class MainActivity extends BaseViewModelActivity<ActivityMainBinding, MainViewModel> implements MainView {

    public static void launch(Context context, String query, String locationName) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("QUERY", query);
        intent.putExtra("LOCATION", locationName);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel onCreateViewModel() {
        return new MainViewModel();
    }

    @Override
    protected MainView getMvvmViewContract() {
        return this;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        final String cityQuery = intent.getStringExtra("QUERY");
        final String locationName = intent.getStringExtra("LOCATION");

        addWeatherForecastFragment(WeatherForecastFragment.instance(cityQuery, locationName));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showCurrentWeather() {
        addWeatherForecastFragment(new WeatherForecastFragment());
    }

    void addWeatherForecastFragment(WeatherForecastFragment forecastFragment) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        android.R.animator.fade_in, android.R.animator.fade_out,
                        android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.root, forecastFragment)
                .addToBackStack(forecastFragment.getAlias())
                .commit();
    }

    @Override
    public void showSearchWeather() {
        startActivity(new Intent(this, SearchActivity.class));
    }
}
