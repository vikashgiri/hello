package com.infoicon.bonjob.logger;

import timber.log.Timber;

/**
 * Created by infoicona on 18/9/17.
 */

public class MyDebugTree extends Timber.DebugTree {
    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return String.format("[L:%s] [M:%s] [C:%s]",
                element.getLineNumber(),
                element.getMethodName(),
                super.createStackElementTag(element));
    }
}
