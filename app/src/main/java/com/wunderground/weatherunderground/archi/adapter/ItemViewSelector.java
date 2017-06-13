package com.wunderground.weatherunderground.archi.adapter;

import com.wunderground.weatherunderground.archi.mvvm.BaseItemViewModel;

public interface ItemViewSelector<D> {

    void onSelectItemView(ItemView itemView, int position, D item);

    BaseItemViewModel<?, D> onCreateViewModel(int itemViewType);
}