package com.wunderground.weatherunderground.archi.adapter;

import com.wunderground.weatherunderground.archi.mvvm.BaseItemViewModel;

/**
 * Created by Anton Azaryan on 11.01.2017.
 */

public class BindingAdapterDelegate<D> {

    public static <D> BindingAdapterDelegate<D> of(ItemView itemView) {
        return new BindingAdapterDelegate<>(itemView);
    }

    public static <D> BindingAdapterDelegate<D> of(ItemViewSelector<D> selector) {
        return new BindingAdapterDelegate<>(selector);
    }

    private final ItemView mItemView;
    private final ItemViewSelector<D> mItemViewSelector;

    private BindingAdapterDelegate(ItemView itemView) {
        this.mItemView = itemView;
        this.mItemViewSelector = new EmptyItemViewSelector<>();
    }

    private BindingAdapterDelegate(ItemViewSelector<D> selector) {
        this.mItemView = new ItemView();
        this.mItemViewSelector = selector;
    }

    public void select(int position, D item) {
        mItemViewSelector.onSelectItemView(mItemView, position, item);
    }

    public int bindingVariable() {
        return mItemView.bindingVariable();
    }

    public int layoutRes() {
        return mItemView.layoutRes();
    }

    public BaseItemViewModel<?, D> obtainViewModel(int itemViewType) {
        return mItemViewSelector.onCreateViewModel(itemViewType);
    }

}
