package com.infoicon.bonjob.logger;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.infoicon.bonjob.BuildConfig;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by infoicona on 20/3/17.
 */

public class MyApplication extends Application {

    private static Context context;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
       // Fabric.with(this, new Crashlytics());
        this.context = getApplicationContext();
         // Timber.plant(new CrashReportingTree());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }

    /** return context. */
    public static Context getAppContext() {
        return context;
    }

    /**  A tree which logs important information for crash reporting.  */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
