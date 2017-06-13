package com.wunderground.weatherunderground.archi.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.StringRes;

/**
 * Created by anton_soboliev on 04.01.17.
 */

public interface GuidedFragment {

    boolean onBackPressed();

    void postMessage(Message message);

    String getAlias();

    void sendToActivity(Message message);

    void sendToHostFragment(Message message);

    void sendToFragment(String fragmentAlias, Message message);

    /************************
     * Fragment default API *
     ************************/

    void setArguments(Bundle args);

    Bundle getArguments();

    String getString(@StringRes int resId);

    Activity getActivity();

    void startActivity(Intent intent);

    Resources getResources();

    void startActivityForResult(Intent intent, int requestCode);

}
