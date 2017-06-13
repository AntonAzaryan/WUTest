package com.wunderground.weatherunderground.archi.mvvm;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.wunderground.weatherunderground.BR;
import com.wunderground.weatherunderground.archi.base.BaseFragment;
import com.wunderground.weatherunderground.archi.base.HostActivity;

/**
 * Created by Anton Azaryan on 20.01.2017.
 */

public abstract class BaseViewModelFragment<
        B extends ViewDataBinding,
        VM extends BaseFragmentViewModel<? extends MvvmView>> extends BaseFragment<B> {

    public static final int BINDING_VARIABLE_NONE = 0;

    private VM mViewModel;

    protected abstract VM onCreateViewModel();

    protected int getBindingVariable() {
        return BR.viewModel;
    }

    protected abstract MvvmView getMvvmViewContract();

    public VM getViewModel() {
        return mViewModel;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mViewModel = obtainViewModel();
        mViewModel.setContext(activity);
        mViewModel.setFragment(this);
    }

    @Override
    public void onDetach() {
        if(getActivity().isChangingConfigurations()) {
            onRetainViewModel();
        }
        super.onDetach();
    }

    private VM obtainViewModel() {
        final HostActivity.RetainCache retainCache = hostActivity.getRetainCache();
        //noinspection unchecked
        VM viewModel = (VM) retainCache.get(getAlias());
        if (viewModel == null) {
            viewModel = onCreateViewModel();
        } else {
            viewModel.retainedInstance = true;
        }
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.onCreate(savedInstanceState);
    }

    @Override
    protected void onBindingInflated(B dataBinding) {
        final int bindingVariable = getBindingVariable();
        if (bindingVariable != BINDING_VARIABLE_NONE) {
            boolean viewModelBindResult = dataBinding.setVariable(bindingVariable, mViewModel);
            if (!viewModelBindResult) {
                throw new IllegalStateException("Could not bind variable");
            }
            dataBinding.executePendingBindings();
        }
        mViewModel.attachMvvmView(getMvvmViewContract());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.detachMvvmView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
        mViewModel.setFragment(null);
        mViewModel.setContext(null);
    }

    protected void onRetainViewModel() {
        hostActivity.getRetainCache().put(getAlias(), mViewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mViewModel.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mViewModel.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mViewModel.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected boolean onHandleMessage(Message message) {
        return mViewModel != null && mViewModel.onHandleMessage(message);
    }

    @Override
    public boolean onBackPressed() {
        return mViewModel != null && mViewModel.onBackPressed();
    }
}
