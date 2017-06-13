package com.wunderground.weatherunderground.ui.widget.compaund;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.wunderground.weatherunderground.R;

/**
 * Created by Anton Azaryan on 12.06.2017.
 */

public class WeatherDetailsView extends LinearLayoutCompat {

    ImageView icon;
    TextView detailType;
    TextView detail;

    public WeatherDetailsView(Context context) {
        super(context);
    }

    public WeatherDetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_weather_details, this);
        icon = (ImageView) findViewById(R.id.icon);
        detail = (TextView) findViewById(R.id.detail);
        detailType = (TextView) findViewById(R.id.detail_type);
    }

    public void setIconDawable(Drawable drawable) {
        icon.setImageDrawable(drawable);
    }

    public void setDetailType(String type) {
        detailType.setText(type);
    }

    public void setDetailText(String text) {
        detail.setText(text);
    }
}
