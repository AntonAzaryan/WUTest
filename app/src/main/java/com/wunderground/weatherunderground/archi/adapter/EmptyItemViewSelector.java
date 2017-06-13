package com.wunderground.weatherunderground.archi.adapter;

import com.wunderground.weatherunderground.archi.mvvm.BaseItemViewModel;

/**
 * Created by Anton Azaryan on 12.02.2017.
 */

public class EmptyItemViewSelector<T> implements ItemViewSelector<T> {

    @Override
    public void onSelectItemView(ItemView itemView, int position, T item) {
    }

    @Override
    public BaseItemViewModel<?, T> onCreateViewModel(int itemViewType) {
        return null;
    }
}
