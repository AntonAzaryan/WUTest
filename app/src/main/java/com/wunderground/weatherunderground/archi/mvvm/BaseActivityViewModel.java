package com.wunderground.weatherunderground.archi.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.wunderground.weatherunderground.archi.base.BaseActivity;

/**
 * Created by Anton Azaryan on 20.01.2017.
 */

public abstract class BaseActivityViewModel<V extends MvvmView> extends BaseViewModel<BaseActivity, V> {

    boolean retainedInstance;

    public boolean isRetainedInstance() {
        return retainedInstance;
    }

    public final void sendMessageToFragment(String fragmentAlias, Message message) {
        if (mContext != null) {
            mContext.sendMessageToFragment(fragmentAlias, message);
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
    }

    public void onPostCreate(@Nullable Bundle savedInstanceState) {
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

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public boolean onHandleMessage(Message message) {
        return false;
    }

    public void onBackPressed() {
        mContext.onBackPressInternal();
    }

}
