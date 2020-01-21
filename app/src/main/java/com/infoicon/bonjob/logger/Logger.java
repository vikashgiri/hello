package com.infoicon.bonjob.logger;



import com.infoicon.bonjob.BuildConfig;

import timber.log.Timber;

/**
 * Created by android1 on 8/12/16.
 */

public class Logger {

    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Timber.d(message);
        } else {

        }
    }

    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            Timber.e(message);
        } else {

        }
    }

    public static void wtf(String message) {
        if (BuildConfig.DEBUG) {
            Timber.wtf("Blunder", message);
        } else {

        }
    }

    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            Timber.i(message);
        } else {

        }
    }



}
