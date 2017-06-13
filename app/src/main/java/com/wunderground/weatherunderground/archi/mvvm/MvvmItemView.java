package com.wunderground.weatherunderground.archi.mvvm;

import android.databinding.ViewDataBinding;

/**
 * Created by Anton Azaryan on 11.01.2017.
 */

public interface MvvmItemView extends MvvmView {

    interface Factory {
        MvvmItemView createItemView(ViewDataBinding binding);
    }

    void onViewRecycled();

    void onViewAttachedToWindow();

    void onViewDetachedFromWindow();
}
