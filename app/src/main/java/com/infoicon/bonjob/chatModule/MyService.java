package com.infoicon.bonjob.chatModule;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.infoicon.bonjob.logger.Logger;

/**
 * Created by infoicona on 26/5/17.
 */

public class MyService extends Service {
    private final String DOMAIN = "bonjob.co";
   // private final String DOMAIN = "172.104.8.51";
    public ConnectivityManager cm;
    public static MyXMPP xmpp;

    @Override
    public IBinder onBind(final Intent intent) {
        return new LocalBinder<MyService>(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        xmpp = MyXMPP.getInstance(MyService.this, DOMAIN);
        xmpp.connect("onCreate");
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        return Service.START_STICKY;
    }

    @Override
    public boolean onUnbind(final Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        if (xmpp != null){
            Logger.e("My Service disconnect");

            xmpp.disconnect();
        }
        super.onDestroy();
    }

    public void goOffline(){
        xmpp.setUserPresenceWhenOffline();
    }
    public void goOnline(){
        xmpp.setUserPresenceWhenOnline();
    }
    public boolean isNetworkConnected() {
        return cm.getActiveNetworkInfo() != null;
    }
}
