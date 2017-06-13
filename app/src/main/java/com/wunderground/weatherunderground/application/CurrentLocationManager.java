package com.wunderground.weatherunderground.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.TextUtils;

import com.wunderground.weatherunderground.wuapi.model.geolookup.WULocation;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Anton Azaryan on 04.06.2017.
 */

public class CurrentLocationManager {

    private LocationManager locationManager;

    @SuppressLint("MissingPermission")
    public CurrentLocationManager(Context context) {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    protected String getBestProviderName() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String providerName = locationManager.getBestProvider(criteria, true);
        return !TextUtils.isEmpty(providerName) ? providerName : LocationManager.NETWORK_PROVIDER;
    }

    public Single<WULocation> location(boolean refresh) {
        return Single.create(new SingleOnSubscribe<WULocation>() {

            SingleEmitter<WULocation> wuLocationEmitter;

            @SuppressLint("MissingPermission")
            @Override
            public void subscribe(SingleEmitter<WULocation> emitter) throws Exception {
                wuLocationEmitter = emitter;

                final String bestLocationProviderName = getBestProviderName();

                if (refresh) {
                    LocationListener locationListener = new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            locationManager.removeUpdates(this);
                            onCurrentLocationChanged(wuLocationEmitter, location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            switch (status) {
                                case LocationProvider.OUT_OF_SERVICE:
                                    wuLocationEmitter.onError(new RuntimeException("Can't find current location"));
                                    break;
                            }
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                        }
                    };
                    wuLocationEmitter.setDisposable(new LocationListenerDisposable(locationListener));
                    locationManager.requestSingleUpdate(bestLocationProviderName, locationListener,null);
                } else {
                    Location lastKnownLocation = locationManager.getLastKnownLocation(bestLocationProviderName);
                    onCurrentLocationChanged(wuLocationEmitter, lastKnownLocation);
                }
            }
        });
    }

    void onCurrentLocationChanged(SingleEmitter<WULocation> wuLocationEmitter, Location location) {
        App.getInstance().getWeatherDataProvider().geoLookup(location)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(wuLocationEmitter::onSuccess)
                .doOnError(wuLocationEmitter::onError)
                .subscribe();
    }

    class LocationListenerDisposable implements Disposable {

        final LocationListener locationListener;
        boolean disposed;

        LocationListenerDisposable(LocationListener locationListener) {
            this.locationListener = locationListener;
        }

        @Override
        public void dispose() {
            locationManager.removeUpdates(locationListener);
            disposed = true;
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }
    }
}
