package com.wunderground.weatherunderground.archi.mvvm;

import android.content.Context;

/**
 * Created by anton on 23.01.17.
 */

public abstract class BaseItemViewModel<V extends MvvmItemView, D> extends BaseViewModel<Context, V> {

    public D dataModel;
    public int adapterPosition;

    void bindDataModel(int position, D model) {
        dataModel = model;
        adapterPosition = position;
        onBindDataModel(position, model);
    }

    protected void onBindDataModel(int position, D data) {
    }

    public D getDataModel() {
        return dataModel;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

}
