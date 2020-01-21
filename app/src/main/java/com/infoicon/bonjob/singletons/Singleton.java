package com.infoicon.bonjob.singletons;

import com.infoicon.bonjob.chatModule.MyService;
import com.infoicon.bonjob.chatModule.database.DbHelper;
import com.infoicon.bonjob.logger.MyApplication;
import com.infoicon.bonjob.preferences.SharePref_User;
import com.infoicon.bonjob.recruiters.subscription.SubscriptionListAndMyPlan;
import com.infoicon.bonjob.retrofit.model.CurrentLocation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by infoicona on 1/6/17.
 * singleton class for creating static instance of class.
 */

public class Singleton {
    private static SharePref_User userInfo;
    private static DbHelper dbHelper;
    private static MyService myService;
    private static Map<String, String> onlineList;
    private static CurrentLocation currentLocation;
    private static SubscriptionListAndMyPlan subscriptionListAndMyPlan;


    private Singleton() {

    }

    /** method for return the instance of SharePref_User class */
    public static SharePref_User getUserInfoInstance() {
        if (userInfo == null) {
            userInfo = new SharePref_User(MyApplication.getAppContext());
        }
        return userInfo;
    }

    public static DbHelper getDbHelper() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(MyApplication.getAppContext());
        }
        return dbHelper;
    }

    public static MyService getMyService() {
        if (myService == null) {
            myService = new MyService();
        }
        return myService;
    }

    public static Map<String, String> getOnlineList() {
        if (onlineList == null) {
            onlineList = new HashMap<>();
        }
        return onlineList;
    }

    public static CurrentLocation getCurrentLocationBean() {
        if (currentLocation == null) {
            currentLocation = new CurrentLocation();
        }
        return currentLocation;
    }

    public static SubscriptionListAndMyPlan getSubscriptionListAndMyPlan() {
        if (subscriptionListAndMyPlan == null) {
            subscriptionListAndMyPlan = new SubscriptionListAndMyPlan();
        }
        return subscriptionListAndMyPlan;
    }
}