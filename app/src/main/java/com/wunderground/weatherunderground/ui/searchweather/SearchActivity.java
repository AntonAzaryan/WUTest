package com.wunderground.weatherunderground.ui.searchweather;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.EditText;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.wunderground.weatherunderground.R;
import com.wunderground.weatherunderground.archi.mvvm.BaseViewModelActivity;
import com.wunderground.weatherunderground.archi.mvvm.MvvmView;
import com.wunderground.weatherunderground.databinding.ActivitySearchBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Anton Azaryan on 04.06.2017.
 */

public class SearchActivity extends BaseViewModelActivity<ActivitySearchBinding, SearchViewModel> implements SearchView {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchViewModel onCreateViewModel() {
        return new SearchViewModel();
    }

    @Override
    protected MvvmView getMvvmViewContract() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivitySearchBinding binding = getBinding();

        binding.search.requestFocus();
        EditText et = (EditText) binding.search.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        et.setHintTextColor(getResources().getColor(R.color.color_white_t77));
        et.setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public Observable<String> searchIntent() {
        return RxSearchView.queryTextChanges(getBinding().search)
                .skip(1) //skip first emission
                .debounce(400, TimeUnit.MILLISECONDS)
                .map(charSequence -> charSequence.toString().trim())
                .filter(charSequence -> charSequence.length() > 2 || charSequence.length() == 0)
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void showError(Throwable t) {
        Snackbar.make(
                getBinding().getRoot(),
                t.toString(),
                Snackbar.LENGTH_SHORT).show();
    }
}
