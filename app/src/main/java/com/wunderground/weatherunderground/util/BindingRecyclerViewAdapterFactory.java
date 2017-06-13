package com.wunderground.weatherunderground.util;

/**
 * Created by anton on 19.01.17.
 */

import android.support.v7.widget.RecyclerView;

import com.wunderground.weatherunderground.archi.adapter.BindingAdapterDelegate;
import com.wunderground.weatherunderground.archi.mvvm.BindingRecyclerAdapter;

public interface BindingRecyclerViewAdapterFactory {
    <D> BindingRecyclerAdapter<D> create(RecyclerView recyclerView, BindingAdapterDelegate<D> arg);

    BindingRecyclerViewAdapterFactory DEFAULT = new BindingRecyclerViewAdapterFactory() {
        @Override
        public <D> BindingRecyclerAdapter<D> create(RecyclerView recyclerView, BindingAdapterDelegate<D> arg) {
            return new BindingRecyclerAdapter<>(arg);
        }
    };
}
