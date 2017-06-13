package com.wunderground.weatherunderground.archi.base;

import android.os.Message;

/**
 * Created by anton_soboliev on 04.01.17.
 */

public interface HostActivity {

    void postMessage(Message message);

    void sendMessageToFragment(String fragmentAlias, Message message);

    void onAttachGuidedFragment(GuidedFragment fragment);

    void onDetachGuidedFragment(GuidedFragment fragment);

    RetainCache getRetainCache();

    interface RetainCache {
        void put(String key, Object value);

        Object get(String key);
    }
}