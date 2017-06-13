package com.wunderground.weatherunderground.wuapi.autocomplete;

import com.wunderground.weatherunderground.weather.AutoCompleteSuggestion;
import com.wunderground.weatherunderground.wuapi.model.autocomplete.SearchCityResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by Anton Azaryan on 05.06.2017.
 */

public class WUAutoCompleteProvider {

    private final WUAutoCompleteApi autoCompleteApi;

    public WUAutoCompleteProvider(WUAutoCompleteApi api) {
        autoCompleteApi = api;
    }

    public Single<List<AutoCompleteSuggestion>> suggestionsList(String searchString) {
        return autoCompleteApi.autoComplete(searchString)
                .flatMapObservable(autoCompleteResponse -> Observable.fromIterable(autoCompleteResponse.results))
                .map((Function<SearchCityResult, AutoCompleteSuggestion>) WUAutoCompleteSuggestion::new)
                .toList();
    }
}
