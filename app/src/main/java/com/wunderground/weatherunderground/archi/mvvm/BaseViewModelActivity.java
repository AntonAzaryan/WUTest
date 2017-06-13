package com.wunderground.weatherunderground.archi.mvvm;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.wunderground.weatherunderground.BR;
import com.wunderground.weatherunderground.archi.base.BaseActivity;

/**
 * Created by Anton Azaryan on 20.01.2017.
 */

public abstract class BaseViewModelActivity<
        B extends ViewDataBinding,
        VM extends BaseActivityViewModel<? extends MvvmView>> extends BaseActivity<B> {

    public static final int BINDING_VARIABLE_NONE = 0;
    static final int MSG_ON_RETAIN_FRAGMENT_INSTANCE = 0x00e;

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
    protected boolean onPreBindingInflate() {
        mViewModel = obtainViewModel();
        mViewModel.setContext(this);
        return false;
    }

    private VM obtainViewModel() {
        final RetainCache retainCache = getRetainCache();
        //noinspection unchecked
        VM viewModel = (VM) retainCache.get(getClass().getName());
        if (viewModel == null) {
            viewModel = onCreateViewModel();
        } else {
            viewModel.retainedInstance = true;
        }
        return viewModel;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.onCreate(savedInstanceState);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mViewModel.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
        mViewModel.detachMvvmView();
        mViewModel.setContext(null);

        if (isChangingConfigurations()) {
            onRetainViewModel();
        }
    }

    protected void onRetainViewModel() {
        getRetainCache().put(getClass().getName(), mViewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mViewModel.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mViewModel.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mViewModel.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected boolean onHandleMessage(Message message) {
        return mViewModel != null && mViewModel.onHandleMessage(message);
    }

    @Override
    public void onBackPressed() {
        if (mViewModel != null) {
            mViewModel.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
