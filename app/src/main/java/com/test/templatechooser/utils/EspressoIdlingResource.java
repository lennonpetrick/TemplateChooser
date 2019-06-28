package com.test.templatechooser.utils;

import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is used to notify Espresso the app is busy at the moment
 * of a network call.
 **/
public class EspressoIdlingResource implements IdlingResource {

    private final static EspressoIdlingResource mInstance = new EspressoIdlingResource();

    private final AtomicInteger mCounter = new AtomicInteger(0);
    private volatile ResourceCallback mResourceCallback;

    public static void increment() {
        mInstance.incrementCounter();
    }

    public static void decrement() {
        mInstance.decrementCounter();
    }

    public static IdlingResource get() {
        return mInstance;
    }

    private void incrementCounter() {
        mCounter.incrementAndGet();
    }

    private void decrementCounter() {
        if (mCounter.get() <= 0)
            return;

        final int counter = mCounter.decrementAndGet();
        if (counter == 0 && mResourceCallback != null) {
            mResourceCallback.onTransitionToIdle();
        }
    }

    @Override
    public String getName() {
        return EspressoIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        return mCounter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }
}