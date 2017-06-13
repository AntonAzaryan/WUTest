
package com.wunderground.weatherunderground.wuapi.model.forecast;


import com.google.gson.annotations.SerializedName;

public class SimpleForecastday extends Forecastday {

    @SerializedName("date")
    public Date date;

    @SerializedName("high")
    public HighLow high;

    @SerializedName("low")
    public HighLow low;

    @SerializedName("conditions")
    public String conditions;

    @SerializedName("skyicon")
    public String skyicon;

    @SerializedName("qpf_allday")
    public QpfSnow qpfAllday;

    @SerializedName("qpf_day")
    public QpfSnow qpfDay;

    @SerializedName("qpf_night")
    public QpfSnow qpfNight;

    @SerializedName("snow_allday")
    public QpfSnow snowAllday;

    @SerializedName("snow_day")
    public QpfSnow snowDay;

    @SerializedName("snow_night")
    public QpfSnow snowNight;

    @SerializedName("maxwind")
    public Wind maxwind;

    @SerializedName("avewind")
    public Wind avewind;

    @SerializedName("avehumidity")
    public int avehumidity;

    @SerializedName("maxhumidity")
    public int maxhumidity;

    @SerializedName("minhumidity")
    public int minhumidity;

    public class QpfSnow {
        public double in;
        public double mm;
    }

    public class Wind {
        public int mph;
        public int kph;
        public String dir;
        public int degrees;
    }

    public class HighLow {
        public String fahrenheit;
        public String celsius;
    }
}
