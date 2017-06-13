package com.wunderground.weatherunderground.archi.base;

import android.app.DialogFragment;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by anton_soboliev on 04.01.17.
 */

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity implements HostActivity {

    private B mBinding;

    private RetainCacheFragment retainCache;
    private Map<String, GuidedFragment> mFragments;

    private boolean bindingInflated;

    protected abstract int getLayoutResId();

    public final B getBinding() {
        return mBinding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!onPreBindingInflate()) {
            inflateBinding();
        }
    }

    /**
     * Use this method to inflate activity layout if onPreBindingInflate() returned true
     */
    public final void forceBindingInflation() {
        if (!bindingInflated) {
            inflateBinding();
        }
    }

    final void inflateBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutResId());
        final View view = mBinding.getRoot();
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                onRootViewGlobalLayout();
            }
        });
        bindingInflated = true;
        onBindingInflated(mBinding);
    }

    /**
     * Override this method if you what to do some logic before setContentView() was called
     *
     * @return true if you what to forbid inflateBinding() method call.
     */
    protected boolean onPreBindingInflate() {
        return false;
    }

    protected void onBindingInflated(B dataBinding) {
    }

    protected void onRootViewGlobalLayout() {
    }

    private Map<String, GuidedFragment> getFragmentsMap() {
        if (mFragments == null) {
            mFragments = new HashMap<>();
        }
        return mFragments;
    }

    protected final Set<String> getAttachedFragmentsAliasSet() {
        return mFragments != null ? mFragments.keySet() : null;
    }

    protected final GuidedFragment getFragmentByAlias(String alias) {
        return mFragments != null ? mFragments.get(alias) : null;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (!(fragment instanceof GuidedFragment
                || fragment instanceof RetainCacheFragment
                || fragment instanceof DialogFragment)) {
            throw new IllegalArgumentException("Fragment must implement 'GuidedFragment' interface.");
        }
    }

    @Override
    public final void onAttachGuidedFragment(GuidedFragment fragment) {
        getFragmentsMap().put(fragment.getAlias(), fragment);
    }

    @Override
    public final void onDetachGuidedFragment(GuidedFragment fragment) {
        getFragmentsMap().remove(fragment.getAlias());
    }

    @Override
    public final void postMessage(Message message) {
        if (!onHandleMessage(message))
            message.recycle();
    }

    protected abstract boolean onHandleMessage(Message message);

    @Override
    public final void sendMessageToFragment(String fragmentAlias, Message message) {
        GuidedFragment addresseeFragment = mFragments.get(fragmentAlias);
        if (addresseeFragment != null) {
            addresseeFragment.postMessage(message);
        }
    }

    public final void onBackPressInternal() {
        super.onBackPressed();
    }

    @Override
    public final RetainCache getRetainCache() {
        if (retainCache == null) {
            retainCache = (RetainCacheFragment) getFragmentManager()
                    .findFragmentByTag(RetainCacheFragment.class.getName());

            if (retainCache == null) {
                retainCache = new RetainCacheFragment();
                getFragmentManager().beginTransaction()
                        .add(retainCache, RetainCacheFragment.class.getName())
                        .commit();
            }
        }
        return retainCache;
    }

    public static final class RetainCacheFragment extends Fragment implements RetainCache {

        private Map<String, Object> cache;

        public RetainCacheFragment() {
            setRetainInstance(true);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            cache = null;
        }

        @Override
        public void put(String key, Object value) {
            if (cache == null) {
                cache = new HashMap<>();
            }
            cache.put(key, value);
        }

        @Override
        public Object get(String key) {
            return cache != null ? cache.get(key) : null;
        }
    }

}
