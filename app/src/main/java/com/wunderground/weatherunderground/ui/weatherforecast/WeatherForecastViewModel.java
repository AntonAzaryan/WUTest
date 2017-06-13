package com.wunderground.weatherunderground.ui.weatherforecast;

import android.app.Activity;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wunderground.weatherunderground.BR;
import com.wunderground.weatherunderground.R;
import com.wunderground.weatherunderground.application.App;
import com.wunderground.weatherunderground.archi.adapter.BindingAdapterDelegate;
import com.wunderground.weatherunderground.archi.mvvm.BaseFragmentViewModel;
import com.wunderground.weatherunderground.util.Utils;
import com.wunderground.weatherunderground.weather.ForecastDayModel;
import com.wunderground.weatherunderground.wuapi.WUForecastDayModel;
import com.wunderground.weatherunderground.wuapi.WUWeatherDataProvider;
import com.wunderground.weatherunderground.wuapi.model.conditions.CurrentObservation;
import com.wunderground.weatherunderground.wuapi.model.forecast.Forecastday;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Anton Azaryan on 11.06.2017.
 */

public class WeatherForecastViewModel extends BaseFragmentViewModel<WeatherForecastView> implements SwipeRefreshLayout.OnRefreshListener {

    public final List<ForecastDayModel> forecastList = new ObservableArrayList<>();
    public final BindingAdapterDelegate<ForecastDayModel> bindingDelegate;
    private WeatherForecastItemSelector itemSelector;

    public final View.OnClickListener onNavigationBackListener =
            v -> WeatherForecastViewModel.this.getContext().onBackPressed();

    Disposable weatherDataDisposable;
    boolean currentLocation;

    String locationQuery;
    String locationName;

    String weatherIconUrl;
    String temperature;
    String feelsLikeTemperature;
    String weather;

    String wind;
    String humidity;
    String pressure;
    String visibility;

    boolean updating;

    WeatherForecastViewModel() {
        itemSelector = new WeatherForecastItemSelector(this);
        bindingDelegate = BindingAdapterDelegate.of(itemSelector);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            locationName = getContext().getString(R.string.current_weather);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getFragment().getArguments();
            if (args != null) {
                locationQuery = args.getString("QUERY");
                setLocationName(args.getString("LOCATION"));
            }
            currentLocation = TextUtils.isEmpty(locationQuery);

            if(currentLocation) {
                checkLocationPermission();
            } else {
                updateCurrentWeather(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (weatherDataDisposable != null) {
            weatherDataDisposable.dispose();
        }
    }

    Single<String> location(boolean refresh) {
        if (currentLocation) {
            return App.getInstance().getCurrentLocationManager().location(refresh)
                    .map(wuLocation -> wuLocation.l);
        }
        return Single.just(locationQuery);
    }

    void checkLocationPermission() {
        final Activity activity = getContext();
        Dexter.withActivity(activity)
                .withPermissions(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!report.areAllPermissionsGranted()) {
                            new AlertDialog.Builder(activity)
                                    .setTitle(R.string.permission_request_title)
                                    .setMessage(R.string.permission_request_body)
                                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                                    .show();
                        } else {
                            updateCurrentWeather(false);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }

    void updateCurrentWeather(boolean refresh) {
        setUpdating(true);
        location(refresh)
                .observeOn(Schedulers.io())
                .flatMap(locationQuery -> {
                    final WUWeatherDataProvider weatherDataProvider =
                            App.getInstance().getWeatherDataProvider();

                    final Single<CurrentObservation> currentWeather =
                            weatherDataProvider.currentWeather(locationQuery);

                    final Single<List<ForecastDayModel>> forecast10Days =
                            weatherDataProvider.forecast10day(locationQuery)
                                    .map(forecast -> forecast.txtForecast.forecastday)
                                    .flatMapObservable(Observable::fromIterable)
                                    .map((Function<Forecastday, ForecastDayModel>) WUForecastDayModel::new)
                                    .toList();

                    return currentWeather.zipWith(forecast10Days, WeatherForecastCombinedResult::new);
                })
                .doFinally(() -> setUpdating(false))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<WeatherForecastCombinedResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        weatherDataDisposable = d;
                    }

                    @Override
                    public void onSuccess(WeatherForecastCombinedResult result) {
                        onBindWeatherModel(result.currentObservation);
                        onBindForecastModel(result.forecast);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().showError(e);
                        }
                    }
                });
    }

    void onBindForecastModel(List<ForecastDayModel> forecast) {
        if (!forecastList.isEmpty()) {
            forecastList.clear();
        }
        forecastList.addAll(forecast);
    }

    void onBindWeatherModel(CurrentObservation currentObservation) {
        setWeatherIconUrl(currentObservation.iconUrl);
        final int temperature = (int) Math.round(currentObservation.tempC);
        final int feelsLike = Math.round(currentObservation.feelslikeC);
        setTemperature(Utils.tempIntToString(temperature));
        setFeelsLikeTemperature(Utils.tempIntToString(feelsLike));
        setWeather(currentObservation.weather);

        setWind(currentObservation.windString);
        setHumidity(currentObservation.relativeHumidity);
        setVisibility(currentObservation.visibilityKm + " km");
        setPressure(currentObservation.pressureIn + " inHg");
    }

    @Override
    public void onRefresh() {
        if(currentLocation) {
            setUpdating(false);
            checkLocationPermission();
        } else {
            updateCurrentWeather(true);
        }
    }

    class WeatherForecastCombinedResult {
        CurrentObservation currentObservation;
        List<ForecastDayModel> forecast;

        WeatherForecastCombinedResult(CurrentObservation currentObservation, List<ForecastDayModel> forecast) {
            this.currentObservation = currentObservation;
            this.forecast = forecast;
        }
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
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
        notifyPropertyChanged(BR.temperature);
    }

    @Bindable
    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
        notifyPropertyChanged(BR.weather);
    }

    @Bindable
    public String getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public void setFeelsLikeTemperature(String feelsLikeTemperature) {
        this.feelsLikeTemperature = feelsLikeTemperature;
        notifyPropertyChanged(BR.feelsLikeTemperature);
    }

    @Bindable
    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
        notifyPropertyChanged(BR.wind);
    }

    @Bindable
    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
        notifyPropertyChanged(BR.humidity);
    }

    @Bindable
    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
        notifyPropertyChanged(BR.visibility);
    }

    @Bindable
    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
        notifyPropertyChanged(BR.pressure);
    }

    @Bindable
    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
        notifyPropertyChanged(BR.updating);
    }

    @Bindable
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
        notifyPropertyChanged(BR.locationName);
    }
}
