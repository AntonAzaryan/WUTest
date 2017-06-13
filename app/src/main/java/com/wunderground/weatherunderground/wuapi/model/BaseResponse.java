package com.wunderground.weatherunderground.wuapi.model;

import com.google.gson.annotations.SerializedName;
import com.wunderground.weatherunderground.wuapi.model.conditions.CurrentObservation;
import com.wunderground.weatherunderground.wuapi.model.forecast.Forecast;
import com.wunderground.weatherunderground.wuapi.model.geolookup.WULocation;

/**
 * Created by Anton Azaryan on 30.05.2017.
 */

public class BaseResponse {

    @SerializedName("response")
    public ResponseInfo response;

    @SerializedName("error")
    public Error error;

    @SerializedName("current_observation")
    public CurrentObservation currentObservation;

    @SerializedName("forecast")
    public Forecast forecast;

    @SerializedName("location")
    public WULocation location;

    public class ResponseInfo {

        public String version;
        public String termsofService;
        public ResponseInfo.Features features;

        public class Features {
            public int geolookup;
            public int conditions;
            public int forecast;
            public int forecast10day;
        }
    }

    public class Error {
        public String type;
        public String description;
    }

}
