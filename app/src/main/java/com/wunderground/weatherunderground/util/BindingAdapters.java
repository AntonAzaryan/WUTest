package com.wunderground.weatherunderground.util;

import android.databinding.BindingAdapter;
import android.support.annotation.ArrayRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Anton Azaryan on 08.06.2017.
 */

public class BindingAdapters {

    @BindingAdapter("onRefresh")
    public static void onSwipeRefreshListener(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @BindingAdapter("colorScheme")
    public static void setSwipeRefreshColorScheme(SwipeRefreshLayout swipeRefreshLayout, @ArrayRes int colorSchemeRes) {
        swipeRefreshLayout.setColorSchemeColors(swipeRefreshLayout.getContext()
                .getResources().getIntArray(colorSchemeRes));
    }

    @BindingAdapter({"navigationOnClick"})
    public static void onBindToolbarBackPressListener(Toolbar toolbar, View.OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }
}
