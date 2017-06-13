package com.wunderground.weatherunderground.archi.base;

import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.DimenRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by anton_soboliev on 04.01.17.
 */

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements GuidedFragment {

    public static final String ARG_FRAGMENT_ALIAS = "ARG_FRAGMENT_ALIAS";

    private B mBinding;
    protected HostActivity hostActivity;

    @Override
    public String getAlias() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getString(ARG_FRAGMENT_ALIAS, getClass().getName());
        }
        return getClass().getName();
    }

    protected abstract int getLayoutResId();

    public final B getBinding() {
        return mBinding;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof HostActivity) {
            hostActivity = (HostActivity) activity;
            hostActivity.onAttachGuidedFragment(this);
        } else {
            throw new IllegalArgumentException("Activity must implement 'HostActivity' interface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        onBindingInflated(mBinding);
        final View view = mBinding.getRoot();
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                onRootViewGlobalLayout();
            }
        });
        return view;
    }

    protected void onBindingInflated(B dataBinding) {
    }

    protected void onRootViewGlobalLayout() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hostActivity.onDetachGuidedFragment(this);
        hostActivity = null;
    }

    @Override
    public final void postMessage(Message message) {
        if (!onHandleMessage(message))
            message.recycle();
    }

    protected abstract boolean onHandleMessage(Message message);

    public final void sendToActivity(Message message) {
        if (hostActivity != null) {
            hostActivity.postMessage(message);
        }
    }

    public final void sendToHostFragment(Message message) {
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof GuidedFragment) {
            ((GuidedFragment) fragment).postMessage(message);
        }
    }

    public final void sendToFragment(String fragmentAlias, Message message) {
        if (hostActivity != null) {
            hostActivity.sendMessageToFragment(fragmentAlias, message);
        }
    }

    public float getDimen(@DimenRes int dimenRes) {
        return getResources().getDimension(dimenRes);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
