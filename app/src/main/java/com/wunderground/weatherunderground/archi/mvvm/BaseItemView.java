package com.wunderground.weatherunderground.archi.mvvm;

import android.databinding.ViewDataBinding;
import android.os.Message;

/**
 * Created by Anton Azaryan on 08.03.2017.
 */

public abstract class BaseItemView<B extends ViewDataBinding> implements MvvmItemView {

    protected B binding;

    public BaseItemView(B binding) {
        this.binding = binding;
    }

    @Override
    public void onViewRecycled() {
    }

    @Override
    public void onViewAttachedToWindow() {
    }

    @Override
    public void onViewDetachedFromWindow() {
    }

    @Override
    public void postMessage(Message message) {
    }
}
