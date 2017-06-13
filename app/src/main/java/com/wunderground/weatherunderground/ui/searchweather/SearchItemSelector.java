package com.wunderground.weatherunderground.ui.searchweather;

import com.android.databinding.library.baseAdapters.BR;
import com.wunderground.weatherunderground.R;
import com.wunderground.weatherunderground.archi.adapter.EmptyItemViewSelector;
import com.wunderground.weatherunderground.archi.adapter.ItemView;
import com.wunderground.weatherunderground.archi.mvvm.BaseItemViewModel;
import com.wunderground.weatherunderground.weather.AutoCompleteSuggestion;

/**
 * Created by Anton Azaryan on 11.06.2017.
 */

public class SearchItemSelector extends EmptyItemViewSelector<AutoCompleteSuggestion> {

    SearchViewModel viewModel;

    SearchItemSelector(SearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onSelectItemView(ItemView itemView, int position, AutoCompleteSuggestion item) {
        itemView.setBindingVariable(BR.viewModel);
        itemView.setLayoutRes(R.layout.item_search_city_suggestion);
    }

    @Override
    public BaseItemViewModel<?, AutoCompleteSuggestion> onCreateViewModel(int itemViewType) {
        return new SearchItemViewModel()
                .setListener(viewModel);
    }
}
