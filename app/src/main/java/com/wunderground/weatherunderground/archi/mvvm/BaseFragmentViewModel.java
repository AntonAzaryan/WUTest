package com.wunderground.weatherunderground.archi.mvvm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.wunderground.weatherunderground.archi.base.GuidedFragment;

/**
 * Created by Anton Azaryan on 20.01.2017.
 */

public abstract class BaseFragmentViewModel<V extends MvvmView> extends BaseViewModel<Activity, V> {

    private GuidedFragment mFragment;
    boolean retainedInstance;

    public boolean isRetainedInstance() {
        return retainedInstance;
    }

    final void setFragment(GuidedFragment fragment) {
        this.mFragment = fragment;
    }

    public GuidedFragment getFragment() {
        return mFragment;
    }

    public final void sendToActivity(Message message) {
        if (mFragment != null) {
            mFragment.sendToActivity(message);
        }
    }

    public final void sendToHostFragment(Message message) {
        if (mFragment != null) {
            mFragment.sendToHostFragment(message);
        }
    }

    public final void sendToFragment(String fragmentAlias, Message message) {
        if (mFragment != null) {
            mFragment.sendToFragment(fragmentAlias, message);
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public boolean onHandleMessage(Message message) {
        return false;
    }

    public boolean onBackPressed() {
        return false;
    }

}
