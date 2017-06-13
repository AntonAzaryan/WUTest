package com.wunderground.weatherunderground.ui.searchweather;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.view.View;

import com.wunderground.weatherunderground.BR;
import com.wunderground.weatherunderground.application.App;
import com.wunderground.weatherunderground.archi.adapter.BindingAdapterDelegate;
import com.wunderground.weatherunderground.archi.mvvm.BaseActivityViewModel;
import com.wunderground.weatherunderground.ui.MainActivity;
import com.wunderground.weatherunderground.weather.AutoCompleteSuggestion;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anton Azaryan on 10.06.2017.
 */

public class SearchViewModel extends BaseActivityViewModel<SearchView> implements SearchItemViewModel.OnCitySelectListener {

    public final List<AutoCompleteSuggestion> citiesList = new ObservableArrayList<>();

    public final BindingAdapterDelegate<AutoCompleteSuggestion> bindingDelegate;
    private SearchItemSelector itemSelector;

    int loadingVisible = View.GONE;
    Disposable searchDisposable;

    SearchViewModel() {
        itemSelector = new SearchItemSelector(this);
        bindingDelegate = BindingAdapterDelegate.of(itemSelector);
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().searchIntent()
                .doOnNext(s -> setLoadingVisible(View.VISIBLE))
                .observeOn(Schedulers.io())
                .switchMap(suggestions())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AutoCompleteSuggestion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        searchDisposable = d;
                    }

                    @Override
                    public void onNext(List<AutoCompleteSuggestion> cities) {
                        setLoadingVisible(View.GONE);
                        if (!citiesList.isEmpty()) {
                            citiesList.clear();
                        }

                        if (!cities.isEmpty()) {
                            citiesList.addAll(cities);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().showError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        searchDisposable.dispose();
    }

    Function<String, ObservableSource<List<AutoCompleteSuggestion>>> suggestions() {
        return searchString -> {
            if (!searchString.isEmpty()) {
                return App.getInstance().getAutoCompleteProvider().suggestionsList(searchString)
                        .toObservable();
            }
            return Observable.just(Collections.emptyList());
        };
    }

    @Override
    public void onCitySelected(SearchItemViewModel viewModel) {
        final String query= viewModel.dataModel.getCityQuery();
        final String locationName= viewModel.dataModel.getCityName();
        MainActivity.launch(getContext(), query, locationName);
    }

    @Bindable
    public int getLoadingVisible() {
        return loadingVisible;
    }

    public void setLoadingVisible(int loadingVisible) {
        this.loadingVisible = loadingVisible;
        notifyPropertyChanged(BR.loadingVisible);
    }
}
