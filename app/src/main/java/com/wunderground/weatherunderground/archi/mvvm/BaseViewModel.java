package com.wunderground.weatherunderground.archi.mvvm;

import android.content.Context;
import android.databinding.BaseObservable;

/**
 * Created by Anton Azaryan on 21.01.2017.
 */

public abstract class BaseViewModel<
        C extends Context,
        V extends MvvmView> extends BaseObservable implements ViewModel {

    C mContext;
    private V mView;

    @Override
    public final void attachMvvmView(MvvmView view) {
        //noinspection unchecked
        mView = (V) view;
        if (mView != null) {
            onViewAttached(mView);
        }
    }

    @Override
    public void detachMvvmView() {
        if (mView != null) {
            mView = null;
            onViewDetached();
        }
    }

    protected void onViewAttached(V view) {
    }

    protected void onViewDetached() {
    }

    protected void onContextAttached(C context) {
    }

    protected void onContextDetached() {
    }

    public final V getView() {
        return mView;
    }

    public final boolean isViewAttached() {
        return mView != null;
    }

    public final C getContext() {
        return mContext;
    }

    protected final void setContext(C context) {
        this.mContext = context;
        if (context != null) {
            onContextAttached(context);
        } else {
            onContextDetached();
        }
    }
}
